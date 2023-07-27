package com.neo.buysell.model.exception.particular;

import org.springframework.http.HttpStatus;

public class NotAuthorizedException extends RuntimeException implements CustomException {
    private final HttpStatus status;
    private final String message;

    public NotAuthorizedException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
