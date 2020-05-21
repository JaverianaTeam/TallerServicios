package com.usuario.exception;

import com.usuario.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UsuarioExceptionHandler {

  @ExceptionHandler(UsuarioNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponse> handleUsuarioNotFound(RuntimeException ex) {
    return new ResponseEntity<>(new ErrorResponse("404", ex.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UsuarioBadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleUsuarioBadRequest(RuntimeException ex) {
    return new ResponseEntity<>(new ErrorResponse("400", ex.getMessage()), HttpStatus.NOT_FOUND);
  }

}


