package com.convenio.model;

import com.convenio.dto.ConvenioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConvenioResponse {
  private Integer idConvenio;
  private String nombre;

  public ConvenioResponse(ConvenioDTO data) {
    idConvenio = data.getPartnerId();
    nombre = data.getPartnerName();
  }
}
