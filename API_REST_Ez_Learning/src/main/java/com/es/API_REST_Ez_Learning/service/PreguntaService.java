package com.es.API_REST_Ez_Learning.service;

import com.es.API_REST_Ez_Learning.dto.PreguntaDTO;
import com.es.API_REST_Ez_Learning.dto.RespuestaDTO;
import com.es.API_REST_Ez_Learning.errors.ConflictException;
import com.es.API_REST_Ez_Learning.mapper_interfaces.RespuestaMapper;
import com.es.API_REST_Ez_Learning.model.Pregunta;
import com.es.API_REST_Ez_Learning.model.Respuesta;
import com.es.API_REST_Ez_Learning.repository.PreguntaRepository;
import com.es.API_REST_Ez_Learning.util.PreguntaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PreguntaService {

    private PreguntaRepository preguntaRepository;

    private RespuestaMapper respuestaMapper;

    private PreguntaMapper preguntaMapper;

    @Autowired
    public PreguntaService(PreguntaRepository preguntaRepository, RespuestaMapper respuestaMapper, PreguntaMapper preguntaMapper) {
        this.preguntaRepository = preguntaRepository;
        this.respuestaMapper = respuestaMapper;
        this.preguntaMapper = preguntaMapper;
    }

    public List<PreguntaDTO> findAll() {
        List<PreguntaDTO> preguntaDTOS = new ArrayList<>();
        List<Pregunta> allPreguntas = preguntaRepository.findAll();
        for(Pregunta p: allPreguntas){
            preguntaDTOS.add(preguntaMapper.entityToDTO(p));
        }
        return preguntaDTOS;
    }

    public PreguntaDTO findById(Long id) {
        return preguntaMapper.entityToDTO(preguntaRepository.findById(id).get());
    }

    public PreguntaDTO save(PreguntaDTO pregunta) {
        if(this.preguntaRepository.findByContenidoPregunta(pregunta.getContenidoPregunta()).isPresent()){
            throw new ConflictException("Conflicto: La pregunta ya existe en base de datos");
        }else{
            Pregunta p = preguntaMapper.dtoToEntity(pregunta);
            preguntaRepository.save(p);
            return pregunta;
        }

    }

    public PreguntaDTO updatePregunta(PreguntaDTO preguntaDTO, Long id) {
        Pregunta pregunta = this.preguntaRepository.findById(id).get();

        if(preguntaDTO.getContenidoPregunta() != null){
            pregunta.setContenidoPregunta(preguntaDTO.getContenidoPregunta());
        }
        if(preguntaDTO.getRespuestas() != null){
            List<Respuesta> respuestas = new ArrayList<>();
            for(RespuestaDTO respuestaDTO: preguntaDTO.getRespuestas()){
                respuestas.add(respuestaMapper.dtoToEntity(respuestaDTO));
            }
            pregunta.setRespuestas(respuestas);
        }
        Pregunta preguntaEditada = preguntaRepository.save(pregunta);
        return preguntaMapper.entityToDTO(preguntaEditada);
    }

    public PreguntaDTO deleteById(Long id) {
        Pregunta pregunta = this.preguntaRepository.findById(id).get();
        preguntaRepository.delete(pregunta);
        return preguntaMapper.entityToDTO(pregunta);
    }
}

