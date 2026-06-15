package com.backend.java.saude_e_bem_estar.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.java.saude_e_bem_estar.entities.Agendamento;
import com.backend.java.saude_e_bem_estar.entities.Usuario;


public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    Optional<Agendamento> findByDataHoraAgendada(LocalDateTime dataHoraAgendada);
    Optional<Agendamento> findById(Long idAgendamento);

    boolean existsByUsuarioAndDataHoraAgendada(Usuario usuario, LocalDateTime dataHora);

    List<Agendamento> findByUsuarioIdUsuarioOrderByDataHoraAgendadaDesc(Long idUsuario);
}