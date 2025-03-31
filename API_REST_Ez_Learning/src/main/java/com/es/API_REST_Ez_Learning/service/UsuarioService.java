package com.es.API_REST_Ez_Learning.service;

import com.es.API_REST_Ez_Learning.dto.UsuarioDTO;
import com.es.API_REST_Ez_Learning.dto.UsuarioModifyDTO;
import com.es.API_REST_Ez_Learning.dto.UsuarioRegistroDTO;
import com.es.API_REST_Ez_Learning.errors.*;
import com.es.API_REST_Ez_Learning.model.Usuario;
import com.es.API_REST_Ez_Learning.repository.UsuarioRepository;
import com.es.API_REST_Ez_Learning.util.UsuarioMapper;
import com.es.API_REST_Ez_Learning.util.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //BUSCO EL USUARIO POR SU NOMBRE EN A BDD
        Usuario usuario =  usuarioRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        //RETORNAMOS UN USERDETAILS
        UserDetails userDetails = User
                .builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRol()).build();

        return userDetails;
    }

    public UsuarioRegistroDTO registerUser(UsuarioRegistroDTO usuario){
        if(!Validators.isValidPassword(usuario.getPassword())){
            throw new ValidationException("La contraseña no es válida");
        }
        if(!Validators.isValidEmail(usuario.getCorreoElectronico())){
            throw new ValidationException("El email no es válido");
        }
        if(!usuario.getRol().equals("PROFESOR") && !usuario.getRol().equals("ALUMNO")){
            throw new ValidationException("El Rol del usuario debe ser USUARIO o PROFESOR");
        }
        if(usuarioRepository.findByUsername(usuario.getUsername()).isPresent()){
            throw new ConflictException("El nombre de usuario ya existe");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuarioMapper.dtoRegistroToEntity(usuario));
        return usuario;
    }

   public UsuarioDTO getByUsername(String authority, String name, String username) {
       Optional<Usuario> usuario = this.usuarioRepository.findByUsername(username);
       if(usuario.isPresent()){
           if(authority.equalsIgnoreCase("role_profesor")){
                   if(!usuario.get().getRol().equalsIgnoreCase("PROFESOR")){
                       return UsuarioMapper.entityToDTO(usuario.get());
                   }else{
                       if (usuario.get().getUsername().equals(name)){
                           return UsuarioMapper.entityToDTO(usuario.get());
                       }else {
                           throw  new NotAuthorizationException("No tienes los permisos para esta acción");
                       }
               }
           }else{
               if (usuario.get().getNombre().equals(name)){
                   return UsuarioMapper.entityToDTO(usuario.get());
               }else{
                   throw new NotAuthorizationException("No tienes los permisos para esta acción"); //ERROR UNAUTHORIZED;
               }
           }
       }else{
            throw new ResourceNotFoundException("No se ha encontrado el usuario indicado"); //ERROR
       }
   }

    public UsuarioDTO deleteUser(String id) {
        try{
            Long idLong = Long.parseLong(id);
            Usuario usuario = this.usuarioRepository.findById(idLong).get();
            this.usuarioRepository.delete(usuario);
            return UsuarioMapper.entityToDTO(usuario);
        } catch (Exception e) {
            throw new ValidationException("El id no es válido");
        }


    }

    public UsuarioDTO modifyUsuario(String id, UsuarioModifyDTO usuarioModifyDTO) {
        Long idLong = Long.parseLong(id);
        Usuario usuario =  this.usuarioRepository.findById(idLong).get();
        if(usuarioModifyDTO.getUsername() != null ){
            usuario.setUsername(usuarioModifyDTO.getUsername());
        }
        if(usuarioModifyDTO.getCorreoElectronico() != null ){
            if(!Validators.isValidEmail(usuarioModifyDTO.getCorreoElectronico())){
                throw new ValidationException("El email que desea introducir no es válido");
            }
            usuario.setCorreoElectronico(usuarioModifyDTO.getCorreoElectronico());
        }
        if(usuarioModifyDTO.getNombre() != null ){
            usuario.setNombre(usuarioModifyDTO.getNombre());
        }
        if(usuarioModifyDTO.getApellidos() != null ){
            usuario.setApellidos(usuarioModifyDTO.getApellidos());
        }
        if(usuarioModifyDTO.getNivel() != null ){
            usuario.setNivel(usuarioModifyDTO.getNivel());
        }
        if(usuarioModifyDTO.getRol() != null ){
            usuario.setRol(usuarioModifyDTO.getRol());
        }
        if(usuarioModifyDTO.getImagenPerfil() != null ){
            usuario.setImagenPerfil(usuarioModifyDTO.getImagenPerfil());
        }
        return UsuarioMapper.entityToDTO(this.usuarioRepository.save(usuario));
    }

    public List<UsuarioDTO> getAlumnos(Authentication authentication) {
        Usuario profesor = usuarioRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado"));

        if (!profesor.getRol().equalsIgnoreCase("profesor")) {
            throw new AccessDeniedException("No tienes permiso para ver esta información");
        }

        List<Usuario> alumnos = usuarioRepository.findByProfesorId(profesor.getId());
        return alumnos.stream().map(UsuarioMapper::entityToDTO).collect(Collectors.toList());
    }
}

