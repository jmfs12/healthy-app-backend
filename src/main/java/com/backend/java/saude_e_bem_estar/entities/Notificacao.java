package com.backend.java.saude_e_bem_estar.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_notificacao")
    private long id_notificacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", foreignKey =@ForeignKey(name = "fk_id_usuario"))
    private Usuario id_usuario;

    @ManyToOne
    @JoinColumn(name = "id_campanha", referencedColumnName = "id_campanha", foreignKey =@ForeignKey(name = "fk_id_campanha"))
    private Campanha id_campanha;

    @Column(name = "titulo_notificacao", nullable = false, length = 100)
    private String titulo_notificacao;

    @Column(name = "mensagem_notificacao", nullable = false, length = 255)
    private String mensagem_notificacao;

    @Column(name = "data_envio", nullable = false, updatable = false)
    private LocalDate data_envio;

    @Column(name = "status_leitura", nullable = false)
    private Boolean status_leitura;
}
