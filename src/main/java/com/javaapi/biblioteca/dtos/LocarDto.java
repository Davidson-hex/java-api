package com.javaapi.biblioteca.dtos;

import javax.validation.constraints.NotBlank;

public class LocarDto {
    @NotBlank
    private String previsao_devolucao;

    public String getPrevisao_devolucao() {
        return previsao_devolucao;
    }

    public void setPrevisao_devolucao(String previsao_devolucao) {
        this.previsao_devolucao = previsao_devolucao;
    }
}
