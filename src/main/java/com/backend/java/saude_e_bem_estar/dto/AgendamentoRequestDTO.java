package com.backend.java.saude_e_bem_estar.dto;



import java.time.LocalDateTime;

import com.backend.java.saude_e_bem_estar.entities.UnidadeSaude;
import com.backend.java.saude_e_bem_estar.entities.Usuario;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record AgendamentoRequestDTO
(   
    @NotNull(message = "O usuário que será consultado é obrigatório")
    Usuario idUsuario,

    @NotNull(message = "A unidade de Saúde onde é obrigatória")
    UnidadeSaude idUnidade,

    @NotBlank(message = "É necessário informar o tipo de servico")
    String tipoServico,

    @FutureOrPresent(message = "Não é possivel realizar um atendimento para um dia anterior")
    @NotNull(message = "É necessário informar a data e a hora do agendamento")
    LocalDateTime dataHoraAgendada

   



    


) {}
