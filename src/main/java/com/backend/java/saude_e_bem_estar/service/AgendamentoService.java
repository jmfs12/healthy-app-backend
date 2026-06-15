package com.backend.java.saude_e_bem_estar.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.backend.java.saude_e_bem_estar.entities.Agendamento;
import com.backend.java.saude_e_bem_estar.entities.StatusAgendamento;
import com.backend.java.saude_e_bem_estar.repository.AgendamentoRepository;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository){
        this.agendamentoRepository = agendamentoRepository;
    }

    public Agendamento criar(Agendamento agendamento){
        if (agendamentoRepository.existsByUsuarioAndDataHoraAgendada(agendamento.getUsuario(), agendamento.getDataHoraAgendada())) {
            throw new RuntimeException("Já existe um agendamento cadastrado neste horário.");
        }

        agendamento.setStatus(StatusAgendamento.PENDENTE);

        return agendamentoRepository.save(agendamento);
        
    }

    public Agendamento buscarPorId(Long id) {
        
        return agendamentoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Agendamento não encontrado."));
    }

    public Agendamento buscarPorData(LocalDateTime dataHoraAgendamento){
        return agendamentoRepository.findByDataHoraAgendada(dataHoraAgendamento)
            .orElseThrow(() -> new RuntimeException("Data não marcada"));
    }

    public Agendamento atualizar(Long id, Agendamento novosDados){
        Agendamento agendamentoExistente = buscarPorId(id);

        
        if (novosDados.getUsuario() != null){
            agendamentoExistente.setUsuario(novosDados.getUsuario());
        }
        if (novosDados.getUnidade() != null){
            agendamentoExistente.setUnidade(novosDados.getUnidade());
        }
        if (novosDados.getTipo_servico() != null){
            agendamentoExistente.setTipo_servico(novosDados.getTipo_servico());
        }
        if (novosDados.getDataHoraAgendada() != null){
            agendamentoExistente.setDataHoraAgendada(novosDados.getDataHoraAgendada());
        }
        if (novosDados.getStatus() != null){
            agendamentoExistente.setStatus(novosDados.getStatus());
        }

        return agendamentoRepository.save(agendamentoExistente);
    }

    public void deletar(Long id) {
        Agendamento agendamentoExistente = buscarPorId(id);
        agendamentoRepository.delete(agendamentoExistente);
    }

}
