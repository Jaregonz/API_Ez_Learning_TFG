package com.es.API_REST_Ez_Learning.mapper_interfaces;


import com.es.API_REST_Ez_Learning.dto.RespuestaDTO;
import com.es.API_REST_Ez_Learning.model.Respuesta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RespuestaMapper {
    @Mapping(target = "pregunta.id", source = "preguntaId")
    Respuesta dtoToEntity(RespuestaDTO respuestaDTO);

    @Mapping(target = "preguntaId", source = "pregunta.id")
    RespuestaDTO entityToDto(Respuesta respuesta);
}
