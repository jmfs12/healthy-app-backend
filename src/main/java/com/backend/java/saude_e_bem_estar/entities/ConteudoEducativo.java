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
public class ConteudoEducativo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_conteudo")
    private long id_conteudo;

    @Column(name = "categoria_conteudo", nullable = false)
    private String categoria_conteudo;

    @Column(name = "titulo_conteudo", nullable = false, length = 100)
    private String titulo_conteudo;

    @Column(name = "corpo_texto", nullable = false, length = 255)
    private String corpo_texto;

    @Column(name = "data_publicacao", nullable = false, updatable = false)
    private LocalDate data_publicacao;
}
