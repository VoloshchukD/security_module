package com.epam.esm.service.exception;

public class ServiceException extends Exception {

    public final String entityCode;

    public final String messageName;

    public ServiceException(String entityCode, String messageName) {
        super();
        this.entityCode = entityCode;
        this.messageName = messageName;
    }

    public ServiceException(Throwable cause, String entityCode, String messageName) {
        super(cause);
        this.entityCode = entityCode;
        this.messageName = messageName;
    }

    public String getMessageName() {
        return messageName;
    }

    public String getEntityCode() {
        return entityCode;
    }

}
