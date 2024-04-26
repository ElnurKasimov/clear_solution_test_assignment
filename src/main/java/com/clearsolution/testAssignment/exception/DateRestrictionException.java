package com.clearsolution.testAssignment.exception;

public class DateRestrictionException extends RuntimeException {
    public DateRestrictionException() {    }

    public DateRestrictionException(String message) {
            super(message);
        }

}