package com.backend.java.saude_e_bem_estar.dto;

import java.time.LocalDateTime;

public record AgendamentoResponseDTO(
    String status,
    String tipo_servico,
    LocalDateTime dataHora_agendada
) {}
