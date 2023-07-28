package com.neo.buysell.model.exception;

import com.neo.buysell.model.exception.particular.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@RestControllerAdvice()
public class CustomAdviceHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomAdviceHandler.class);

    @ExceptionHandler(value = {
            EntityNotFound.class,
            WrongPasswordException.class,
            RuntimeIOException.class,
            UserAlreadyExistsException.class,
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
        LOG.error("Exception {} has occurred with message: {}", ex.getName(), ex.getMessage());
        return new ResponseEntity<>(error, headers, status);
    }


}
