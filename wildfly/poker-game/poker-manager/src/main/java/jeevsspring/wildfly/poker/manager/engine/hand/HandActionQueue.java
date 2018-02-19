package jeevsspring.wildfly.poker.manager.engine.hand;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Marco Romagnolo
 */
@Singleton
@LocalBean
public class HandActionQueue {

    private Queue<HandAction> actions;

    @PostConstruct
    public void init() {
        actions = new ArrayDeque<>();
    }

    public boolean isEmpty() {
        return actions.isEmpty();
    }

    public boolean insert(HandActionType actionType, String tableId, String handId, String playerId, Long amount) {
        HandAction hand = new HandAction(actionType, tableId, handId, playerId, amount);
        return actions.offer(hand);
    }

    public HandAction examine() {
        return actions.peek();
    }

    public HandAction poll() {
        return actions.poll();
    }

}