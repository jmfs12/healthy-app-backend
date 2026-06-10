package com.backend.java.saude_e_bem_estar.dto;



import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;


public record AgendamentoRequestDTO
(   
    @NotBlank(message = "O usuário que será consultado é obrigatório")
    Long id_usuario,

    @NotBlank(message = "A unidade de Saúde onde é obrigatória")
    Long id_unidade,

    @NotBlank(message = "É necessário informar o tipo de servico")
    String tipo_servico,

    @FutureOrPresent(message = "Não é possivel realizar um atendimento para um dia anterior")
    @NotBlank(message = "É necessário informar a data e a hora do agendamento")
    LocalDateTime dataHora_agendada

   



    


) {}
