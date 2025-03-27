package com.es.API_REST_Ez_Learning.controller;

import com.es.API_REST_Ez_Learning.dto.PreguntaDTO;
import com.es.API_REST_Ez_Learning.service.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/preguntas")
public class PreguntaController {

    @Autowired
    private PreguntaService preguntaService;

    @GetMapping("/")
    public ResponseEntity<List<PreguntaDTO>>findAll() {
        return new ResponseEntity<List<PreguntaDTO>>(preguntaService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PreguntaDTO>findById(@PathVariable Long id) {
        return new ResponseEntity<PreguntaDTO>(preguntaService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<PreguntaDTO> create(@RequestBody PreguntaDTO preguntaDTO) {
        return new ResponseEntity<>(preguntaService.save(preguntaDTO),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PreguntaDTO> update(@PathVariable Long id, @RequestBody PreguntaDTO preguntaDTO) {

       return new ResponseEntity<>(preguntaService.updatePregunta(preguntaDTO,id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PreguntaDTO> delete(@PathVariable Long id) {
        return new ResponseEntity<PreguntaDTO>(this.preguntaService.deleteById(id),HttpStatus.NO_CONTENT);
    }
}
