package com.neo.buysell.model.exception.particular;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends RuntimeException implements CustomException {
    private final HttpStatus status;

    public UserAlreadyExistsException(HttpStatus status) {
        this.status = status;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return "Such user already exists";
    }
}
