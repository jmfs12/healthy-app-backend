package com.backend.java.saude_e_bem_estar.controller;

import com.backend.java.saude_e_bem_estar.dto.UsuarioResponseDTO;
import com.backend.java.saude_e_bem_estar.dto.UsuarioUpdateRequestDTO;
import com.backend.java.saude_e_bem_estar.entities.Usuario;
import com.backend.java.saude_e_bem_estar.service.UsuarioService;
import com.backend.java.saude_e_bem_estar.exceptions.CustomErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para consulta, atualização e exclusão de perfis de usuários.")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca perfil de usuário por ID", description = "Retorna as informações públicas do usuário correspondente ao ID informado. Requer token JWT.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário localizado com sucesso",
            content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Acesso não autorizado - token JWT inválido ou ausente",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":401,\"error\":\"Acesso não autorizado\",\"message\":\"Token JWT ausente ou inválido.\",\"path\":\"/usuarios/1\"}"))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado com o ID especificado",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":404,\"error\":\"Recurso não encontrado\",\"message\":\"Usuário não encontrado.\",\"path\":\"/usuarios/1\"}")))
    })
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioResponseDTO.fromEntity(usuario));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza dados do perfil do usuário", description = "Permite a atualização de campos opcionais como nome completo, data de nascimento, telefone e senha. Requer token JWT.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados fornecidos inválidos ou mal formatados",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":400,\"error\":\"Regra de negócio violada\",\"message\":\"A data de nascimento deve ser uma data passada.\",\"path\":\"/usuarios/1\"}"))),
        @ApiResponse(responseCode = "401", description = "Acesso não autorizado - token JWT inválido ou ausente",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":401,\"error\":\"Acesso não autorizado\",\"message\":\"Token JWT ausente ou inválido.\",\"path\":\"/usuarios/1\"}"))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado com o ID especificado",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":404,\"error\":\"Recurso não encontrado\",\"message\":\"Usuário não encontrado.\",\"path\":\"/usuarios/1\"}")))
    })
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id, 
            @RequestBody @Valid UsuarioUpdateRequestDTO novosDados
    ) {
        Usuario usuarioAtualizado = usuarioService.atualizar(id, novosDados);
        return ResponseEntity.ok(UsuarioResponseDTO.fromEntity(usuarioAtualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um usuário do sistema", description = "Remove permanentemente a conta de um usuário. Requer token JWT.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
        @ApiResponse(responseCode = "401", description = "Acesso não autorizado - token JWT inválido ou ausente",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":401,\"error\":\"Acesso não autorizado\",\"message\":\"Token JWT ausente ou inválido.\",\"path\":\"/usuarios/1\"}"))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado com o ID especificado",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":404,\"error\":\"Recurso não encontrado\",\"message\":\"Usuário não encontrado.\",\"path\":\"/usuarios/1\"}")))
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
