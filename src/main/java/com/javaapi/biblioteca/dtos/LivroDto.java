package com.javaapi.biblioteca.dtos;

import javax.validation.constraints.NotBlank;

public class LivroDto {

    @NotBlank
    private String autor;
    @NotBlank
    private String titulo;

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
