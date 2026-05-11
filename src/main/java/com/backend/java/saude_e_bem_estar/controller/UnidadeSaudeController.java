package com.backend.java.saude_e_bem_estar.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unidades")
public class UnidadeSaudeController {

    // Simulação de uma lista para teste 
   private final List<String> unidadesTeste = new ArrayList<>();

    // O GET
    @GetMapping
    public ResponseEntity<List<String>> listarTodas() {
        return ResponseEntity.ok(unidadesTeste);
    }

    // O POST
    @PostMapping
    public ResponseEntity<String> criar(@RequestBody String nomeUnidade) {
        unidadesTeste.add(nomeUnidade);
        return ResponseEntity.status(201).body("Unidade " + nomeUnidade + " cadastrada com sucesso!");
    }
}