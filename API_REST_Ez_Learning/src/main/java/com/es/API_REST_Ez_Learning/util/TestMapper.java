package com.es.API_REST_Ez_Learning.util;

import com.es.API_REST_Ez_Learning.dto.PreguntaDTO;
import com.es.API_REST_Ez_Learning.dto.TestDTO;
import com.es.API_REST_Ez_Learning.model.Pregunta;
import com.es.API_REST_Ez_Learning.model.Test;
import com.es.API_REST_Ez_Learning.model.Usuario;
import com.es.API_REST_Ez_Learning.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestMapper {

    private PreguntaMapper preguntaMapper;
    private static final List<String> ORDEN_NIVELES = List.of("A2", "B1", "B2", "C1");

    @Autowired
    public TestMapper(PreguntaMapper preguntaMapper) {
        this.preguntaMapper = preguntaMapper;
    }

    public Test dtoToEntity(TestDTO testDto, Usuario usuario){
        List<Pregunta> preguntas = new ArrayList<>();
        for(PreguntaDTO pregunta : testDto.getPreguntas()){
            preguntas.add(preguntaMapper.dtoToEntity(pregunta));
        }

        return new Test(
                usuario,
                testDto.getTipo(),
                testDto.getTitulo(),
                testDto.getDificultad(),
                testDto.getCantidadPreguntas(),
                testDto.getTiempo(),
                preguntas);

    }

    public TestDTO entityToDTO(Test test){
        List<PreguntaDTO> preguntasDTO = new ArrayList<>();
        for(Pregunta pregunta : test.getPreguntas()){
            preguntasDTO.add(preguntaMapper.entityToDTO(pregunta));
        }

        return new TestDTO(
                test.getId(),
                test.getUsuario().getId(),
                test.getTipo(),
                test.getTitulo(),
                test.getDificultad(),
                test.getCantidadPreguntas(),
                test.getTiempo(),
                preguntasDTO);

    }
    public TestDTO entityToDTOWithId(Test test){
        List<PreguntaDTO> preguntasDTO = new ArrayList<>();
        for(Pregunta pregunta : test.getPreguntas()){
            preguntasDTO.add(preguntaMapper.entityToDTO(pregunta));
        }

        return new TestDTO(
                test.getId(),
                test.getUsuario().getId(),
                test.getTipo(),
                test.getTitulo(),
                test.getDificultad(),
                test.getCantidadPreguntas(),
                test.getTiempo(),
                preguntasDTO);

    }

    public static List<TestDTO> filtrarPorNivel(String nivelMinimo, List<TestDTO> testsDTO) {
        String nivelMinimoAjustado = (nivelMinimo == null) ? "C1" : nivelMinimo;
        if (!ORDEN_NIVELES.contains(nivelMinimo)) {
            throw new IllegalArgumentException("Nivel mínimo inválido: " + nivelMinimo);
        }

        return testsDTO.stream()
                .filter(objeto -> ORDEN_NIVELES.indexOf(objeto.getDificultad()) <= ORDEN_NIVELES.indexOf(nivelMinimoAjustado))
                .collect(Collectors.toList());
    }

}
