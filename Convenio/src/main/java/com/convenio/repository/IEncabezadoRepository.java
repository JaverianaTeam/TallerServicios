package com.convenio.repository;

import com.convenio.entities.EncabezadosEntity;
import com.convenio.model.HeaderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEncabezadoRepository extends JpaRepository<EncabezadosEntity, Integer> {

  @Query(name = "encabezadosQuery", nativeQuery = true)
  List<HeaderService> getHeaderByConfiguration(@Param("configurationId") Integer configurationId);
}
