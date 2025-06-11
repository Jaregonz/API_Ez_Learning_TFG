package com.es.API_REST_Ez_Learning.service;

import com.es.API_REST_Ez_Learning.dto.EntregaDTO;
import com.es.API_REST_Ez_Learning.dto.EntregaFeedbackDTO;
import com.es.API_REST_Ez_Learning.dto.ExamenDTO;
import com.es.API_REST_Ez_Learning.model.EntregaExamen;
import com.es.API_REST_Ez_Learning.model.Examen;
import com.es.API_REST_Ez_Learning.model.Usuario;
import com.es.API_REST_Ez_Learning.repository.EntregaExamenRepository;
import com.es.API_REST_Ez_Learning.repository.ExamenRepository;
import com.es.API_REST_Ez_Learning.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExamenService {
    private final ExamenRepository examenRepository;
    private final EntregaExamenRepository entregaRepository;
    private final UsuarioRepository usuarioRepository;
    private final FileStorageService fileStorageService;

    public ExamenService(ExamenRepository examenRepository,
                         EntregaExamenRepository entregaRepository,
                         UsuarioRepository usuarioRepository,
                         FileStorageService fileStorageService) {
        this.examenRepository = examenRepository;
        this.entregaRepository = entregaRepository;
        this.usuarioRepository = usuarioRepository;
        this.fileStorageService = fileStorageService;
    }

    public void crearExamen(String titulo, LocalDate fechaCierre, MultipartFile archivo, Principal principal) {
        Usuario profesor = usuarioRepository.findByUsername(principal.getName()).orElseThrow();
        String archivoRuta = fileStorageService.saveFile(archivo, "examen");
        Examen examen = new Examen();
        examen.setTitulo(titulo);
        examen.setFechaCierre(fechaCierre);
        examen.setArchivoRuta(archivoRuta);
        examen.setProfesor(profesor);
        examenRepository.save(examen);
    }

    public List<ExamenDTO> listarExamenes(Principal principal) {
        Usuario usuario = usuarioRepository.findByUsername(principal.getName()).orElseThrow();
        List<Examen> examenes = examenRepository.findAll();
        return examenes.stream()
                .filter(e -> {
                    Long idProfesorExamen = e.getProfesor().getId();
                    Long idUsuario = usuario.getId();
                    return (usuario.getRol().equals("PROFESOR") && idProfesorExamen.equals(idUsuario)) ||
                            (usuario.getProfesor() != null && idProfesorExamen.equals(usuario.getProfesor().getId()));
                })
                .map(e -> {
                    ExamenDTO dto = new ExamenDTO();
                    dto.setId(e.getId());
                    dto.setTitulo(e.getTitulo());
                    dto.setArchivoRuta(e.getArchivoRuta());
                    dto.setFechaCierre(e.getFechaCierre());
                    return dto;
                }).collect(Collectors.toList());
    }

    public void subirEntrega(Long examenId, MultipartFile archivo, Principal principal) {
        Usuario alumno = usuarioRepository.findByUsername(principal.getName()).orElseThrow();
        Examen examen = examenRepository.findById(examenId).orElseThrow();
        if (LocalDate.now().isAfter(examen.getFechaCierre())) {
            throw new IllegalStateException("Fecha de entrega pasada");
        }
        EntregaExamen entrega = entregaRepository.findByExamenIdAndAlumnoId(examenId, alumno.getId())
                .orElse(new EntregaExamen());
        String ruta = fileStorageService.saveFile(archivo, "entregas");
        entrega.setArchivoRespuestaRuta(ruta);
        entrega.setExamen(examen);
        entrega.setAlumno(alumno);
        entrega.setFechaEntrega(LocalDate.now());
        entregaRepository.save(entrega);
    }

    public List<EntregaDTO> verEntregas(Long examenId, Principal principal) {
        Examen examen = examenRepository.findById(examenId).orElseThrow();
        Usuario profesor = usuarioRepository.findByUsername(principal.getName()).orElseThrow();
        List<Usuario> alumnos = usuarioRepository.findByProfesorId(profesor.getId());
        List<EntregaExamen> entregas = entregaRepository.findByExamenId(examenId);
        List<EntregaDTO> resultado = new ArrayList<>();

        for (Usuario alumno : alumnos) {
            Optional<EntregaExamen> entregaOpt = entregas.stream()
                    .filter(e -> e.getAlumno().getId().equals(alumno.getId()))
                    .findFirst();
            if (entregaOpt.isPresent()) {
                EntregaExamen e = entregaOpt.get();
                resultado.add(new EntregaDTO(
                        e.getId(),
                        alumno.getNombre() + " " + alumno.getApellidos(),
                        e.getArchivoRespuestaRuta(),
                        e.getComentario(),
                        e.getAprobado(),
                        examen.getId(),
                        alumno.getId()
                ));
            } else {
                resultado.add(new EntregaDTO(
                        null,
                        alumno.getNombre() + " " + alumno.getApellidos(),
                        null,
                        null,
                        null,
                        examen.getId(),
                        alumno.getId()
                ));
            }
        }

        return resultado;
    }


    public ExamenDTO verExamen(Long id, Principal principal) {
        Examen examen = examenRepository.findById(id).orElseThrow();
        ExamenDTO dto = new ExamenDTO();
        dto.setId(examen.getId());
        dto.setTitulo(examen.getTitulo());
        dto.setArchivoRuta(examen.getArchivoRuta());
        dto.setFechaCierre(examen.getFechaCierre());
        return dto;
    }

    public void corregirEntrega(Long id, EntregaFeedbackDTO dto) {
        EntregaExamen entrega = entregaRepository.findById(id).orElseThrow();
        entrega.setComentario(dto.getComentario());
        entrega.setAprobado(dto.getAprobado());
        entregaRepository.save(entrega);
    }

    public EntregaDTO verEntregaAlumno(Long examenId,Long idAlumno) {
        EntregaDTO entregaDTO = new EntregaDTO();
        Optional<EntregaExamen> entregaAlumnoOptional = entregaRepository.findByExamenIdAndAlumnoId(examenId,idAlumno);
        if (entregaAlumnoOptional.isEmpty()) {
            entregaDTO = null;
        }else{
            EntregaExamen entregaAlumno = entregaAlumnoOptional.get();
            entregaDTO.setId(entregaAlumno.getId());
            entregaDTO.setAlumnoNombre(entregaAlumno.getAlumno().getNombre() + " " + entregaAlumno.getAlumno().getApellidos());
            entregaDTO.setArchivoRespuestaRuta(entregaAlumno.getArchivoRespuestaRuta());
            entregaDTO.setComentario(entregaAlumno.getComentario());
            entregaDTO.setAprobado(entregaAlumno.getAprobado());
        }


        return entregaDTO;
    }
}
