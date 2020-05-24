package com.dispatcher.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequest {
  private Integer idUsuario;
  private Integer idConvenio;
  private Integer referencia;
  private Integer valor;
}
