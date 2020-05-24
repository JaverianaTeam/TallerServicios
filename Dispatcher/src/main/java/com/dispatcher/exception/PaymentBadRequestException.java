package com.dispatcher.exception;

public class PaymentBadRequestException extends RuntimeException {
  public PaymentBadRequestException(String message) {
    super(message);
  }
}
