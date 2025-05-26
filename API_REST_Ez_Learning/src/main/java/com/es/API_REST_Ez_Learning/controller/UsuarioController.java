package com.es.API_REST_Ez_Learning.controller;

import com.es.API_REST_Ez_Learning.dto.UsuarioDTO;
import com.es.API_REST_Ez_Learning.dto.UsuarioLoginDTO;
import com.es.API_REST_Ez_Learning.dto.UsuarioModifyDTO;
import com.es.API_REST_Ez_Learning.dto.UsuarioRegistroDTO;
import com.es.API_REST_Ez_Learning.model.Usuario;
import com.es.API_REST_Ez_Learning.service.TokenService;
import com.es.API_REST_Ez_Learning.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public String login ( @RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioLoginDTO.getUsername(),usuarioLoginDTO.getPassword())
        );
        String token =  tokenService.generateToken(authentication);
        return token;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioRegistroDTO> register(
            @RequestBody UsuarioRegistroDTO usuarioRegisterDTO) {
        UsuarioRegistroDTO newUsuario = usuarioService.registerUser(usuarioRegisterDTO);
        return new ResponseEntity<UsuarioRegistroDTO>(newUsuario, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable String username, Authentication authentication){

        String role = authentication.getAuthorities().stream().toList().get(0).toString();

        UsuarioDTO usuarioDTO = this.usuarioService.getByUsername(role,authentication.getName(), username);
        return new ResponseEntity<>(usuarioDTO,HttpStatus.OK);
    }

    @GetMapping("/perfil-usuario")
    public ResponseEntity<UsuarioDTO> getUsuarioAutenticado(Authentication authentication) {
        String role = authentication.getAuthorities().stream().findFirst().orElseThrow().getAuthority();
        String username = authentication.getName();

        UsuarioDTO usuarioDTO = this.usuarioService.getByUsername(role, username, username);
        return ResponseEntity.ok(usuarioDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioDTO> deleteUsuario(@PathVariable String id){
        return new ResponseEntity<>(this.usuarioService.deleteUser(id), HttpStatus.NO_CONTENT);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable String id, @RequestBody UsuarioModifyDTO usuarioModifyDTO){
        return new ResponseEntity<>(this.usuarioService.modifyUsuario(id,usuarioModifyDTO), HttpStatus.OK);

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioDTO> getById(@PathVariable String id) {
        return new ResponseEntity<>(this.usuarioService.getById(id), HttpStatus.OK);
    }

}
