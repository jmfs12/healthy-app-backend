package com.backend.java.saude_e_bem_estar.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.backend.java.saude_e_bem_estar.entities.Agendamento;
import com.backend.java.saude_e_bem_estar.repository.AgendamentoRepository;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository){
        this.agendamentoRepository = agendamentoRepository;
    }

    public Agendamento criar(Agendamento agendamento){

        if (agendamentoRepository.existsById(agendamento.getId_agendamento())) {
            throw new RuntimeException("Agendamento já cadastrado.");
        }
        if (agendamentoRepository.existsByUsuarioIdAndDataHoraAgendada(agendamento.getId_agendamento(), agendamento.getDataHora_agendada())) {
            throw new RuntimeException("Já existe um agendamento cadastrado neste horário.");
        }
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

        
        if (novosDados.getId_usuario() != null){
            agendamentoExistente.setId_usuario(novosDados.getId_usuario());
        }
        if (novosDados.getId_unidade() != null){
            agendamentoExistente.setId_unidade(novosDados.getId_unidade());
        }
        if (novosDados.getTipo_servico() != null){
            agendamentoExistente.setTipo_servico(novosDados.getTipo_servico());
        }
        if (novosDados.getDataHora_agendada() != null){
            agendamentoExistente.setDataHora_agendada(novosDados.getDataHora_agendada());
        }

        return agendamentoRepository.save(agendamentoExistente);
    }

    public void deletar(Long id) {
        Agendamento agendamentoExistente = buscarPorId(id);
        agendamentoRepository.delete(agendamentoExistente);
    }

}
