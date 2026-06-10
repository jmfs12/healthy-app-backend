package com.backend.java.saude_e_bem_estar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UnidadeSaudeRequestDTO(
        @NotBlank(message = "O nome da unidade é obrigatório.")
        @Size(max = 100, message = "O nome da unidade não pode exceder 100 caracteres.")
        String nome_unidade,

        @NotBlank(message = "O tipo da unidade é obrigatório.")
        @Size(max = 100, message = "O tipo não pode exceder 100 caracteres.")
        String tipo,

        @NotBlank(message = "O endereço completo é obrigatório.")
        String endereco_completo,

        @NotNull(message = "A latitude é obrigatória.")
        BigDecimal latitude,

        @NotNull(message = "A longitude é obrigatória.")
        BigDecimal longitude
) {}