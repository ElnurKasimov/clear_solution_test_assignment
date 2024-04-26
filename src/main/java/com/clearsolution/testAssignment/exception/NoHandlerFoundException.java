package com.clearsolution.testAssignment.exception;

public class NoHandlerFoundException extends RuntimeException {
    public NoHandlerFoundException() {    }

    public NoHandlerFoundException(String message) {
        super(message);
    }
}
