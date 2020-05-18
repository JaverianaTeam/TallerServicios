package com.convenio.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConvenioNotFoundException extends RuntimeException {

  public ConvenioNotFoundException(String message) {
    super(message);
  }
}
