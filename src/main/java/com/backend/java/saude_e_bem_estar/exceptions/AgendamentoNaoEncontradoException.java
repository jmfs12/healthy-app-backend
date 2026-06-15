package com.backend.java.saude_e_bem_estar.exceptions;

public class AgendamentoNaoEncontradoException extends ResourceNotFoundException {
    public AgendamentoNaoEncontradoException() {
        super("Agendamento não encontrado.");
    }

    public AgendamentoNaoEncontradoException(String msg) {
        super(msg);
    }
}
