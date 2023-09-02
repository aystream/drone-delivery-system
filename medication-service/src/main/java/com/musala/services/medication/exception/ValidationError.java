package com.musala.services.medication.exception;

public class ValidationError {
    private String field;
    private String message;

    public ValidationError(String field, String defaultMessage) {
        this.field = field;
        this.message = defaultMessage;
    }

    public String getField() {
        return field;
    }

    public ValidationError setField(String field) {
        this.field = field;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ValidationError setMessage(String message) {
        this.message = message;
        return this;
    }
}