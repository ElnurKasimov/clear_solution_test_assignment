package com.clearsolution.testAssignment.exception;

public class FieldValidationException extends RuntimeException {
    public FieldValidationException() {    }

    public FieldValidationException(String message) {
            super(message);
        }

}
