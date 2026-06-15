package com.backend.java.saude_e_bem_estar.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de retorno após autenticação com sucesso")
public record LoginResponseDTO(
    @Schema(description = "Token JWT de autorização", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    String token,

    @Schema(description = "Endereço de e-mail do usuário autenticado", example = "joao.silva@email.com")
    String email
) {}
