package com.es.API_REST_Ez_Learning.repository;

import com.es.API_REST_Ez_Learning.model.Pregunta;
import com.es.API_REST_Ez_Learning.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
    Optional<Pregunta> findByContenidoPregunta(String contenidoPregunta);
}
