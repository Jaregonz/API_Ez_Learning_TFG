package com.es.API_REST_Ez_Learning.util;

import com.es.API_REST_Ez_Learning.dto.UsuarioDTO;
import com.es.API_REST_Ez_Learning.dto.UsuarioRegistroDTO;
import com.es.API_REST_Ez_Learning.model.Usuario;
import com.es.API_REST_Ez_Learning.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    @Autowired
    private UsuarioRepository usuarioRepository;
    public  Usuario dtoRegistroToEntity (UsuarioRegistroDTO usuarioRegistroDTO){
        Usuario profesor = null;
        if(usuarioRegistroDTO.getIdProfesor() != null){
            if(this.usuarioRepository.findById(usuarioRegistroDTO.getIdProfesor()).isPresent()){
                profesor = this.usuarioRepository.findById(usuarioRegistroDTO.getIdProfesor()).get();
            }
        }
        return new Usuario(
                usuarioRegistroDTO.getUsername(),
                usuarioRegistroDTO.getCorreoElectronico(),
                usuarioRegistroDTO.getPassword(),
                usuarioRegistroDTO.getNombre(),
                usuarioRegistroDTO.getApellidos(),
                usuarioRegistroDTO.getNivel(),
                usuarioRegistroDTO.getRol(),
                usuarioRegistroDTO.getImagenPerfil(),
                profesor);
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
