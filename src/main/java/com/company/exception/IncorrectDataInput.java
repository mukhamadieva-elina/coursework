package com.company.exception;

public class IncorrectDataInput extends RuntimeException {
    public IncorrectDataInput(String message) {
        super(message);
    }
}
