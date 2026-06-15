package com.backend.java.saude_e_bem_estar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "Dados para cadastro de novo usuário")
public record UsuarioRequestDTO(
    @Schema(description = "Nome completo do usuário", example = "João da Silva Santos", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O nome completo é obrigatório.")
    @Size(max = 100, message = "O nome completo não pode exceder 100 caracteres.")
    String nome_completo,

    @Schema(description = "CPF do usuário (apenas números ou formatado)", example = "12345678909", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O CPF é obrigatório.")
    String cpf,

    @Schema(description = "Data de nascimento do usuário (deve ser no passado)", example = "1995-10-25", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento deve ser uma data passada.")
    LocalDate data_nascimento,

    @Schema(description = "Endereço de e-mail do usuário (único)", example = "joao.silva@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    String email,

    @Schema(description = "Telefone para contato", example = "(11) 99999-9999", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O telefone é obrigatório.")
    String telefone,

    @Schema(description = "Senha para login (mínimo 6 caracteres)", example = "senhaForte123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    String senha
) {}
