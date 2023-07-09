package com.neo.buysell.model.exception.particular;

import org.springframework.http.HttpStatus;

public class EntityNotFound extends RuntimeException implements CustomException {
    private final Class<?> klass;
    private final HttpStatus status;

    public EntityNotFound(Class<?> klass, HttpStatus status) {
        this.klass = klass;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return String.format("Such %s does not exist", klass.getSimpleName());
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

}
