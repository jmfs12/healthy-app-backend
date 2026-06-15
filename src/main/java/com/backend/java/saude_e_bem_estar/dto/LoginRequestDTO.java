package com.backend.java.saude_e_bem_estar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para autenticação de usuário")
public record LoginRequestDTO(
    @Schema(description = "Endereço de e-mail do usuário", example = "joao.silva@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    String email,

    @Schema(description = "Senha do usuário", example = "senhaForte123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "A senha é obrigatória.")
    String senha
) {}
