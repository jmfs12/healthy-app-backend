package com.backend.java.saude_e_bem_estar.repository;

import com.backend.java.saude_e_bem_estar.entities.UnidadeSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeSaudeRepository extends JpaRepository<UnidadeSaude, Long> {
}