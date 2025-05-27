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
import java.util.List;
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
        String archivoRuta = fileStorageService.saveFile(archivo);
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
                .filter(e -> usuario.getRol().equals("ALUMNO") || e.getProfesor().getId().equals(usuario.getId()))
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
        String ruta = fileStorageService.saveFile(archivo);
        entrega.setArchivoRespuestaRuta(ruta);
        entrega.setExamen(examen);
        entrega.setAlumno(alumno);
        entrega.setFechaEntrega(LocalDate.now());
        entregaRepository.save(entrega);
    }

    public List<EntregaDTO> verEntregas(Long examenId, Principal principal) {
        Examen examen = examenRepository.findById(examenId).orElseThrow();
        List<EntregaExamen> entregas = entregaRepository.findByExamenId(examenId);
        return entregas.stream().map(e -> {
            EntregaDTO dto = new EntregaDTO();
            dto.setId(e.getId());
            dto.setAlumnoNombre(e.getAlumno().getNombre() + " " + e.getAlumno().getApellidos());
            dto.setArchivoRespuestaRuta(e.getArchivoRespuestaRuta());
            dto.setComentario(e.getComentario());
            dto.setAprobado(e.getAprobado());
            return dto;
        }).collect(Collectors.toList());
    }

    public void corregirEntrega(Long id, EntregaFeedbackDTO dto) {
        EntregaExamen entrega = entregaRepository.findById(id).orElseThrow();
        entrega.setComentario(dto.getComentario());
        entrega.setAprobado(dto.getAprobado());
        entregaRepository.save(entrega);
    }

    public EntregaFeedbackDTO verFeedback(Long examenId, Principal principal) {
        Usuario alumno = usuarioRepository.findByUsername(principal.getName()).orElseThrow();
        EntregaExamen entrega = entregaRepository.findByExamenIdAndAlumnoId(examenId, alumno.getId()).orElseThrow();
        EntregaFeedbackDTO dto = new EntregaFeedbackDTO();
        dto.setComentario(entrega.getComentario());
        dto.setAprobado(entrega.getAprobado());
        return dto;
    }
}
