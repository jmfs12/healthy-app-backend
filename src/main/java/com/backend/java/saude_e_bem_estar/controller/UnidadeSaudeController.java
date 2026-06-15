package com.backend.java.saude_e_bem_estar.controller;

import com.backend.java.saude_e_bem_estar.dto.UnidadeSaudeRequestDTO;
import com.backend.java.saude_e_bem_estar.dto.UnidadeSaudeResponseDTO;
import com.backend.java.saude_e_bem_estar.service.UnidadeSaudeService;
import com.backend.java.saude_e_bem_estar.exceptions.CustomErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidades")
@Tag(name = "Unidades de Saúde", description = "Endpoints para gerenciamento e consulta de estabelecimentos de saúde.")
public class UnidadeSaudeController {

    private final UnidadeSaudeService unidadeSaudeService;

    public UnidadeSaudeController(UnidadeSaudeService unidadeSaudeService) {
        this.unidadeSaudeService = unidadeSaudeService;
    }

    @PostMapping
    @Operation(summary = "Cadastra uma nova unidade de saúde", description = "Cria uma unidade de saúde no sistema. Requer token JWT.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Unidade de saúde criada com sucesso",
            content = @Content(schema = @Schema(implementation = UnidadeSaudeResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados informados inválidos ou mal formatados",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":400,\"error\":\"Regra de negócio violada\",\"message\":\"O nome da unidade é obrigatório.\",\"path\":\"/unidades\"}"))),
        @ApiResponse(responseCode = "401", description = "Acesso não autorizado - token JWT inválido ou ausente",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":401,\"error\":\"Acesso não autorizado\",\"message\":\"Token JWT ausente ou inválido.\",\"path\":\"/unidades\"}")))
    })
    public ResponseEntity<UnidadeSaudeResponseDTO> criar(@RequestBody @Valid UnidadeSaudeRequestDTO body) {
        UnidadeSaudeResponseDTO unidadeCriada = unidadeSaudeService.criar(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(unidadeCriada);
    }

    @GetMapping
    @Operation(summary = "Lista todas as unidades de saúde", description = "Retorna uma lista contendo todas as unidades de saúde cadastradas. Requer token JWT.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de unidades retornada com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UnidadeSaudeResponseDTO.class)))),
        @ApiResponse(responseCode = "401", description = "Acesso não autorizado - token JWT inválido ou ausente",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":401,\"error\":\"Acesso não autorizado\",\"message\":\"Token JWT ausente ou inválido.\",\"path\":\"/unidades\"}")))
    })
    public ResponseEntity<List<UnidadeSaudeResponseDTO>> listarTodas() {
        return ResponseEntity.ok(unidadeSaudeService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma unidade de saúde por ID", description = "Retorna as informações detalhadas da unidade de saúde correspondente ao ID informado. Requer token JWT.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Unidade de saúde encontrada com sucesso",
            content = @Content(schema = @Schema(implementation = UnidadeSaudeResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Acesso não autorizado - token JWT inválido ou ausente",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":401,\"error\":\"Acesso não autorizado\",\"message\":\"Token JWT ausente ou inválido.\",\"path\":\"/unidades/1\"}"))),
        @ApiResponse(responseCode = "404", description = "Unidade de saúde não encontrada",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":404,\"error\":\"Recurso não encontrado\",\"message\":\"Unidade de saúde não encontrada.\",\"path\":\"/unidades/1\"}")))
    })
    public ResponseEntity<UnidadeSaudeResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(unidadeSaudeService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de uma unidade de saúde", description = "Atualiza completamente as informações da unidade de saúde especificada. Requer token JWT.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Unidade de saúde atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = UnidadeSaudeResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados informados inválidos ou mal formatados",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":400,\"error\":\"Regra de negócio violada\",\"message\":\"A latitude é obrigatória.\",\"path\":\"/unidades/1\"}"))),
        @ApiResponse(responseCode = "401", description = "Acesso não autorizado - token JWT inválido ou ausente",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":401,\"error\":\"Acesso não autorizado\",\"message\":\"Token JWT ausente ou inválido.\",\"path\":\"/unidades/1\"}"))),
        @ApiResponse(responseCode = "404", description = "Unidade de saúde não encontrada",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":404,\"error\":\"Recurso não encontrado\",\"message\":\"Unidade de saúde não encontrada.\",\"path\":\"/unidades/1\"}")))
    })
    public ResponseEntity<UnidadeSaudeResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid UnidadeSaudeRequestDTO body
    ) {
        return ResponseEntity.ok(unidadeSaudeService.atualizar(id, body));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma unidade de saúde", description = "Exclui permanentemente a unidade de saúde especificada do sistema. Requer token JWT.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Unidade de saúde excluída com sucesso"),
        @ApiResponse(responseCode = "401", description = "Acesso não autorizado - token JWT inválido ou ausente",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":401,\"error\":\"Acesso não autorizado\",\"message\":\"Token JWT ausente ou inválido.\",\"path\":\"/unidades/1\"}"))),
        @ApiResponse(responseCode = "404", description = "Unidade de saúde não encontrada",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":404,\"error\":\"Recurso não encontrado\",\"message\":\"Unidade de saúde não encontrada.\",\"path\":\"/unidades/1\"}")))
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        unidadeSaudeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}