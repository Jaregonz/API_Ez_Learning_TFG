package com.es.API_REST_Ez_Learning.repository;

import com.es.API_REST_Ez_Learning.model.EntregaExamen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntregaExamenRepository extends JpaRepository<EntregaExamen, Long> {
    List<EntregaExamen> findByExamenId(Long examenId);
    Optional<EntregaExamen> findByExamenIdAndAlumnoId(Long examenId, Long alumnoId);
}
