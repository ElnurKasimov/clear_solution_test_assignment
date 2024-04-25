package com.clearsolution.testAssignment.exception;

public class AgeRestrictionException extends RuntimeException {
    public AgeRestrictionException() {    }

    public AgeRestrictionException(String message) {
            super(message);
        }

}