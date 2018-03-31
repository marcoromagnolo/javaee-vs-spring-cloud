package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.hand.ActionOut;
import jeevsspring.wildfly.poker.manager.api.json.Status;
import jeevsspring.wildfly.poker.manager.api.json.hand.*;
import jeevsspring.wildfly.poker.manager.game.GameException;
import jeevsspring.wildfly.poker.manager.game.engine.GameAction;
import jeevsspring.wildfly.poker.manager.game.engine.GameActions;
import jeevsspring.wildfly.poker.manager.game.hand.Card;
import jeevsspring.wildfly.poker.manager.game.hand.HandActionType;
import jeevsspring.wildfly.poker.manager.game.hand.HandActions;
import jeevsspring.wildfly.poker.manager.game.hand.Pot;
import jeevsspring.wildfly.poker.manager.game.player.Player;
import jeevsspring.wildfly.poker.manager.game.player.PlayerManager;
import org.jboss.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Stateless
@LocalBean
@Path("/hand")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HandApi<E extends GameAction> {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @EJB
    private HandActions handQueue;

    @EJB
    private PlayerManager playerManager;

    @EJB
    private GameActions<E> gameActions;

    @GET
    @Path("/test")
    public Response test() {
        logger.trace("test()");
        Status out = new Status();
        out.setMessage("Test completed");
        return Response.ok("Test completed").build();
    }

    @POST
    @Path("/bet")
    public Response bet(BetIn in) {
        logger.trace("bet(" + in + ")");

        Response response;
        BetOut out = new BetOut();
        try {
            String playerId = playerManager.getPlayerId(in.getSessionId());
            handQueue.insert(HandActionType.BET, in.getTableId(), in.getHandId(), playerId, in.getAmount());
            List<ActionOut> actions = toActions(in.getTableId(), playerId);
            out.setActions(actions);
            out.setSessionId(in.getSessionId());
            out.setSessionToken(in.getSessionToken());
            response = Response.ok(out).build();
        } catch (GameException e) {
            logger.error(e.getMessage(), e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("bet(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/call")
    public Response call(CallIn in) {
        logger.trace("call(" + in + ")");

        Response response;
        CallOut out = new CallOut();
        try {
            String playerId = playerManager.getPlayerId(in.getSessionId());
            handQueue.insert(HandActionType.CALL, in.getTableId(), in.getHandId(), playerId, null);
            List<ActionOut> actions = toActions(in.getTableId(), playerId);
            out.setActions(actions);
            out.setSessionId(in.getSessionId());
            out.setSessionToken(in.getSessionToken());
            response = Response.ok(out).build();
        } catch (GameException e) {
            logger.error(e.getMessage(), e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("call(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/check")
    public Response check(CheckIn in) {
        logger.trace("check(" + in + ")");

        Response response;
        CheckOut out = new CheckOut();
        try {
            String playerId = playerManager.getPlayerId(in.getSessionId());
            handQueue.insert(HandActionType.CHECK, in.getTableId(), in.getHandId(), playerId, null);
            List<ActionOut> actions = toActions(in.getTableId(), playerId);
            out.setActions(actions);
            out.setSessionId(in.getSessionId());
            out.setSessionToken(in.getSessionToken());
            response = Response.ok(out).build();
        } catch (GameException e) {
            logger.error(e.getMessage(), e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("check(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/raise")
    public Response raise(RaiseIn in) {
        logger.trace("raise(" + in + ")");

        Response response;
        RaiseOut out = new RaiseOut();
        try {
            String playerId = playerManager.getPlayerId(in.getSessionId());
            handQueue.insert(HandActionType.RAISE, in.getTableId(), in.getHandId(), playerId, in.getAmount());
            List<ActionOut> actions = toActions(in.getTableId(), playerId);
            out.setActions(actions);
            out.setSessionId(in.getSessionId());
            out.setSessionToken(in.getSessionToken());
            response = Response.ok(out).build();
        } catch (GameException e) {
            logger.error(e.getMessage(), e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("raise(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/fold")
    public Response fold(FoldIn in) {
        logger.trace("fold(" + in + ")");

        Response response;
        FoldOut out = new FoldOut();
        try {
            String playerId = playerManager.getPlayerId(in.getSessionId());
            handQueue.insert(HandActionType.FOLD, in.getTableId(), in.getHandId(), playerId, null);
            List<ActionOut> actions = toActions(in.getTableId(), playerId);
            out.setActions(actions);
            out.setSessionId(in.getSessionId());
            out.setSessionToken(in.getSessionToken());
            response = Response.ok(out).build();
        } catch (GameException e) {
            logger.error(e.getMessage(), e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("fold(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/sync")
    public Response sync(SyncIn in) {
        logger.trace("sync(" + in + ")");

        Response response;
        SyncOut out = new SyncOut();
        try {
            String playerId = playerManager.getPlayerId(in.getSessionId());
            List<ActionOut> actions = toActions(in.getTableId(), playerId);
            out.setActions(actions);
            out.setSessionId(in.getSessionId());
            out.setSessionToken(in.getSessionToken());
            response = Response.ok(out).build();
        } catch (GameException e) {
            logger.error(e.getMessage(), e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("sync(" + in + ") return " + out);
        return response;
    }

    private List<ActionOut> toActions(String tableId, String playerId) throws GameException {
        logger.trace("toActions(" + tableId + ", " + playerId + ")");
        List<ActionOut> out = new ArrayList<>();
        List<E> list = gameActions.get(tableId, playerId);
        for (GameAction action : list) {
            ActionOut actionOut = convert(action, playerId);
            actionOut.setTableId(tableId);
            out.add(actionOut);
        }
        return out;
    }

    private ActionOut convert(GameAction o, String playerId) {
        logger.trace("newGameAction(" + o + ", " + playerId  + ")");
        ActionOut actionOut = new ActionOut();
        actionOut.setActionId(o.getId());
        actionOut.setHandId(o.getHandId());

        // Set Players (without player id)
        for (Player player : o.getPlayers().values()) {
            PlayerOut playerOut = new PlayerOut();

            //Set Player Cards only for current player, only if isn't a visitor
            for (Card card : player.getCards()) {
                CardOut cardOut = null;
                if (!card.isShadow()) {
                    String c = card.getSuit().getValue() + card.getSymbol().getValue();
                    cardOut = CardOut.valueOf(c);
                    playerOut.getCards().add(cardOut);
                }
            }
            playerOut.setBalance(player.getBalance());
            playerOut.setNickname(player.getNickname());
            playerOut.setSeat(player.getSeat());
            playerOut.setSitOut(player.isSitOut());
            actionOut.getPlayers().add(playerOut);
        }

        // Set Community Cards
        for (Card card : o.getCommunityCards()) {
            String c = card.getSuit().getValue() + card.getSymbol().getValue();
            CardOut cardOut = CardOut.valueOf(c);
            actionOut.getCommunityCards().add(cardOut);
        }

        // Set Pots (with nickname instead of player id)
        for (Pot pot : o.getPots()) {
            PotOut potOut = new PotOut();
            potOut.setValue(pot.getValue());
            for (String potPlayerId : pot.getPlayers()) {
                String nickname = o.getPlayers().get(potPlayerId).getNickname();
                potOut.getPlayers().add(nickname);
            }
            actionOut.getPots().add(potOut);
        }

        return actionOut;
    }
}
