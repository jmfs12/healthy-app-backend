package com.backend.java.saude_e_bem_estar.dto;

import com.backend.java.saude_e_bem_estar.entities.Usuario;
import java.time.LocalDate;

public record UsuarioResponseDTO(
    long id_usuario,
    String nome_completo,
    String cpf,
    LocalDate data_nascimento,
    String email,
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
