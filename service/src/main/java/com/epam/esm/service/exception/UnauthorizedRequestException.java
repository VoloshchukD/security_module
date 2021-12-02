package com.epam.esm.service.exception;

public class UnauthorizedRequestException extends Exception {

    public UnauthorizedRequestException() {
    }

    public UnauthorizedRequestException(String message) {
        super(message);
    }

    public UnauthorizedRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedRequestException(Throwable cause) {
        super(cause);
    }

}
