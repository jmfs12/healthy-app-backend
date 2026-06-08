package com.backend.java.saude_e_bem_estar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record UsuarioRequestDTO(
    @NotBlank(message = "O nome completo é obrigatório.")
    @Size(max = 100, message = "O nome completo não pode exceder 100 caracteres.")
    String nome_completo,

    @NotBlank(message = "O CPF é obrigatório.")
    String cpf,

    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento deve ser uma data passada.")
    LocalDate data_nascimento,

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    String email,

    @NotBlank(message = "O telefone é obrigatório.")
    String telefone,

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    String senha
) {}
