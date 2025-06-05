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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario =  usuarioRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        UserDetails userDetails = User
                .builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRol()).build();

        return userDetails;
    }

    public UsuarioRegistroDTO registerUser(UsuarioRegistroDTO usuario, MultipartFile imagenPerfil) {
        if (!Validators.isValidPassword(usuario.getPassword())) {
            throw new ValidationException("La contraseña no es válida");
        }
        if (!Validators.isValidEmail(usuario.getCorreoElectronico())) {
            throw new ValidationException("El email no es válido");
        }
        if (!usuario.getRol().equals("PROFESOR") && !usuario.getRol().equals("ALUMNO")) {
            throw new ValidationException("El Rol del usuario debe ser USUARIO o PROFESOR");
        }
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new ConflictException("El nombre de usuario ya existe");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        String ruta = null;
        if (imagenPerfil != null && !imagenPerfil.isEmpty()) {
             ruta = fileStorageService.saveFile(imagenPerfil, "imagenes_perfil");
        }
        Usuario entidad = usuarioMapper.dtoRegistroToEntity(usuario);
        entidad.setImagenPerfil(ruta);
        usuarioRepository.save(entidad);
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
               if (usuario.get().getCorreoElectronico().equals(username)){
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

    public UsuarioDTO modifyUsuario(String id, UsuarioModifyDTO usuarioModifyDTO, MultipartFile imagenPerfil) {
        Long idLong = Long.parseLong(id);
        Usuario usuario = this.usuarioRepository.findById(idLong)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioModifyDTO.getCorreoElectronico() != null) {
            if (!Validators.isValidEmail(usuarioModifyDTO.getCorreoElectronico())) {
                throw new ValidationException("El email que desea introducir no es válido");
            }
            usuario.setUsername(usuarioModifyDTO.getCorreoElectronico());
            usuario.setCorreoElectronico(usuarioModifyDTO.getCorreoElectronico());
        }

        if (usuarioModifyDTO.getNombre() != null) {
            usuario.setNombre(usuarioModifyDTO.getNombre());
        }

        if (usuarioModifyDTO.getApellidos() != null) {
            usuario.setApellidos(usuarioModifyDTO.getApellidos());
        }

        if (usuarioModifyDTO.getFechaNacimiento() != null) {
            LocalDate localDate = LocalDate.parse(usuarioModifyDTO.getFechaNacimiento());
            Date fechaNacimiento = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            usuario.setFechaNacimiento(fechaNacimiento);
        }

        if (usuarioModifyDTO.getNivel() != null) {
            usuario.setNivel(usuarioModifyDTO.getNivel());
        }

        if (imagenPerfil != null && !imagenPerfil.isEmpty()) {
            try {
                // Eliminar imagen anterior si existe
                if (usuario.getImagenPerfil() != null && !usuario.getImagenPerfil().isBlank()) {
                    Path previous = Paths.get("uploads", usuario.getImagenPerfil().replace("/uploads/", ""));
                    Files.deleteIfExists(previous);
                }

                // Guardar nueva imagen

                String nuevoNombre = fileStorageService.saveFile(imagenPerfil, "imagenes_perfil");

                usuario.setImagenPerfil(nuevoNombre);

            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la nueva imagen", e);
            }
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

    public UsuarioDTO getById(String id) {
        Long idLong = Long.parseLong(id);
        Usuario usuario = this.usuarioRepository.findById(idLong)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return UsuarioMapper.entityToDTO(usuario);
    }

    public List<UsuarioDTO> getProfesores() {
        List<Usuario> profesores = this.usuarioRepository.findByProfesorId(null);
        if(profesores.isEmpty()){
            throw new ResourceNotFoundException("No se han encontrado profesores");
        }
        return profesores.stream().map(UsuarioMapper::entityToDTO).collect(Collectors.toList());
    }

    public List<UsuarioDTO> getAlumnosDelProfesor(Long idProfesor) {
        Optional<Usuario> profesor = usuarioRepository.findById(idProfesor);
        if (profesor.isEmpty() || !profesor.get().getRol().equalsIgnoreCase("PROFESOR")) {
            throw new ResourceNotFoundException("Profesor no encontrado");
        }
        List<Usuario> alumnos = usuarioRepository.findByProfesorId(idProfesor);
        if (alumnos.isEmpty()) {
            throw new ResourceNotFoundException("No se han encontrado alumnos para el profesor indicado");
        }
        return alumnos.stream().map(UsuarioMapper::entityToDTO).collect(Collectors.toList());
    }
}

