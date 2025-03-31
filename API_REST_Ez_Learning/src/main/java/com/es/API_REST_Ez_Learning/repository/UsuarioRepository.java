package com.es.API_REST_Ez_Learning.repository;

import com.es.API_REST_Ez_Learning.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    List<Usuario> findByProfesorId(Long idProfesor);
}
