package jeevsspring.wildfly.poker.manager.engine.game.texasholdem;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.engine.game.Game;
import jeevsspring.wildfly.poker.manager.engine.hand.HandAction;
import jeevsspring.wildfly.poker.manager.engine.player.Player;
import jeevsspring.wildfly.poker.manager.engine.table.TableAction;

import java.util.ArrayList;
import java.util.List;

public class THGame extends Game<THGameAction> {

    private final String tableId;
    private final TableSettings settings;

    private int smallBlind;
    private int bigBlind;
    private RoundPhase roundPhase;
    private Long bet;
    private List<Integer> playersIndex = new ArrayList<>();
    private List<Integer> hand = new ArrayList<>();
    private List<Integer> round = new ArrayList<>();

    public THGame(String tableId, TableSettings settings, BoClient boClient) {
       super(tableId, settings, boClient);
       this.tableId = tableId;
       this.settings = settings;
    }

    @Override
    public void action(HandAction action) {
        // On the first time when game is starting
        if (!isRunning() && getPlayers().size() >= 2) {

            // Wait some seconds before game running
            waitStart();
            setRunning(true);

            preparePlayersIndex();

            // Set Random dealer
            int dealerIndex = randomPlayerIndex();
            setDealer(dealerIndex);
        }

        // On hand starting
        if (!isHandRunning()) {

            // Set Dealer
            int dealerIndex = nextPlayerIndex(getDealer());
            setDealer(dealerIndex);

            // Prepare Players turn for hand
            prepareHand(dealerIndex);

            // Set SmallBlind, BigBlind and First to play
            smallBlind = hand.get(0);
            bigBlind = hand.get(1);
            if (getPlayers().size() > 2) {
                setTurn(hand.get(2));
            } else setTurn(hand.get(1));

            // Set all instance var for next actions
            setHandRunning(true);

            // Add Game Action to Queue
            addGameAction(action.getHandId());
        }

        // On betting round start or change
        if (!isRoundRunning()) {
            switch (roundPhase) {

                case PREFLOP:
                    roundPhase = RoundPhase.FLOP;

                    // Discard one card
                    getCardDeck().getCard();

                    // Add 3 Community Cards
                    getCommunityCards().add(getCardDeck().getCard());
                    getCommunityCards().add(getCardDeck().getCard());
                    getCommunityCards().add(getCardDeck().getCard());
                    setRoundRunning(true);
                    break;
                case FLOP:
                    roundPhase = RoundPhase.RIVER;

                    // Discard one card
                    getCardDeck().getCard();

                    // Add 1 Community Card
                    getCommunityCards().add(getCardDeck().getCard());
                    setRoundRunning(true);
                    break;
                case TURN:
                    roundPhase = RoundPhase.RIVER;

                    // Discard one card
                    getCardDeck().getCard();

                    // Add 1 Community Card
                    getCommunityCards().add(getCardDeck().getCard());
                    setRoundRunning(true);
                    break;
                case RIVER:
                    roundPhase = RoundPhase.SHOWDOWN;
                    setRoundRunning(true);
                    break;
                case SHOWDOWN:
                    roundPhase = null;
                    setRoundRunning(false);
                    break;
                default:
                    roundPhase = RoundPhase.PREFLOP;

                    // Distribute 2  cards for every player from Small Blind to Dealer
                    for (int i = 0; i < getPlayers().size(); i++) {
                        int n = (i + smallBlind) % getPlayers().size();
                        String playerId = getSeats().get(n);
                        Player player = getPlayers().get(playerId);
                        player.getCards().add(getCardDeck().getCard());
                        player.getCards().add(getCardDeck().getCard());
                    }

                    // Prepare Players turns for round
                    prepareRound(getTurn());
                    setRoundRunning(true);
            }
        }

        // Play the Betting Round
        if (isRoundRunning()) {
            switch (action.getActionType()) {

                case RAISE:
                    raise(action.getHandId(), action.getPlayerId(), action.getOption());
                    break;
                case BET:
                    bet(action.getHandId(), action.getPlayerId(), action.getOption());
                    break;
                case CALL:
                    call(action.getHandId(), action.getPlayerId());
                    break;
                case CHECK:
                    check(action.getHandId(), action.getPlayerId());
                    break;
                case FOLD:
                    fold(action.getHandId(), action.getPlayerId());
                    break;
            }
        }
    }

    /**
     * Assign a turn for each seat with a player
     */
    private void preparePlayersIndex() {
        playersIndex.clear();
        for (int i = 0; i < getNumberOfSeats(); i++) {
            String playerId = getSeats().get(i);
            if (playerId != null) {
                playersIndex.add(i);
            }
        }
    }

    /**
     * Prepare round by assigning turn order
     * @param index
     */
    private void prepareRound(int index) {
        for (int i = 0; i < getPlayers().size(); i++) {
            index = nextPlayerIndex(index);
            round.add(index);
        }
    }

    /**
     * Prepare hand by assigning dealer player to index 0
     * SmallBlind to index 1 and BigBlind to index 2
     * Player that have the turn have the index 3 if number of player is >= 3
     * @param dealerIndex
     */
    private void prepareHand(int dealerIndex) {
        int index = dealerIndex;
        hand.clear();
        for (int i = 0; i < getPlayers().size(); i++) {
            index = nextPlayerIndex(index);
            hand.add(index);
        }
    }

    @Override
    public void action(TableAction action) {
        if (!isRunning()) {
            switch (action.getActionType()) {
                case BUY_IN:
                    buyin(action.getPlayerId(), action.getOption());
                    break;
                case BUY_OUT:
                    buyout(action.getPlayerId());
                    break;
                case SIT_IN:
                    sitin(action.getPlayerId(), action.getOption());
                    break;
                case SIT_OUT:
                    sitout(action.getPlayerId());
                    break;
            }
        }
    }

    private void addGameAction(String handId) {
        THGameAction gameAction = new THGameAction(handId);
        gameAction.setCommunityCards(getCommunityCards());
        gameAction.setPlayers(getPlayers());
        gameAction.setPots(getPots());
        gameAction.setSeats(getSeats().toArray());
        gameAction.setTurn(getTurn());
        gameAction.setVisitors(getVisitors());
        gameAction.setBigBlind(bigBlind);
        gameAction.setSmallBlind(smallBlind);
        gameAction.setRoundPhase(roundPhase);
        getQueue().offer(gameAction);
    }

    private void raise(String handId, String playerId, String amount) {
        switch (roundPhase) {
            case PREFLOP:
                break;
            case FLOP:
                break;
            case RIVER:
                break;
            case TURN:
                break;
            case SHOWDOWN:
                break;
        }
    }

    private void bet(String handId, String playerId, String amount) {

    }

    private void call(String handId, String playerId) {

    }

    private void check(String handId, String playerId) {

    }

    private void fold(String handId, String playerId) {

    }

    private void sitin(String playerId, String seat) {
        // Add player to Seats
        int s = Integer.parseInt(seat);
        getSeats().set(s ,playerId);

        // Retrieve Player and add to players
        Player player = getBoClient().getPlayer(playerId);
        player.setSeat(s);
        getPlayers().put(playerId, player);

        // Prepare turns
        preparePlayersIndex();

        if (getPlayers().size() >= 2 && !isRunning()) {
            start();
        }
    }

    private void sitout(String playerId) {
        // remove Player from Seats
        for (int i =0; i < getSeats().size(); i++) {
            if (getSeats().get(i).equals(playerId)) {
                getSeats().unset(i);
            }
        }

        // Remove Player from players
        getPlayers().remove(playerId);

        // Prepare turns
        preparePlayersIndex();
    }

    private void buyin(String playerId, String amount) {

    }

    private void buyout(String playerId) {

    }

    public enum RoundPhase {
        PREFLOP, FLOP, RIVER, TURN, SHOWDOWN
    }

}
