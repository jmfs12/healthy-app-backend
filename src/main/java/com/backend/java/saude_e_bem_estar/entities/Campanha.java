package com.backend.java.saude_e_bem_estar.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Campanha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_campanha")
    private long id_campanha;

    @Column(name = "titulo_campanha", nullable = false, length = 100)
    private String titulo_campanha;

    @Column(name = "descricao_campanha", nullable = false, length = 150)
    private String descricao_campanha;

    @Column(name = "publico_alvo", nullable = false, length = 80)
    private String publico_alvo;

    @Column(name = "data_inicio", nullable = false, updatable = false)
    private LocalDate data_inicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate data_fim;


}
