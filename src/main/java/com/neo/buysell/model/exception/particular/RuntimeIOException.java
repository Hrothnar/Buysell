package com.neo.buysell.model.exception.particular;

import org.springframework.http.HttpStatus;

public class RuntimeIOException extends RuntimeException implements CustomException {
    private HttpStatus status;

    public RuntimeIOException(HttpStatus status) {
        this.status = status;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return "Inner error of reading or writing file";
    }
}
