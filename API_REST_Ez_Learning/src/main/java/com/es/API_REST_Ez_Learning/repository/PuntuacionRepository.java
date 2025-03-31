package com.es.API_REST_Ez_Learning.repository;

import com.es.API_REST_Ez_Learning.model.Puntuacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PuntuacionRepository extends JpaRepository<Puntuacion, Long> {
     List<Puntuacion> findByUsuarioId(Long usuarioId);
}
