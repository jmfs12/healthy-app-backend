package com.backend.java.saude_e_bem_estar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.java.saude_e_bem_estar.entities.UnidadeSaude;

public interface UnidadeSaudeRepository
        extends JpaRepository<UnidadeSaude, Long> {

}