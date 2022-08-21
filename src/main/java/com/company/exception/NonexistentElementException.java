package com.company.exception;

public class NonexistentElementException extends RuntimeException {
    public NonexistentElementException(String message) {
        super(message);
    }
}
