package com.dispatcher.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentResponse {
  private String referencia;
  private String mensaje;
  private String valor;
  private String nroTransaccion;

}
