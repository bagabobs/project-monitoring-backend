package com.baga.promon.usermanagement.util;

public class PersistenceAdapterException extends Exception {
    public PersistenceAdapterException(String message) {
        super(message);
    }

    public PersistenceAdapterException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
