package com.backend.java.saude_e_bem_estar.service;

import com.backend.java.saude_e_bem_estar.dto.UnidadeSaudeRequestDTO;
import com.backend.java.saude_e_bem_estar.dto.UnidadeSaudeResponseDTO;
import com.backend.java.saude_e_bem_estar.entities.UnidadeSaude;
import com.backend.java.saude_e_bem_estar.repository.UnidadeSaudeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadeSaudeService {

    private final UnidadeSaudeRepository unidadeSaudeRepository;

    public UnidadeSaudeService(UnidadeSaudeRepository unidadeSaudeRepository) {
        this.unidadeSaudeRepository = unidadeSaudeRepository;
    }

    public UnidadeSaudeResponseDTO criar(UnidadeSaudeRequestDTO dto) {
        UnidadeSaude unidade = new UnidadeSaude();

        unidade.setNome_unidade(dto.nome_unidade());
        unidade.setTipo(dto.tipo());
        unidade.setEndereco_completo(dto.endereco_completo());
        unidade.setLatitude(dto.latitude());
        unidade.setLongitude(dto.longitude());

        UnidadeSaude unidadeSalva = unidadeSaudeRepository.save(unidade);

        return UnidadeSaudeResponseDTO.fromEntity(unidadeSalva);
    }

    public List<UnidadeSaudeResponseDTO> listarTodas() {
        return unidadeSaudeRepository.findAll()
                .stream()
                .map(UnidadeSaudeResponseDTO::fromEntity)
                .toList();
    }

    public UnidadeSaudeResponseDTO buscarPorId(Long id) {
        UnidadeSaude unidade = unidadeSaudeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade de saúde não encontrada."));

        return UnidadeSaudeResponseDTO.fromEntity(unidade);
    }

    public UnidadeSaudeResponseDTO atualizar(Long id, UnidadeSaudeRequestDTO dto) {
        UnidadeSaude unidade = unidadeSaudeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade de saúde não encontrada."));

        unidade.setNome_unidade(dto.nome_unidade());
        unidade.setTipo(dto.tipo());
        unidade.setEndereco_completo(dto.endereco_completo());
        unidade.setLatitude(dto.latitude());
        unidade.setLongitude(dto.longitude());

        UnidadeSaude unidadeAtualizada = unidadeSaudeRepository.save(unidade);

        return UnidadeSaudeResponseDTO.fromEntity(unidadeAtualizada);
    }

    public void deletar(Long id) {
        UnidadeSaude unidade = unidadeSaudeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade de saúde não encontrada."));

        unidadeSaudeRepository.delete(unidade);
    }
}