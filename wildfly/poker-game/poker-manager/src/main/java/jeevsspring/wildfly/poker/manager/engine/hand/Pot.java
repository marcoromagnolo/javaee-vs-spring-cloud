package jeevsspring.wildfly.poker.manager.engine.hand;

import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Marco Romagnolo
 */
public class Pot {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private String id;
    private long value;
    private List<String> players;

    public Pot() {
        this(new ArrayList<>());
    }

    public Pot(List<String> players) {
        logger.debug("Pot :: Constructor(" + players + ")");
        this.id = UUID.randomUUID().toString();
        this.players = players;
    }

    public String getId() {
        return id;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }
}
