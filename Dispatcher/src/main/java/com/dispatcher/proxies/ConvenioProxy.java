package com.dispatcher.proxies;

import com.dispatcher.model.ConvenioObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Orquestación con el servicio de Convenios para traer la información del convenio y la configuración.
 */
@FeignClient(name = "convenio-service", url = "${modval.convenioService.url}")
public interface ConvenioProxy {

  @GetMapping(value = "/convenios/info/{id}")
  ConvenioObject getInfoConvenio(@PathVariable("id") Integer id);

}
