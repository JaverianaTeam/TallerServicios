package com.dispatcher.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationObject {
  private String servicio;
  private Integer idUsuario;
  private String nombreUsuario;
  private String email;
  private Integer idConvenio;
  private String nombreConvenio;
  private Integer referencia;
  private Integer valor;
  private Integer nroTransaccion;
  private String mensaje;
}
