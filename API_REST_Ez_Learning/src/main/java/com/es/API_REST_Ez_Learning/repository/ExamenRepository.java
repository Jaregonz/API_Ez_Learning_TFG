package com.es.API_REST_Ez_Learning.repository;

import com.es.API_REST_Ez_Learning.model.Examen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamenRepository extends JpaRepository<Examen, Long> {
    List<Examen> findByProfesorId(Long profesorId);
}
