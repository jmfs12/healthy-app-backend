package com.backend.java.saude_e_bem_estar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

import com.backend.java.saude_e_bem_estar.entities.Agendamento;
import com.backend.java.saude_e_bem_estar.entities.StatusAgendamento;

@Schema(description = "Dados de retorno do agendamento de consulta")
public record AgendamentoResponseDTO(
    @Schema(description = "Identificador único do agendamento", example = "1")
    Long idAgendamento,

    @Schema(description = "Status do agendamento", example = "CONFIRMADO")
    StatusAgendamento status,

    @Schema(description = "Tipo de serviço solicitado", example = "Clínico Geral")
    String tipoServico,

    @Schema(description = "Data e hora agendadas para a consulta", example = "2026-07-20T14:30:00")
    LocalDateTime dataHoraAgendada
) {
      public static AgendamentoResponseDTO fromEntity(Agendamento agendamento) {
        return new AgendamentoResponseDTO(
            agendamento.getIdAgendamento(),
            agendamento.getStatus(),
            agendamento.getTipoServico(),
            agendamento.getDataHoraAgendada()
        );
    }
}
