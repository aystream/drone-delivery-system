package com.musala.services.medication.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private List<ValidationError> errors;

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.status = httpStatus;
        this.message = message;
    }

    public ErrorResponse(HttpStatus httpStatus, String validationError, List<ValidationError> validationErrors) {
        this.status = httpStatus;
        this.message = validationError;
        this.errors = validationErrors;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorResponse setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public ErrorResponse setErrors(List<ValidationError> errors) {
        this.errors = errors;
        return this;
    }
}