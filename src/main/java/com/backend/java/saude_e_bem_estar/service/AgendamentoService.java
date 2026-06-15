package com.backend.java.saude_e_bem_estar.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.backend.java.saude_e_bem_estar.entities.Agendamento;
import com.backend.java.saude_e_bem_estar.entities.StatusAgendamento;
import com.backend.java.saude_e_bem_estar.entities.Usuario;
import com.backend.java.saude_e_bem_estar.entities.UnidadeSaude;
import com.backend.java.saude_e_bem_estar.repository.AgendamentoRepository;
import com.backend.java.saude_e_bem_estar.repository.UsuarioRepository;
import com.backend.java.saude_e_bem_estar.repository.UnidadeSaudeRepository;
import com.backend.java.saude_e_bem_estar.exceptions.*;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UnidadeSaudeRepository unidadeSaudeRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              UsuarioRepository usuarioRepository,
                              UnidadeSaudeRepository unidadeSaudeRepository){
        this.agendamentoRepository = agendamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.unidadeSaudeRepository = unidadeSaudeRepository;
    }

    public Agendamento criar(Agendamento agendamento, Long idUsuario, Long idUnidade){
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new UsuarioNaoEncontradoException());
        UnidadeSaude unidade = unidadeSaudeRepository.findById(idUnidade)
            .orElseThrow(() -> new UnidadeSaudeNaoEncontradaException());

        if (agendamentoRepository.existsByUsuarioAndDataHoraAgendada(usuario, agendamento.getDataHoraAgendada())) {
            throw new ConflitoAgendamentoException();
        }

        agendamento.setUsuario(usuario);
        agendamento.setUnidade(unidade);
        agendamento.setStatus(StatusAgendamento.PENDENTE);

        return agendamentoRepository.save(agendamento);
    }

    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
            .orElseThrow(() -> new AgendamentoNaoEncontradoException());
    }

    public Agendamento buscarPorData(LocalDateTime dataHoraAgendamento){
        return agendamentoRepository.findByDataHoraAgendada(dataHoraAgendamento)
            .orElseThrow(() -> new DataNaoMarcadaException());
    }

    public Agendamento atualizar(Long id, Agendamento novosDados, Long idUsuario, Long idUnidade){
        Agendamento agendamentoExistente = buscarPorId(id);

        if (idUsuario != null){
            Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());
            agendamentoExistente.setUsuario(usuario);
        }
        if (idUnidade != null){
            UnidadeSaude unidade = unidadeSaudeRepository.findById(idUnidade)
                .orElseThrow(() -> new UnidadeSaudeNaoEncontradaException());
            agendamentoExistente.setUnidade(unidade);
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
