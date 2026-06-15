package com.backend.java.saude_e_bem_estar.dto;

import java.time.LocalDateTime;

import com.backend.java.saude_e_bem_estar.entities.Agendamento;
import com.backend.java.saude_e_bem_estar.entities.StatusAgendamento;

public record AgendamentoResponseDTO(
    Long idAgendamento,
    StatusAgendamento status,
    String tipoServico,
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
