package com.backend.java.saude_e_bem_estar.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.java.saude_e_bem_estar.entities.Agendamento;


public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    Optional<Agendamento> findByDataHoraAgendada(LocalDateTime dataHora_agendada);
    Optional<Agendamento> findById(Long id_agendamento);

    boolean existsByUsuarioIdAndDataHoraAgendada(Long idUsuario, LocalDateTime dataHora);

    List<Agendamento> findByUsuarioIdOrderByDataHoraAgendadaDesc(Long idUsuario);
} 