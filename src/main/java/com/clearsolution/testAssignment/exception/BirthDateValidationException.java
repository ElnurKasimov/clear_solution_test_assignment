package com.clearsolution.testAssignment.exception;

public class BirthDateValidationException extends RuntimeException {
    public BirthDateValidationException() {    }

    public BirthDateValidationException(String message) {
            super(message);
        }

}
