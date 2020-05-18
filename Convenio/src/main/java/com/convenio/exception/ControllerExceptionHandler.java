package com.convenio.exception;

import com.convenio.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
  @ExceptionHandler(ConvenioNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponse> handleConvenioNotFound(RuntimeException ex) {
    return new ResponseEntity<ErrorResponse>(new ErrorResponse("404", ex.getMessage()), HttpStatus.NOT_FOUND);
  }
}
