/*
package com.javaapi.biblioteca.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaapi.biblioteca.dtos.LoginDto;
import com.javaapi.biblioteca.services.AuthenticationService;
import com.javaapi.biblioteca.services.UsuarioService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    public LoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        LoginDto dto = new ObjectMapper()
                .readValue(req.getInputStream(), LoginDto.class);
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                dto.getEmail(),
                dto.getSenha(),
                Collections.emptyList()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        AuthenticationService.addToken(response, authResult.getName());

    }
}

 */

