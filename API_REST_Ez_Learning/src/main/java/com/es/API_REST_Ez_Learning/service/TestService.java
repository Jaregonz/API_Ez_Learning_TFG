package com.es.API_REST_Ez_Learning.service;

import com.es.API_REST_Ez_Learning.dto.PreguntaDTO;
import com.es.API_REST_Ez_Learning.dto.RespuestaDTO;
import com.es.API_REST_Ez_Learning.dto.TestDTO;
import com.es.API_REST_Ez_Learning.errors.InternalServerError;
import com.es.API_REST_Ez_Learning.errors.ResourceNotFoundException;
import com.es.API_REST_Ez_Learning.errors.ValidationException;
import com.es.API_REST_Ez_Learning.mapper_interfaces.RespuestaMapper;
import com.es.API_REST_Ez_Learning.model.Pregunta;
import com.es.API_REST_Ez_Learning.model.Respuesta;
import com.es.API_REST_Ez_Learning.model.Test;
import com.es.API_REST_Ez_Learning.model.Usuario;
import com.es.API_REST_Ez_Learning.repository.PreguntaRepository;
import com.es.API_REST_Ez_Learning.repository.RespuestaRepository;
import com.es.API_REST_Ez_Learning.repository.TestRepository;
import com.es.API_REST_Ez_Learning.repository.UsuarioRepository;
import com.es.API_REST_Ez_Learning.specifications.TestSpecification;
import com.es.API_REST_Ez_Learning.util.PreguntaMapper;
import com.es.API_REST_Ez_Learning.util.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestService {


    private TestRepository testRepository;
    private UsuarioRepository usuarioRepository;
    private PreguntaRepository preguntaRepository;
    private PreguntaMapper preguntaMapper;
    private TestMapper testMapper;
    private RespuestaMapper respuestaMapper;
    private RespuestaRepository respuestaRepository;

    @Autowired
    public TestService(TestRepository testRepository, RespuestaMapper respuestaMapper, PreguntaRepository preguntaRepository, TestMapper testMapper, PreguntaMapper preguntaMapper, UsuarioRepository usuarioRepository, RespuestaRepository respuestaRepository) {
        this.testRepository = testRepository;
        this.respuestaMapper = respuestaMapper;
        this.preguntaRepository = preguntaRepository;
        this.testMapper = testMapper;
        this.preguntaMapper = preguntaMapper;
        this.usuarioRepository = usuarioRepository;
        this.respuestaRepository = respuestaRepository;
    }

    public TestDTO insertTest(TestDTO testDTO, Authentication authentication){
        Usuario creador = this.usuarioRepository.findByUsername(authentication.getName()).get();
        testDTO.setUsuario(creador.getId());
        if(testDTO.getTiempo() > 30){
            throw new ValidationException("El tiempo no puede ser mayor a 30 minutos");
        }
        if(testDTO.getCantidadPreguntas() > 20){
            throw new ValidationException("La cantidad de preguntas no puede exceder de 20");
        }
        Test test = testMapper.dtoToEntity(testDTO,creador);
        List<Pregunta> preguntas = new ArrayList<>();

        for (PreguntaDTO preguntaDTO : testDTO.getPreguntas()) {
            Pregunta pregunta = new Pregunta();
            pregunta.setContenidoPregunta(preguntaDTO.getContenidoPregunta());

            List<Respuesta> respuestas = new ArrayList<>();
            for (RespuestaDTO respuestaDTO : preguntaDTO.getRespuestas()) {
                Respuesta respuesta = new Respuesta();
                respuesta.setContenido(respuestaDTO.getContenido());
                respuesta.setEsCorrecta(respuestaDTO.isEsCorrecta());
                respuesta.setPregunta(pregunta);
                respuestas.add(respuesta);
            }

            pregunta.setRespuestas(respuestas);

            preguntas.add(pregunta);
        }

        test.setPreguntas(preguntas);

        return testMapper.entityToDTO(testRepository.save(test));
    }

    public List<TestDTO> getTests(Authentication authentication) {
        String rol = authentication.getAuthorities().stream().findFirst().toString();
        Usuario usuario = this.usuarioRepository.findByUsername(authentication.getName()).get();
        if(rol.equalsIgnoreCase("role_profesor")){
            List<TestDTO> testsDTOs = new ArrayList<>();
            for(Test test: this.testRepository.findAll()){
                testsDTOs.add(testMapper.entityToDTO(test));
            }
            return testsDTOs;

        }else{
            List<TestDTO> testsDTOs = new ArrayList<>();
            for(Test test: this.testRepository.findAll()){
                testsDTOs.add(testMapper.entityToDTO(test));
            }
            return TestMapper.filtrarPorNivel(usuario.getNivel(),testsDTOs);

        }
    }


    public TestDTO getTest(String id) {
        try{
            Long idLong = Long.parseLong(id);
            return testMapper.entityToDTO(this.testRepository.findById(idLong).get());
        } catch (Exception e) {
            throw new ValidationException("El id introducido no es válido");
        }

    }

    public TestDTO updateTest(String id, TestDTO testDTO) {
        try {
            Long idLong = Long.parseLong(id);
            Optional<Test> optionalTest = this.testRepository.findById(idLong);

            if (optionalTest.isPresent()) {
                Test test = optionalTest.get();

                if (testDTO.getPreguntas() != null) {
                    List<Pregunta> preguntas = new ArrayList<>();

                    for (PreguntaDTO preguntaDTO : testDTO.getPreguntas()) {
                        if (preguntaDTO.getId() != null) {

                            Optional<Pregunta> preguntaExistente = this.preguntaRepository.findById(preguntaDTO.getId());

                            if (preguntaExistente.isPresent()) {
                                Pregunta pregunta = preguntaExistente.get();
                                pregunta.setContenidoPregunta(preguntaDTO.getContenidoPregunta());

                                // ACTUALIZAR RESPUESTAS
                                List<Respuesta> respuestasActualizadas = new ArrayList<>();
                                for (RespuestaDTO respuestaDTO : preguntaDTO.getRespuestas()) {
                                    respuestasActualizadas.add(respuestaMapper.dtoToEntity(respuestaDTO));
                                }
                                pregunta.setRespuestas(respuestasActualizadas);

                                preguntas.add(pregunta);
                            }
                        } else {
                            // Si no tiene ID, es una nueva pregunta y se debe crear
                            Pregunta nuevaPregunta = preguntaMapper.dtoToEntity(preguntaDTO);
                            this.preguntaRepository.save(nuevaPregunta);
                            preguntas.add(nuevaPregunta);
                        }
                    }

                    test.getPreguntas().clear();
                    test.getPreguntas().addAll(preguntas);
                }

                // Resto de validaciones y actualizaciones
                if (testDTO.getTitulo() != null) {
                    test.setTitulo(testDTO.getTitulo());
                }
                if (testDTO.getTipo() != null) {
                    test.setTipo(testDTO.getTipo());
                }
                if (testDTO.getTiempo() != test.getTiempo()) {
                    if (testDTO.getTiempo() > 30) {
                        throw new ValidationException("El tiempo no puede ser mayor de 30 minutos");
                    }
                    test.setTiempo(testDTO.getTiempo());
                }
                if (testDTO.getUsuario() != test.getUsuario().getId()) {
                    Optional<Usuario> userOptional = this.usuarioRepository.findById(testDTO.getUsuario());
                    if (userOptional.isPresent()) {
                        test.setUsuario(userOptional.get());
                    } else {
                        throw new RuntimeException("El usuario introducido no es válido");
                    }
                }
                if (testDTO.getCantidadPreguntas() != test.getCantidadPreguntas()) {
                    if (testDTO.getCantidadPreguntas() > 20) {
                        throw new ValidationException("El test no puede tener más de 20 preguntas");
                    }
                    test.setCantidadPreguntas(testDTO.getCantidadPreguntas());
                }
                if (testDTO.getDificultad() != null) {
                    test.setDificultad(testDTO.getDificultad());
                }

                this.testRepository.save(test);
                return testMapper.entityToDTO(test);
            } else {
                throw new ResourceNotFoundException("No existe el test que quiere modificar");
            }
        } catch (Exception e) {
            throw new InternalServerError("Se ha producido un error interno");
        }
    }

    public TestDTO deleteTest(String id) {
        if(!id.isEmpty() || !id.isBlank()){
            Long idLong = Long.parseLong(id);
            Optional<Test> test = this.testRepository.findById(idLong);
            this.testRepository.delete(test.get());
            return testMapper.entityToDTO(test.get());
        }else{
            throw new ValidationException("El id no es válido");
        }
    }

    public List<TestDTO> filtrarTests(String titulo, String dificultad, String tipo) {
        Specification<Test> spec = TestSpecification.filtrarTests(titulo, dificultad, tipo);
        List<TestDTO> testsFiltradosDTO = new ArrayList<>();
        for (Test test : testRepository.findAll(spec)) {
            testsFiltradosDTO.add(testMapper.entityToDTO(test));
        }
        return testsFiltradosDTO;
    }
}
