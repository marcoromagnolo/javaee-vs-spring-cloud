package jeevsspring.wildfly.backoffice.service;

public class InvalidSessionException extends AbstractServiceException {

    public InvalidSessionException() {
        super(ErrorCode.INVALID_SESSION);
    }
}
