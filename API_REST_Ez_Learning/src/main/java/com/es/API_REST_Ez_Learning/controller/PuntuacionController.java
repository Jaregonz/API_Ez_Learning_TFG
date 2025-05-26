package com.es.API_REST_Ez_Learning.controller;

import com.es.API_REST_Ez_Learning.dto.PreguntaDTO;
import com.es.API_REST_Ez_Learning.dto.PuntuacionDTO;
import com.es.API_REST_Ez_Learning.dto.PuntuacionProfileDTO;
import com.es.API_REST_Ez_Learning.service.PuntuacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/puntuaciones")
public class PuntuacionController {
    @Autowired
    private PuntuacionService puntuacionService;

    @GetMapping("/")
    public ResponseEntity<List<PuntuacionDTO>> findAll() {
        return new ResponseEntity<List<PuntuacionDTO>>(puntuacionService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<PuntuacionProfileDTO>> findByUsuarioId(@PathVariable Long id) {
        return new ResponseEntity<List<PuntuacionProfileDTO>>(puntuacionService.findByUsuarioId(id), HttpStatus.OK);
    }

    @PostMapping("/submit-puntuacion")
    public ResponseEntity<PuntuacionDTO> createPuntuacion(@RequestBody PuntuacionDTO puntuacionDTO) {
        PuntuacionDTO createdPuntuacion = puntuacionService.createPuntuacion(puntuacionDTO);
        return new ResponseEntity<>(createdPuntuacion, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePuntuacion(@PathVariable Long id) {
        puntuacionService.deletePuntuacion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PuntuacionDTO> updatePuntuacion(@PathVariable Long id, @RequestBody PuntuacionDTO puntuacionDTO) {
        PuntuacionDTO updatedPuntuacion = puntuacionService.updatePuntuacion(id, puntuacionDTO);
        return new ResponseEntity<>(updatedPuntuacion, HttpStatus.OK);
    }
}
