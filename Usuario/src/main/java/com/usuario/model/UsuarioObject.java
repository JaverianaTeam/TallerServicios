package com.usuario.model;

import com.usuario.entities.UsuariosEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioObject {
  private Integer idUsuario;
  private String nombre;
  private String email;

  public UsuarioObject(UsuariosEntity data) {
    this.idUsuario = data.getUserId();
    this.nombre = data.getUserName();
    this.email = data.getUserEmail();
  }
}
