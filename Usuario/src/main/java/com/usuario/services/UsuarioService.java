package com.usuario.services;

import com.usuario.entities.UsuariosEntity;
import com.usuario.exception.UsuarioNotFoundException;
import com.usuario.model.UsuarioObject;
import com.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

  private UsuarioRepository ur;

  @Autowired
  public UsuarioService(UsuarioRepository ur) {
    this.ur = ur;
  }

  public UsuarioObject getUsuarioById(Integer userId) {
    return ur.getUsuarioById(userId).orElseThrow(() -> new UsuarioNotFoundException("No se encuentran usuarios registrados con el id." + userId));
  }

  public void deleteUsuario(Integer userId) {
    UsuariosEntity aux = ur.findById(userId).orElseThrow(() -> new UsuarioNotFoundException("No se encuentran usuarios registrados con el id." + userId));
    ur.delete(aux);
  }

  public UsuarioObject insertUsuario(UsuarioObject usuario) {
    return new UsuarioObject(ur.save(new UsuariosEntity(usuario)));
  }

  public UsuarioObject updateUsuario(UsuarioObject usuario) {
    UsuariosEntity aux = ur.findById(usuario.getIdUsuario()).orElseThrow(() -> new UsuarioNotFoundException("No se encuentran usuarios registrados con el id." + usuario.getIdUsuario()));
    aux.setUserName(usuario.getNombre());
    aux.setUserEmail(usuario.getEmail());
    return new UsuarioObject(ur.save(aux));
  }
}


