package com.es.API_REST_Ez_Learning.service;

import com.es.API_REST_Ez_Learning.dto.PuntuacionDTO;
import com.es.API_REST_Ez_Learning.dto.PuntuacionProfileDTO;
import com.es.API_REST_Ez_Learning.dto.TestDTO;
import com.es.API_REST_Ez_Learning.errors.ResourceNotFoundException;
import com.es.API_REST_Ez_Learning.model.Puntuacion;
import com.es.API_REST_Ez_Learning.model.Test;
import com.es.API_REST_Ez_Learning.model.Usuario;
import com.es.API_REST_Ez_Learning.repository.PuntuacionRepository;
import com.es.API_REST_Ez_Learning.repository.TestRepository;
import com.es.API_REST_Ez_Learning.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PuntuacionService {
    @Autowired
    PuntuacionRepository puntuacionRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    TestRepository testRepository;


    public PuntuacionService(){

    }

    public List<PuntuacionDTO> findAll() {
        return puntuacionRepository.findAll().stream()
                .map(puntuacion -> new PuntuacionDTO(puntuacion.getUsuario().getId(), puntuacion.getTest().getId(), puntuacion.getPuntuacion()))
                .collect(Collectors.toList());
    }

    public List<PuntuacionProfileDTO> findByUsuarioId(Long usuarioId) {
        List<PuntuacionDTO> puntuaciones = puntuacionRepository.findByUsuarioId(usuarioId).stream()
                .map(puntuacion -> new PuntuacionDTO(puntuacion.getUsuario().getId(), puntuacion.getTest().getId(), puntuacion.getPuntuacion()))
                .collect(Collectors.toList());
        List<PuntuacionProfileDTO> puntuacionesPerfil = new ArrayList<>();
        for(PuntuacionDTO puntuacion: puntuaciones){
            Test test = this.testRepository.findById(puntuacion.getIdTest()).get();
            PuntuacionProfileDTO puntuacionPerfil = new PuntuacionProfileDTO( puntuacion.getIdUsuario(), puntuacion.getIdTest(), puntuacion.getPuntuacion(), test.getTitulo() , test.getDificultad(), test.getTipo(), test.getCantidadPreguntas());
            puntuacionesPerfil.add(puntuacionPerfil);
        }
        return puntuacionesPerfil;
    }

    public PuntuacionDTO createPuntuacion(PuntuacionDTO puntuacionDTO) {
        Puntuacion puntuacion = new Puntuacion();
        Usuario usuario = usuarioRepository.findById(puntuacionDTO.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        Test test = testRepository.findById(puntuacionDTO.getIdTest())
                .orElseThrow(() -> new ResourceNotFoundException("Test no encontrado"));
        puntuacion.setUsuario(usuario);
        puntuacion.setTest(test);
        puntuacion.setPuntuacion(puntuacionDTO.getPuntuacion());
        puntuacion = puntuacionRepository.save(puntuacion);
        return new PuntuacionDTO(puntuacion.getUsuario().getId(), puntuacion.getTest().getId(), puntuacion.getPuntuacion());
    }

    public void deletePuntuacion(Long id) {
        Puntuacion puntuacion = puntuacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Puntuacion no encontrada"));
        puntuacionRepository.delete(puntuacion);
    }

    public PuntuacionDTO updatePuntuacion(Long id, PuntuacionDTO puntuacionDTO) {
        Puntuacion puntuacion = puntuacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Puntuacion no encontrada"));
        puntuacion.setPuntuacion(puntuacionDTO.getPuntuacion());
        puntuacion = puntuacionRepository.save(puntuacion);
        return new PuntuacionDTO(puntuacion.getUsuario().getId(), puntuacion.getTest().getId(), puntuacion.getPuntuacion());
    }
}
