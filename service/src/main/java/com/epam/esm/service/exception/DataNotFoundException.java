package com.epam.esm.service.exception;

public class DataNotFoundException extends ServiceException {

    public DataNotFoundException(String entityCode, String messageName) {
        super(entityCode, messageName);
    }

    public DataNotFoundException(Throwable cause, String entityCode, String messageName) {
        super(cause, entityCode, messageName);
    }

}
