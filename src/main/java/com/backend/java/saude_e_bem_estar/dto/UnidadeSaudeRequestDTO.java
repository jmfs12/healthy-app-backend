package com.backend.java.saude_e_bem_estar.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UnidadeSaudeRequestDTO {

    @NotBlank
    private String nome_unidade;

    @NotBlank
    private String tipo;

    @NotBlank
    private String endereco_completo;

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;
}