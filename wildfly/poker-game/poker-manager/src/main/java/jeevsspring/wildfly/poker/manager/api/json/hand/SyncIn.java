package jeevsspring.wildfly.poker.manager.api.json.hand;

import jeevsspring.wildfly.poker.manager.api.json.player.PlayerSessionIn;

public class SyncIn extends PlayerSessionIn {

    private String handId;
    private String tableId;

    public String getHandId() {
        return handId;
    }

    public void setHandId(String handId) {
        this.handId = handId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    @Override
    public String toString() {
        return "SyncIn{" +
                "handId='" + handId + '\'' +
                ", tableId='" + tableId + '\'' +
                "} " + super.toString();
    }
}
