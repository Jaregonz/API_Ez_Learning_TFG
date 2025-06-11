package com.es.API_REST_Ez_Learning.controller;

import com.es.API_REST_Ez_Learning.dto.EntregaDTO;
import com.es.API_REST_Ez_Learning.dto.EntregaFeedbackDTO;
import com.es.API_REST_Ez_Learning.dto.ExamenDTO;
import com.es.API_REST_Ez_Learning.service.ExamenService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/examenes")
public class ExamenController {
    private final ExamenService examenService;

    public ExamenController(ExamenService examenService) {
        this.examenService = examenService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crearExamen(@RequestParam String titulo,
                                         @RequestParam LocalDate fechaCierre,
                                         @RequestParam MultipartFile archivo,
                                         Principal principal) {
        examenService.crearExamen(titulo, fechaCierre, archivo, principal);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<ExamenDTO> listarExamenes(Principal principal) {
        return examenService.listarExamenes(principal);
    }

    @PostMapping("/{id}/entrega")
    public ResponseEntity<?> subirEntrega(@PathVariable Long id,
                                          @RequestParam MultipartFile archivo,
                                          Principal principal) {
        examenService.subirEntrega(id, archivo, principal);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/entregas")
    public List<EntregaDTO> verEntregas (@PathVariable Long id, Principal principal) {
        return examenService.verEntregas(id, principal);
    }

    @GetMapping("/{id}")
    public ExamenDTO verExamen(@PathVariable Long id, Principal principal) {
        return examenService.verExamen(id, principal);
    }

    @GetMapping("/entrega-examen/{idExamen}/{idAlumno}")
    public EntregaDTO verEntregaAlumno(@PathVariable Long idExamen,@PathVariable Long idAlumno) {
        return examenService.verEntregaAlumno(idExamen,idAlumno);
    }

    @PutMapping("/entregas/{id}")
    public ResponseEntity<?> corregirEntrega(@PathVariable Long id,
                                             @RequestBody EntregaFeedbackDTO dto) {
        examenService.corregirEntrega(id, dto);
        return ResponseEntity.ok().build();
    }

}
