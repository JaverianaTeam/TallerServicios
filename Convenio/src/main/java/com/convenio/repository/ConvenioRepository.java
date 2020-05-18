package com.convenio.repository;

import com.convenio.dto.ConvenioDTO;
import com.convenio.entities.ConveniosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConvenioRepository extends JpaRepository<ConveniosEntity, Integer> {

  @Query(name = "convenio.queryById", nativeQuery = true)
  Optional<ConvenioDTO> getConvenioById(@Param("partnerId") Integer partnerId);

  @Query(name = "convenio.queryByName", nativeQuery = true)
  List<ConvenioDTO> getConvenioByName(@Param("partnerName") String partnerName);
}
