package com.javaapi.biblioteca.dtos.views;

import java.time.LocalDateTime;

public class LivroView {
    private Integer id;
    private String autor;
    private String titulo;
    private LocalDateTime dataCriacao;
    private long ativo;
    private long idUsuarioCadastro;

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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public long getAtivo() {
        return ativo;
    }

    public void setAtivo(long ativo) {
        this.ativo = ativo;
    }

    public long getIdUsuarioCadastro() {
        return idUsuarioCadastro;
    }

    public void setIdUsuarioCadastro(long idUsuarioCadastro) {
        this.idUsuarioCadastro = idUsuarioCadastro;
    }
}
