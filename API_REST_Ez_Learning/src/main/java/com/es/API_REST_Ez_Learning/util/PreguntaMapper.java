package com.es.API_REST_Ez_Learning.util;


import com.es.API_REST_Ez_Learning.dto.PreguntaDTO;
import com.es.API_REST_Ez_Learning.dto.RespuestaDTO;
import com.es.API_REST_Ez_Learning.mapper_interfaces.RespuestaMapper;
import com.es.API_REST_Ez_Learning.model.Pregunta;
import com.es.API_REST_Ez_Learning.model.Respuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PreguntaMapper {


    private RespuestaMapper respuestaMapper;

    @Autowired
    public PreguntaMapper(RespuestaMapper respuestaMapper) {
        this.respuestaMapper = respuestaMapper;
    }

    public  Pregunta dtoToEntity(PreguntaDTO preguntaDto){
        List<Respuesta> respuestasDTO =  new ArrayList<>();
        for (RespuestaDTO respuestaDTO: preguntaDto.getRespuestas()) {
            respuestasDTO.add(respuestaMapper.dtoToEntity(respuestaDTO));
        }
        return new Pregunta(
                preguntaDto.getContenidoPregunta(),
                respuestasDTO);
    }

    public  PreguntaDTO entityToDTO(Pregunta pregunta){
        List<RespuestaDTO> respuestas =  new ArrayList<>();
        for (Respuesta respuesta: pregunta.getRespuestas()) {
            respuestas.add(respuestaMapper.entityToDto(respuesta));
        }
        return new PreguntaDTO(
                pregunta.getContenidoPregunta(),
                respuestas);
    }
}
