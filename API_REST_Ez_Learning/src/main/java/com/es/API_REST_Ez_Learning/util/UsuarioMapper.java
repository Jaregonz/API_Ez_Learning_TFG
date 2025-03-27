package com.es.API_REST_Ez_Learning.util;

import com.es.API_REST_Ez_Learning.dto.UsuarioDTO;
import com.es.API_REST_Ez_Learning.dto.UsuarioRegistroDTO;
import com.es.API_REST_Ez_Learning.model.Usuario;

public class UsuarioMapper {
    public static Usuario dtoRegistroToEntity (UsuarioRegistroDTO usuarioRegistroDTO){
        return new Usuario(
                usuarioRegistroDTO.getUsername(),
                usuarioRegistroDTO.getCorreoElectronico(),
                usuarioRegistroDTO.getPassword(),
                usuarioRegistroDTO.getNombre(),
                usuarioRegistroDTO.getApellidos(),
                usuarioRegistroDTO.getNivel(),
                usuarioRegistroDTO.getRol(),
                usuarioRegistroDTO.getImagenPerfil());
    }
    public static UsuarioDTO entityToDTO (Usuario usuario){
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getCorreoElectronico(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getNivel(),
                usuario.getRol(),
                usuario.getImagenPerfil());
    }
}
