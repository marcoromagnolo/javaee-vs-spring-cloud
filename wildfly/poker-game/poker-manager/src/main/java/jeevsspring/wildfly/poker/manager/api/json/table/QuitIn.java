package jeevsspring.wildfly.poker.manager.api.json.table;

import jeevsspring.wildfly.poker.manager.api.json.player.PlayerSessionIn;

public class QuitIn extends PlayerSessionIn {

    private String tableId;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    @Override
    public String toString() {
        return "QuitIn{" +
                "tableId='" + tableId + '\'' +
                "} " + super.toString();
    }
}
