package jeevsspring.wildfly.poker.manager.engine.player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bets {

    // Max value of bet
    private long max;

    // Best player with max bet
    private String best;

    // Players map
    private Map<String, Long> players;

    // Players in Allin
    private List<String> allin;

    /**
     * Constructor
     */
    public Bets() {
        this.players = new HashMap<>();
    }

    /**
     * Add bet to player
     * @param playerId
     * @param amount
     */
    public void bet(String playerId, long amount) {
        bet(playerId, amount, false);
    }

    /**
     * Add bet to player
     * @param playerId
     * @param amount
     */
    public void bet(String playerId, long amount, boolean isAllin) {
        players.put(playerId, amount);
        if (amount > max) {
            max = amount;
            best = playerId;
        }
        if (isAllin) allin.add(playerId);
    }

    /**
     * Get max bet value
     * @return
     */
    public long getMax() {
        return max;
    }

    /**
     * Get best player with max bet
     * @return
     */
    public String getBest() {
        return best;
    }

    /**
     * Check if all bet are even
     * @return
     */
    public boolean isEven() {
        for (Map.Entry<String, Long> entry : players.entrySet()) {

            // Bet are not even if one value is not equal to max and is not included in allin players
            if (entry.getValue() != max && !allin.contains(entry.getKey())) return false;
        }
        return true;
    }

    /**
     * Get Players bets
     * @return
     */
    public Map<String, Long> getPlayers() {
        return players;
    }

    /**
     * Get Players in Allin
     * @return
     */
    public List<String> getAllin() {
        return allin;
    }
}
