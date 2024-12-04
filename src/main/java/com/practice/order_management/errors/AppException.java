package com.practice.order_management.errors;

public class AppException extends RuntimeException {
    private int statusCode;

    public AppException(int status, String message) {
        super(message);
        this.statusCode = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
