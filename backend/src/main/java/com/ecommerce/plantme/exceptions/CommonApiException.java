package com.ecommerce.plantme.exceptions;

public class CommonApiException extends RuntimeException {

    public CommonApiException() {
    }

    public CommonApiException(String message) {
        super(message);
    }

    public CommonApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonApiException(Throwable cause) {
        super(cause);
    }
}

