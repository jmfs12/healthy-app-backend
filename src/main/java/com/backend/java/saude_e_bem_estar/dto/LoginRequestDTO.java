package com.backend.java.saude_e_bem_estar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    String email,

    @NotBlank(message = "A senha é obrigatória.")
    String senha
) {}
