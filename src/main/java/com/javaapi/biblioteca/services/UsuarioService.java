package com.javaapi.biblioteca.services;

import com.javaapi.biblioteca.models.UsuarioModel;
import com.javaapi.biblioteca.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class UsuarioService {

    final UsuarioRepository repository;
    final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public boolean criarUsuario(UsuarioModel usuario) {
        boolean valid = repository.findByEmail(usuario.getEmail()).isEmpty();
        if (valid) {
            usuario.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
            usuario.setSenha(encoder.encode(usuario.getSenha()));
            repository.save(usuario);
            return true;
        } else {
            return false;
        }
    }

    public boolean loginUsuario(UsuarioModel usuario) {
        if (usuario.getEmail() == null && usuario.getSenha() == null) {
            return false;
        } else {
            Optional<UsuarioModel> optUsuario = repository.findByEmail(usuario.getEmail());
                if (optUsuario.isEmpty()) {
                    return false;
                }
                UsuarioModel user = optUsuario.get();
                boolean valid = encoder.matches(usuario.getSenha(), user.getSenha());

                return valid;
        }

    }

    public UsuarioModel findByEmail(String email) {
        Optional<UsuarioModel> optUser = repository.findByEmail(email);
        UsuarioModel user = optUser.get();
        return user;
    }

}

