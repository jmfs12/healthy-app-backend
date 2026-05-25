package com.backend.java.saude_e_bem_estar.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UnidadeSaudeResponseDTO {

    private Long id_unidade;

    private String nome_unidade;

    private String tipo;

    private String endereco_completo;

    private BigDecimal latitude;

    private BigDecimal longitude;
}