package jeevsspring.wildfly.poker.manager.bo.json;

public class BOWinOut extends BOSessionOut {

    private String playerId;

    private String nickname;

    private long balance;

    private long amount;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BOWinOut{" +
                "playerId='" + playerId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", balance=" + balance +
                ", amount=" + amount +
                "} " + super.toString();
    }
}
