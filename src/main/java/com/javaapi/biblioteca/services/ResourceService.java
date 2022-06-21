package com.javaapi.biblioteca.services;

import com.javaapi.biblioteca.resource.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {

    public ResponseEntity<Object> sucesso(String message) {
        GenericResponse genericResponse = new GenericResponse(HttpStatus.OK.value(), message);
        return ResponseEntity.status(HttpStatus.OK).body(genericResponse);
    }

    public ResponseEntity<Object> unauthorized(String message) {
        GenericResponse genericResponse = new GenericResponse(HttpStatus.UNAUTHORIZED.value(), message);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(genericResponse);
    }

    public ResponseEntity<Object> notFound(String message) {
        GenericResponse genericResponse = new GenericResponse(HttpStatus.NOT_FOUND.value(), message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericResponse);
    }
}
