package com.backend.java.saude_e_bem_estar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

import com.backend.java.saude_e_bem_estar.entities.UnidadeSaude;
import com.backend.java.saude_e_bem_estar.entities.Usuario;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para criação/atualização de agendamento de consulta")
public record AgendamentoRequestDTO
(   
    @Schema(description = "ID do usuário solicitante da consulta", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O usuário que será consultado é obrigatório")
    Long idUsuario,

    @Schema(description = "ID da unidade de saúde onde será realizada a consulta", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A unidade de Saúde onde é obrigatória")
    Long idUnidade,

    @Schema(description = "Tipo de serviço ou especialidade médica", example = "Clínico Geral", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "É necessário informar o tipo de servico")
    String tipoServico,

    @Schema(description = "Data e hora pretendidas para a consulta (deve ser no presente ou futuro)", example = "2026-07-20T14:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @FutureOrPresent(message = "Não é possivel realizar um atendimento para um dia anterior")
    @NotNull(message = "É necessário informar a data e a hora do agendamento")
    LocalDateTime dataHoraAgendada
) {}
