package com.dispatcher.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvenioObject {

  private Integer idConvenio = null;
  private String nombre = null;
  private String descripcion = null;
  private List<ServiceDescription> configuracion = null;

  public ConvenioObject(Integer idConvenio, String nombre, String descripcion) {
    this.idConvenio = idConvenio;
    this.nombre = nombre;
    this.descripcion = descripcion;
  }
}
