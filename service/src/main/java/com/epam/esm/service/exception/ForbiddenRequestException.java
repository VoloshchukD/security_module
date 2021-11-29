package com.epam.esm.service.exception;

public class ForbiddenRequestException extends ServiceException {

    public ForbiddenRequestException(String entityCode, String messageName) {
        super(entityCode, messageName);
    }

    public ForbiddenRequestException(Throwable cause, String entityCode, String messageName) {
        super(cause, entityCode, messageName);
    }

}
