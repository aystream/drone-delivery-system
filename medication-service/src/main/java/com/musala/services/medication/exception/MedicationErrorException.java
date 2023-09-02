package com.musala.services.medication.exception;

public class MedicationErrorException extends RuntimeException {
    public MedicationErrorException(String message) {
        super(message);
    }
}
