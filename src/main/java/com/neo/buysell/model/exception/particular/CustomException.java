package com.neo.buysell.model.exception.particular;

import org.springframework.http.HttpStatus;

public interface CustomException {
    String getMessage();
    HttpStatus getStatus();
}
