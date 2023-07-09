package com.baga.promon.usermanagement.util;

public class PersistenceRepositoryException extends Exception {
    public PersistenceRepositoryException(String message) {
        super(message);
    }

    public PersistenceRepositoryException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
