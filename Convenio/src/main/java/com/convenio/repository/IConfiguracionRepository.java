package com.convenio.repository;

import com.convenio.entities.ConfiguracionEntity;
import com.convenio.model.ServiceDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IConfiguracionRepository extends JpaRepository<ConfiguracionEntity, Integer> {

  @Query(name = "configurationQuery", nativeQuery = true)
  List<ServiceDescription> getConfigByConvenio(@Param("partnerId") Integer partnerId);
}
