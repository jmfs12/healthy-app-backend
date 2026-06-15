package com.backend.java.saude_e_bem_estar.dto;

import com.backend.java.saude_e_bem_estar.entities.UnidadeSaude;

import java.math.BigDecimal;

public record UnidadeSaudeResponseDTO(
        long id_unidade,
        String nome_unidade,
        String tipo,
        String endereco_completo,
        BigDecimal latitude,
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