package com.es.API_REST_Ez_Learning.controller;

import com.es.API_REST_Ez_Learning.dto.TestDTO;
import com.es.API_REST_Ez_Learning.service.TestService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestController {
    @Autowired
    private TestService testService;

    @PostMapping("/")
    public ResponseEntity<TestDTO> insertTest(@RequestBody TestDTO testDTO, Authentication authentication){
        return new ResponseEntity<>(this.testService.insertTest(testDTO,authentication), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<TestDTO>> getTests(Authentication authentication){
        return new ResponseEntity<List<TestDTO>>(this.testService.getTests(authentication),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestDTO> getTest(@PathVariable String id){
        return new ResponseEntity<TestDTO>(this.testService.getTest(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestDTO> updateTest(@PathVariable String id, @RequestBody TestDTO testDTO){
        return new ResponseEntity<TestDTO>(this.testService.updateTest(id,testDTO),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<TestDTO> deleteTest(@PathVariable String id){
        return new ResponseEntity<TestDTO>(this.testService.deleteTest(id),HttpStatus.NO_CONTENT);
    }

    @GetMapping("/filtrar-tests")
    public ResponseEntity<List<TestDTO>> getFilteredTests(
                                                           @RequestParam(required = false) String titulo,
                                                           @RequestParam(required = false) String dificultad,
                                                           @RequestParam(required = false) String tipo) {
        return new ResponseEntity<List<TestDTO>>(this.testService.filtrarTests(titulo,dificultad,tipo),HttpStatus.OK);
    }



}
