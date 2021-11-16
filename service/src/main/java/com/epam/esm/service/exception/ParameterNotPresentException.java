package com.epam.esm.service.exception;

public class ParameterNotPresentException extends ServiceException {

    public ParameterNotPresentException(String entityCode, String messageName) {
        super(entityCode, messageName);
    }

    public ParameterNotPresentException(Throwable cause, String entityCode, String messageName) {
        super(cause, entityCode, messageName);
    }

}
