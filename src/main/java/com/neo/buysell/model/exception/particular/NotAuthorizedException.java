package com.neo.buysell.model.exception.particular;

import org.springframework.http.HttpStatus;

public class NotAuthorizedException extends RuntimeException implements CustomException {
    private final HttpStatus status;

    public NotAuthorizedException(HttpStatus status) {
        this.status = status;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}
