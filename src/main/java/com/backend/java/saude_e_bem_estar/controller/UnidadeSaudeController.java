package com.backend.java.saude_e_bem_estar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.java.saude_e_bem_estar.dto.UnidadeSaudeRequestDTO;
import com.backend.java.saude_e_bem_estar.dto.UnidadeSaudeResponseDTO;
import com.backend.java.saude_e_bem_estar.service.UnidadeSaudeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/unidades")
public class UnidadeSaudeController {

    @Autowired
    private UnidadeSaudeService service;

    @PostMapping
    public ResponseEntity<UnidadeSaudeResponseDTO> criar(
            @Valid @RequestBody UnidadeSaudeRequestDTO dto) {

        return ResponseEntity.ok(service.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<UnidadeSaudeResponseDTO>> listar() {

        return ResponseEntity.ok(service.listar());
    }
}