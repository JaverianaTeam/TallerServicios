package com.usuario.controller;

import com.usuario.model.UsuarioObject;
import com.usuario.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios/")
public class UsuarioController {

  private UsuarioService us;

  @Autowired
  public UsuarioController(UsuarioService us) {
    this.us = us;
  }

  @GetMapping(path = "api/{idUsuario}")
  public UsuarioObject getUsuario(@PathVariable("idUsuario") Integer idUsuario) {
   return us.getUsuarioById(idUsuario);
  }

  @DeleteMapping(path = "api/{idUsuario}")
  public ResponseEntity<String> deleteUsuario(@PathVariable("idUsuario") Integer idUsuario) {
    us.deleteUsuario(idUsuario);
    return new ResponseEntity<>("Usuario eliminado ", HttpStatus.OK);
  }

  @PostMapping(path = "api")
  public UsuarioObject postUsuario(@RequestBody UsuarioObject usuario) {
    return us.insertUsuario(usuario);
  }

  @PutMapping(path = "api")
  public UsuarioObject putUsuario(@RequestBody UsuarioObject usuario) {
    return us.updateUsuario(usuario);
  }

}
