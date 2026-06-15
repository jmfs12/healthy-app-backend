package com.backend.java.saude_e_bem_estar.dto;

import com.backend.java.saude_e_bem_estar.entities.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Dados de retorno do perfil do usuário")
public record UsuarioResponseDTO(
    @Schema(description = "Identificador único do usuário", example = "1")
    long id_usuario,

    @Schema(description = "Nome completo do usuário", example = "João da Silva Santos")
    String nome_completo,

    @Schema(description = "CPF do usuário", example = "12345678909")
    String cpf,

    @Schema(description = "Data de nascimento do usuário", example = "1995-10-25")
    LocalDate data_nascimento,

    @Schema(description = "Endereço de e-mail do usuário", example = "joao.silva@email.com")
    String email,

    @Schema(description = "Telefone para contato", example = "(11) 99999-9999")
    String telefone
) {
    public static UsuarioResponseDTO fromEntity(Usuario usuario) {
        return new UsuarioResponseDTO(
            usuario.getIdUsuario(),
            usuario.getNome_completo(),
            usuario.getCpf(),
            usuario.getData_nascimento(),
            usuario.getEmail(),
            usuario.getTelefone()
        );
    }
}
