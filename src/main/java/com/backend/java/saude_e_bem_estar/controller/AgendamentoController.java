package com.backend.java.saude_e_bem_estar.controller;

import org.springframework.web.bind.annotation.RestController;

import com.backend.java.saude_e_bem_estar.dto.AgendamentoRequestDTO;
import com.backend.java.saude_e_bem_estar.dto.AgendamentoResponseDTO;
import com.backend.java.saude_e_bem_estar.dto.UsuarioResponseDTO;
import com.backend.java.saude_e_bem_estar.entities.Agendamento;
import com.backend.java.saude_e_bem_estar.entities.Usuario;
import com.backend.java.saude_e_bem_estar.service.AgendamentoService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




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
        novoAgendamento.setId_usuario(request.idUsuario());
        novoAgendamento.setId_unidade(request.idUnidade());
        novoAgendamento.setTipo_servico(request.tipoServico());
        novoAgendamento.setDataHora_agendada(request.dataHoraAgendada());
        
        Agendamento AgendamentoCriado = agendamentoService.criar(novoAgendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(AgendamentoResponseDTO.fromEntity(AgendamentoCriado));
    }
    
}
