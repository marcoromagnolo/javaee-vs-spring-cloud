package jeevsspring.wildfly.poker.manager.bo.json;

/**
 * @author Marco Romagnolo
 */
public class BOSessionIn {

    private String sessionId;
    private String sessionToken;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
