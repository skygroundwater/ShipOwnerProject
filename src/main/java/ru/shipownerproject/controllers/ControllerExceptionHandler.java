package ru.shipownerproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.shipownerproject.utils.exceptions.*;

import java.io.IOException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(IOException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(AlreadyAddedToBaseException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(ListIsEmptyException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(NotFoundInBaseException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(NotCreatedException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(NotRefactoredException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }
}