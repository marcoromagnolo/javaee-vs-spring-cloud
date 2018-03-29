package jeevsspring.wildfly.poker.manager.api.json.table;

import jeevsspring.wildfly.poker.manager.api.json.player.PlayerSessionOut;

public class BuyinOut extends PlayerSessionOut {

    private long amount;

    private long balance;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BuyinOut{" +
                "amount=" + amount +
                ", balance=" + balance +
                "} " + super.toString();
    }
}
