package com.backend.java.saude_e_bem_estar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "Dados para atualização do perfil do usuário")
public record UsuarioUpdateRequestDTO(
    @Schema(description = "Nome completo do usuário", example = "João Silva Santos")
    @Size(max = 100, message = "O nome completo não pode exceder 100 caracteres.")
    String nome_completo,

    @Schema(description = "Data de nascimento do usuário (deve ser no passado)", example = "1990-05-15")
    @Past(message = "A data de nascimento deve ser uma data passada.")
    LocalDate data_nascimento,

    @Schema(description = "Telefone para contato", example = "(11) 99999-9999")
    String telefone,

    @Schema(description = "Nova senha do usuário (mínimo 6 caracteres)", example = "novaSenha123")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    String senha
) {}
