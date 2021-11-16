package com.epam.esm.entity.dto;

import java.util.Objects;

public class ErrorData {

    public ErrorData() {
    }

    public ErrorData(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    private String errorMessage;

    private String errorCode;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorData errorData = (ErrorData) o;
        return Objects.equals(errorMessage, errorData.errorMessage)
                && Objects.equals(errorCode, errorData.errorCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorMessage, errorCode);
    }

    @Override
    public String toString() {
        return "ErrorData{" +
                "errorMessage='" + errorMessage + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }

}
