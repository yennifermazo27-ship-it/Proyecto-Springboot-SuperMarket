package com.Devops.Micro_Market.exception;

public class NotFound extends RuntimeException {
    public NotFound(String message) {
        super(message);
    }
}