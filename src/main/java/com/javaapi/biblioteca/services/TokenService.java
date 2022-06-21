package com.javaapi.biblioteca.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TokenService {

    private HttpServletRequest request;


    public TokenService(HttpServletRequest request) {
        this.request = request;
    }

    private String token;
    private long idUsuarioCadastro;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getIdUsuarioCadastro() {
        return idUsuarioCadastro;
    }
    public void setIdUsuarioCadastro(long idUsuarioCadastro) {
        this.idUsuarioCadastro = idUsuarioCadastro;
    }

    public long getClaim() {
        String claim = JWT.require(Algorithm.HMAC512("secret")).build()
                .verify(request.getHeader("Authorization").replace("Bearer ", ""))
                .getClaim("id")
                .asString();
        setIdUsuarioCadastro(Long.parseLong(claim));
        return Long.parseLong(claim);
    }
}
