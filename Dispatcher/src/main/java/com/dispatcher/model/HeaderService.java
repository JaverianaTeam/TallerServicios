package com.dispatcher.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HeaderService {
  private String headerName;
  private String headerValue;
}
