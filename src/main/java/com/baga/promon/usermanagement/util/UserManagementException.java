package com.baga.promon.usermanagement.util;

public class UserManagementException extends Exception {
    public UserManagementException(String message) {
        super(message);
    }

    public UserManagementException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
