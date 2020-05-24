package com.convenio.services;

import com.convenio.exception.ConvenioNotFoundException;
import com.convenio.model.ConvenioObject;
import com.convenio.model.ConvenioResponse;
import com.convenio.repository.ConvenioRepository;
import com.convenio.repository.IConfiguracionRepository;
import com.convenio.repository.IEncabezadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConvenioService {

  private ConvenioRepository cr;
  private IConfiguracionRepository confR;
  private IEncabezadoRepository encR;

  @Autowired
  public ConvenioService(ConvenioRepository cr, IConfiguracionRepository confR, IEncabezadoRepository encR) {
    this.cr = cr;
    this.confR = confR;
    this.encR = encR;
  }

  public ConvenioResponse getConvenioById(Integer partnerId) {
    return new ConvenioResponse(cr.getConvenioById(partnerId).orElseThrow(() -> new ConvenioNotFoundException("No se encuentran convenios registrados con el id." + partnerId)));
  }

  public List<ConvenioResponse> getConvenioByName(String partnerName) {
    List<ConvenioResponse> rta = cr
            .getConvenioByName(partnerName)
            .stream()
            .map(ConvenioResponse::new)
            .collect(Collectors.toList());
    if (rta.size() != 0) {
      return rta;
    } else {
      throw new ConvenioNotFoundException("No se encuentran convenios que el nombre coincida con " + partnerName);
    }
  }

  public ConvenioObject getInfoConvenio(Integer partnerId) {
    ConvenioObject rta = cr.getConvenioById2(partnerId).orElseThrow(() -> new ConvenioNotFoundException("No se encuentran convenios registrados con el id." + partnerId));
    rta.setConfiguracion(confR.getConfigByConvenio(partnerId));
    rta.getConfiguracion().forEach(x -> x.setServiceHeaders(encR.getHeaderByConfiguration(x.getConfigurationId())));
    return rta;
  }

}
