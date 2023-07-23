package com.neo.buysell.model.exception;

import com.neo.buysell.model.exception.particular.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@RestControllerAdvice()
public class CustomAdviceHandler {

    @ExceptionHandler(value = {EntityNotFound.class,
            WrongPasswordException.class,
            UserAlreadyExistsException.class,
            NotAuthenticatedException.class,
            NotAuthorizedException.class}
    )
    public ResponseEntity<Error> handleEntityNotFoundException(CustomException ex, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = ex.getStatus();
        Error error = new Error();
        error.setTimestamp(new Timestamp(System.currentTimeMillis()));
        error.setStatus(status.value());
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());
        return new ResponseEntity<>(error, headers, status);
    }


}
