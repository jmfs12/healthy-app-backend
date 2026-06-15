package com.backend.java.saude_e_bem_estar.entities;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_agendamento")
    private Long idAgendamento;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", foreignKey =@ForeignKey(name = "fk_id_usuario"))
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_unidade", referencedColumnName = "id_unidade", foreignKey =@ForeignKey(name = "fk_id_unidade"))
    private UnidadeSaude unidade;

    @Column(name = "tipo_servico", nullable = false, length = 150)
    private String tipoServico;

    @FutureOrPresent(message = "Não é possivel realizar um atendimento para um dia anterior")
    @Column(name = "dataHora_agendada", nullable = false)
    private LocalDateTime dataHoraAgendada;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 80)
    private StatusAgendamento status;



}
