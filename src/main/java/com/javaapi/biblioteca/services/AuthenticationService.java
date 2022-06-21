/*
package com.javaapi.biblioteca.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;

public class AuthenticationService {

    static final long EXPIRATIONTIME = 600_000;
    static final String SIGNIGKEY = "Secret";
    static final String PREFIX = "Bearer";


    static public void addToken(HttpServletResponse res, String email) {
        String t = "";
        String JwtToken = Jwts.builder().setSubject(email)
                .setId(t)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SIGNIGKEY)
                .compact();
        res.addHeader("Authorization", PREFIX + " " + JwtToken);
        res.addHeader("Access-Control-Expose-Headers", "Authorization");

    }

    static public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            String email = Jwts.parser()
                    .setSigningKey(SIGNIGKEY)
                    .parseClaimsJws(token.replace(PREFIX, ""))
                    .getBody()
                    .getSubject();
            if (email != null)
                return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());

        }
        return null;
    }
}

 */


