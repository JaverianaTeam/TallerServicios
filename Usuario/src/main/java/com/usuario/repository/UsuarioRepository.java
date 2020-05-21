package com.usuario.repository;

import com.usuario.entities.UsuariosEntity;
import com.usuario.model.UsuarioObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuariosEntity, Integer> {

  @Query(name = "consultaUsuario", nativeQuery = true)
  Optional<UsuarioObject> getUsuarioById(@Param("userId") Integer userId);
}
