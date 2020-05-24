package com.convenio.model;

import lombok.Data;

@Data
public class RemoteResponse {
  private String reference;
  private String value;
  private String message;
}
