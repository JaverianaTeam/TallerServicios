package com.dispatcher.proxies;

import com.dispatcher.model.UsuarioObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Orquestación con el servicio de Convenios para traer la información del usuario que está realizando el pago.
 */
@FeignClient(name="usuario-service", url = "${modval.usuarioService.url}")
public interface UsuarioProxy {

  @GetMapping(path = "/usuarios/api/{idUsuario}")
  UsuarioObject getUsuario(@PathVariable("idUsuario") Integer idUsuario);

}
