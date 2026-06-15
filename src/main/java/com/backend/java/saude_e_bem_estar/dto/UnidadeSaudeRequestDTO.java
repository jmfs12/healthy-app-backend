package com.backend.java.saude_e_bem_estar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Dados para cadastro/atualização de unidade de saúde")
public record UnidadeSaudeRequestDTO(
        @Schema(description = "Nome fantasia da unidade de saúde", example = "UBS Central", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "O nome da unidade é obrigatório.")
        @Size(max = 100, message = "O nome da unidade não pode exceder 100 caracteres.")
        String nome_unidade,

        @Schema(description = "Tipo da unidade (ex: Posto de Saúde, Hospital, UBS)", example = "UBS", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "O tipo da unidade é obrigatório.")
        @Size(max = 100, message = "O tipo não pode exceder 100 caracteres.")
        String tipo,

        @Schema(description = "Endereço completo da unidade de saúde", example = "Av. Principal, 100 - Centro", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "O endereço completo é obrigatório.")
        String endereco_completo,

        @Schema(description = "Coordenada de latitude geográfica", example = "-23.550520", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "A latitude é obrigatória.")
        BigDecimal latitude,

        @Schema(description = "Coordenada de longitude geográfica", example = "-46.633308", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "A longitude é obrigatória.")
        BigDecimal longitude
) {}