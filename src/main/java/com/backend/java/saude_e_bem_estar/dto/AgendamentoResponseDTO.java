package com.backend.java.saude_e_bem_estar.dto;

import java.time.LocalDateTime;

public record AgendamentoResponseDTO(
    Long idAgendamento,
    String status,
    String tipoServico,
    LocalDateTime dataHoraAgendada
) {}
