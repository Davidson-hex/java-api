/*
package com.javaapi.biblioteca.services;

import com.javaapi.biblioteca.models.UsuarioModel;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final UsuarioService service;

    public UserDetailsServiceImpl(UsuarioService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioModel currentUser = service.findByEmail(email);
        UserDetails user = new org.springframework.security.core.userdetails.User(
                email, currentUser.getSenha(), true, true, true, true, AuthorityUtils.createAuthorityList()
        );
        return user;
    }
}

 */


