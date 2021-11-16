package com.epam.esm.service.exception;

public class IllegalPageNumberException extends Exception {

    public IllegalPageNumberException() {
    }

    public IllegalPageNumberException(String message) {
        super(message);
    }

    public IllegalPageNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalPageNumberException(Throwable cause) {
        super(cause);
    }

}
