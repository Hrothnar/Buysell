package com.neo.buysell.model.exception.particular;

import org.springframework.http.HttpStatus;

public class NotAuthenticatedException extends RuntimeException implements CustomException {
    private final HttpStatus status;

    public NotAuthenticatedException(HttpStatus status) {
        this.status = status;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}
