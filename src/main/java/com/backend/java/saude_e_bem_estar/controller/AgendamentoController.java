package com.backend.java.saude_e_bem_estar.controller;

import org.springframework.web.bind.annotation.RestController;

import com.backend.java.saude_e_bem_estar.dto.AgendamentoRequestDTO;
import com.backend.java.saude_e_bem_estar.dto.AgendamentoResponseDTO;

import com.backend.java.saude_e_bem_estar.entities.Agendamento;
import com.backend.java.saude_e_bem_estar.service.AgendamentoService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;







@RestController
@RequestMapping("/agendamento")

public class AgendamentoController {
    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService){
        this.agendamentoService = agendamentoService;
    }

    @PostMapping("/agendar")
    public ResponseEntity<AgendamentoResponseDTO> create(@RequestBody AgendamentoRequestDTO request) {
        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setUsuario(request.idUsuario());
        novoAgendamento.setUnidade(request.idUnidade());
        novoAgendamento.setTipo_servico(request.tipoServico());
        novoAgendamento.setDataHoraAgendada(request.dataHoraAgendada());
        
        Agendamento agendamentoCriado = agendamentoService.criar(novoAgendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(AgendamentoResponseDTO.fromEntity(agendamentoCriado));
    }

    @GetMapping("/{id_agendamento}")
    public ResponseEntity<AgendamentoResponseDTO> getById(@PathVariable Long id_agendamento) {
        
        Agendamento agendamentoBuscado = agendamentoService.buscarPorId(id_agendamento);
        return ResponseEntity.status(HttpStatus.OK).body(AgendamentoResponseDTO.fromEntity(agendamentoBuscado));
    }

    @GetMapping("/{dataHoraAgendamento}")
    public ResponseEntity<AgendamentoResponseDTO> getByDataHora(@PathVariable LocalDateTime dataHoraAgendamento) {
        
        Agendamento agendamentoBuscado = agendamentoService.buscarPorData(dataHoraAgendamento);
        return ResponseEntity.status(HttpStatus.OK).body(AgendamentoResponseDTO.fromEntity(agendamentoBuscado));
    }
    
    @PutMapping("atualizar/{id_agendamento}")
    public ResponseEntity<Void> putAgendamento(@PathVariable Long id_agendamento, @RequestBody Agendamento novosDados) {
        
        agendamentoService.atualizar(id_agendamento, novosDados);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("deletar/{id_agendamento}")
    public ResponseEntity<Void> deleteAgendamento(@PathVariable Long id_agendamento) {
        
        agendamentoService.deletar(id_agendamento);

        return ResponseEntity.noContent().build();
    }


    
}
