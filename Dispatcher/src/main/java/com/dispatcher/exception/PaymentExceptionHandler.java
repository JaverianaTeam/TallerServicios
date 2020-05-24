package com.dispatcher.exception;

import com.dispatcher.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PaymentExceptionHandler {

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponse> handlePaymentNotFound(RuntimeException ex) {
    return new ResponseEntity<>(new ErrorResponse("404", ex.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(PaymentBadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handlePaymentBadRequest(RuntimeException ex) {
    return new ResponseEntity<>(new ErrorResponse("400", ex.getMessage()), HttpStatus.NOT_FOUND);
  }

}
