package com.backend.java.saude_e_bem_estar.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.java.saude_e_bem_estar.dto.UnidadeSaudeRequestDTO;
import com.backend.java.saude_e_bem_estar.dto.UnidadeSaudeResponseDTO;
import com.backend.java.saude_e_bem_estar.entities.UnidadeSaude;
import com.backend.java.saude_e_bem_estar.repository.UnidadeSaudeRepository;

@Service
public class UnidadeSaudeService {

    @Autowired
    private UnidadeSaudeRepository repository;

    public UnidadeSaudeResponseDTO criar(
            UnidadeSaudeRequestDTO dto) {

        UnidadeSaude unidade = new UnidadeSaude();

        unidade.setNome_unidade(dto.getNome_unidade());
        unidade.setTipo(dto.getTipo());
        unidade.setEndereco_completo(dto.getEndereco_completo());
        unidade.setLatitude(dto.getLatitude());
        unidade.setLongitude(dto.getLongitude());

        UnidadeSaude salva = repository.save(unidade);

        return converterParaDTO(salva);
    }

    public List<UnidadeSaudeResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    private UnidadeSaudeResponseDTO converterParaDTO(
            UnidadeSaude unidade) {

        UnidadeSaudeResponseDTO dto =
                new UnidadeSaudeResponseDTO();

        dto.setId_unidade(unidade.getId_unidade());
        dto.setNome_unidade(unidade.getNome_unidade());
        dto.setTipo(unidade.getTipo());
        dto.setEndereco_completo(unidade.getEndereco_completo());
        dto.setLatitude(unidade.getLatitude());
        dto.setLongitude(unidade.getLongitude());

        return dto;
    }
}