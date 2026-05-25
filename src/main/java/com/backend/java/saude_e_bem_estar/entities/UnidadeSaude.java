package com.backend.java.saude_e_bem_estar.entities;

import java.math.BigDecimal;

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
public class UnidadeSaude {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_unidade")
    private long id_unidade;

    @Column(name = "nome_unidade", nullable = false, length = 100)
    private String nome_unidade;

    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;

    @Column(name = "endereco_completo", nullable = false)
    private String endereco_completo;

    @Column(name = "latitude", nullable = false, precision = 8, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 9, scale = 6 )
    private BigDecimal longitude;


}
