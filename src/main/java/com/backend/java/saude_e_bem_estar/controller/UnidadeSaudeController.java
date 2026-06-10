package com.backend.java.saude_e_bem_estar.controller;

import com.backend.java.saude_e_bem_estar.dto.UnidadeSaudeRequestDTO;
import com.backend.java.saude_e_bem_estar.dto.UnidadeSaudeResponseDTO;
import com.backend.java.saude_e_bem_estar.service.UnidadeSaudeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidades")
public class UnidadeSaudeController {

    private final UnidadeSaudeService unidadeSaudeService;

    public UnidadeSaudeController(UnidadeSaudeService unidadeSaudeService) {
        this.unidadeSaudeService = unidadeSaudeService;
    }

    @PostMapping
    public ResponseEntity<UnidadeSaudeResponseDTO> criar(@RequestBody @Valid UnidadeSaudeRequestDTO body) {
        UnidadeSaudeResponseDTO unidadeCriada = unidadeSaudeService.criar(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(unidadeCriada);
    }

    @GetMapping
    public ResponseEntity<List<UnidadeSaudeResponseDTO>> listarTodas() {
        return ResponseEntity.ok(unidadeSaudeService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeSaudeResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(unidadeSaudeService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadeSaudeResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid UnidadeSaudeRequestDTO body
    ) {
        return ResponseEntity.ok(unidadeSaudeService.atualizar(id, body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        unidadeSaudeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}