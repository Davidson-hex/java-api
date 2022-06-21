package com.javaapi.biblioteca.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "livros")
public class LivroModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String autor;
    @Column
    private String titulo;
    @Column
    private LocalDateTime dataCriacao;
    @Column
    private long ativo;
    @Column
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
