package com.backend.java.saude_e_bem_estar.controller;

import com.backend.java.saude_e_bem_estar.dto.AgendamentoRequestDTO;
import com.backend.java.saude_e_bem_estar.dto.AgendamentoResponseDTO;
import com.backend.java.saude_e_bem_estar.entities.Agendamento;
import com.backend.java.saude_e_bem_estar.service.AgendamentoService;
import com.backend.java.saude_e_bem_estar.exceptions.CustomErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
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

import java.time.LocalDateTime;

@RestController
@RequestMapping("/agendamento")
@Tag(name = "Agendamentos", description = "Endpoints para agendamento de consultas médicas e gestão de status.")
public class AgendamentoController {
    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService){
        this.agendamentoService = agendamentoService;
    }

    @PostMapping("/agendar")
    @Operation(summary = "Agenda uma nova consulta médica", description = "Cria um registro de consulta associado a um usuário e a uma unidade de saúde. Requer token JWT.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Consulta agendada com sucesso",
            content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Parâmetros informados inválidos",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":400,\"error\":\"Regra de negócio violada\",\"message\":\"Não é possivel realizar um atendimento para um dia anterior.\",\"path\":\"/agendamento/agendar\"}"))),
        @ApiResponse(responseCode = "401", description = "Acesso não autorizado - token JWT inválido ou ausente",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":401,\"error\":\"Acesso não autorizado\",\"message\":\"Token JWT ausente ou inválido.\",\"path\":\"/agendamento/agendar\"}"))),
        @ApiResponse(responseCode = "404", description = "Usuário ou Unidade de Saúde não encontrada",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":404,\"error\":\"Recurso não encontrado\",\"message\":\"Unidade de saúde não encontrada.\",\"path\":\"/agendamento/agendar\"}"))),
        @ApiResponse(responseCode = "409", description = "Conflito: Já existe agendamento neste horário",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":409,\"error\":\"Regra de negócio violada\",\"message\":\"Já existe agendamento neste horário.\",\"path\":\"/agendamento/agendar\"}")))
    })
    public ResponseEntity<AgendamentoResponseDTO> create(@RequestBody @Valid AgendamentoRequestDTO request) {
        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setTipoServico(request.tipoServico());
        novoAgendamento.setDataHoraAgendada(request.dataHoraAgendada());
        
        Agendamento agendamentoCriado = agendamentoService.criar(novoAgendamento, request.idUsuario(), request.idUnidade());
        return ResponseEntity.status(HttpStatus.CREATED).body(AgendamentoResponseDTO.fromEntity(agendamentoCriado));
    }

    @GetMapping("/{id_agendamento}")
    @Operation(summary = "Busca detalhes de um agendamento por ID", description = "Retorna as informações completas do agendamento solicitado. Requer token JWT.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Agendamento localizado com sucesso",
            content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Acesso não autorizado - token JWT inválido ou ausente",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":401,\"error\":\"Acesso não autorizado\",\"message\":\"Token JWT ausente ou inválido.\",\"path\":\"/agendamento/1\"}"))),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":404,\"error\":\"Recurso não encontrado\",\"message\":\"Agendamento não encontrado.\",\"path\":\"/agendamento/1\"}")))
    })
    public ResponseEntity<AgendamentoResponseDTO> getById(@PathVariable Long id_agendamento) {
        Agendamento agendamentoBuscado = agendamentoService.buscarPorId(id_agendamento);
        return ResponseEntity.status(HttpStatus.OK).body(AgendamentoResponseDTO.fromEntity(agendamentoBuscado));
    }

    @GetMapping("/data/{dataHoraAgendamento}")
    @Operation(summary = "Busca agendamento por data e hora", description = "Retorna o agendamento associado à data e hora exatas passadas como parâmetro. Requer token JWT.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Agendamento localizado com sucesso",
            content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Acesso não autorizado - token JWT inválido ou ausente",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":401,\"error\":\"Acesso não autorizado\",\"message\":\"Token JWT ausente ou inválido.\",\"path\":\"/agendamento/data/2026-06-15T12:00:00\"}"))),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado para este horário",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":404,\"error\":\"Recurso não encontrado\",\"message\":\"Agendamento não encontrado para este horário.\",\"path\":\"/agendamento/data/2026-06-15T12:00:00\"}")))
    })
    public ResponseEntity<AgendamentoResponseDTO> getByDataHora(@PathVariable LocalDateTime dataHoraAgendamento) {
        Agendamento agendamentoBuscado = agendamentoService.buscarPorData(dataHoraAgendamento);
        return ResponseEntity.status(HttpStatus.OK).body(AgendamentoResponseDTO.fromEntity(agendamentoBuscado));
    }
    
    @PutMapping("atualizar/{id_agendamento}")
    @Operation(summary = "Atualiza os dados de um agendamento", description = "Permite alterar o tipo de serviço, data/hora, usuário e unidade de um agendamento existente. Requer token JWT.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Agendamento atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados fornecidos inválidos",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":400,\"error\":\"Regra de negócio violada\",\"message\":\"Não é possivel realizar um atendimento para um dia anterior.\",\"path\":\"/agendamento/atualizar/1\"}"))),
        @ApiResponse(responseCode = "401", description = "Acesso não autorizado - token JWT inválido ou ausente",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":401,\"error\":\"Acesso não autorizado\",\"message\":\"Token JWT ausente ou inválido.\",\"path\":\"/agendamento/atualizar/1\"}"))),
        @ApiResponse(responseCode = "404", description = "Agendamento, Usuário ou Unidade de Saúde não encontrado",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":404,\"error\":\"Recurso não encontrado\",\"message\":\"Agendamento não encontrado.\",\"path\":\"/agendamento/atualizar/1\"}"))),
        @ApiResponse(responseCode = "409", description = "Conflito: Novo horário indisponível",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":409,\"error\":\"Regra de negócio violada\",\"message\":\"Já existe agendamento neste horário.\",\"path\":\"/agendamento/atualizar/1\"}")))
    })
    public ResponseEntity<Void> putAgendamento(@PathVariable Long id_agendamento, @RequestBody @Valid AgendamentoRequestDTO request) {
        Agendamento novosDados = new Agendamento();
        novosDados.setTipoServico(request.tipoServico());
        novosDados.setDataHoraAgendada(request.dataHoraAgendada());

        agendamentoService.atualizar(id_agendamento, novosDados, request.idUsuario(), request.idUnidade());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("deletar/{id_agendamento}")
    @Operation(summary = "Cancela/Exclui um agendamento", description = "Remove logicamente ou fisicamente o agendamento correspondente. Requer token JWT.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Agendamento cancelado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Acesso não autorizado - token JWT inválido ou ausente",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":401,\"error\":\"Acesso não autorizado\",\"message\":\"Token JWT ausente ou inválido.\",\"path\":\"/agendamento/deletar/1\"}"))),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado",
            content = @Content(schema = @Schema(implementation = CustomErrorResponse.class),
                examples = @ExampleObject(value = "{\"timestamp\":\"2026-06-15T12:00:00Z\",\"status\":404,\"error\":\"Recurso não encontrado\",\"message\":\"Agendamento não encontrado.\",\"path\":\"/agendamento/deletar/1\"}")))
    })
    public ResponseEntity<Void> deleteAgendamento(@PathVariable Long id_agendamento) {
        agendamentoService.deletar(id_agendamento);
        return ResponseEntity.noContent().build();
    }
}
