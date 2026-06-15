package com.backend.java.saude_e_bem_estar.dto;

import com.backend.java.saude_e_bem_estar.entities.UnidadeSaude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Dados de retorno de unidade de saúde cadastrada")
public record UnidadeSaudeResponseDTO(
        @Schema(description = "Identificador único da unidade de saúde", example = "1")
        long id_unidade,

        @Schema(description = "Nome fantasia da unidade de saúde", example = "UBS Central")
        String nome_unidade,

        @Schema(description = "Tipo de estabelecimento de saúde", example = "UBS")
        String tipo,

        @Schema(description = "Endereço físico completo", example = "Av. Principal, 100 - Centro")
        String endereco_completo,

        @Schema(description = "Coordenada de latitude geográfica", example = "-23.550520")
        BigDecimal latitude,

        @Schema(description = "Coordenada de longitude geográfica", example = "-46.633308")
        BigDecimal longitude
) {
    public static UnidadeSaudeResponseDTO fromEntity(UnidadeSaude unidade) {
        return new UnidadeSaudeResponseDTO(
                unidade.getId_unidade(),
                unidade.getNome_unidade(),
                unidade.getTipo(),
                unidade.getEndereco_completo(),
                unidade.getLatitude(),
                unidade.getLongitude()
        );
    }
}