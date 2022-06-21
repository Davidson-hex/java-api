package com.javaapi.biblioteca.controllers;

import com.javaapi.biblioteca.dtos.UsuarioDto;
import com.javaapi.biblioteca.models.UsuarioModel;
import com.javaapi.biblioteca.repositories.UsuarioRepository;
import com.javaapi.biblioteca.services.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private  final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioRepository repository, PasswordEncoder encoder, UsuarioService usuarioService) {
        this.repository = repository;
        this.encoder = encoder;
        this.usuarioService = usuarioService;
    }


    @PostMapping("/criar")
    public ResponseEntity<Object> criarUsuario(@RequestBody @Valid UsuarioDto usuarioDto) {
        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(usuarioDto, usuarioModel);
        var user = usuarioService.criarUsuario(usuarioModel);
        if (user) {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioModel);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário já existe");
        }
    }
}
