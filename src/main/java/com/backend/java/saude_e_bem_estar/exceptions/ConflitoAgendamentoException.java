package com.backend.java.saude_e_bem_estar.exceptions;

public class ConflitoAgendamentoException extends BusinessException {
    public ConflitoAgendamentoException() {
        super("Já existe um agendamento cadastrado neste horário.");
    }

    public ConflitoAgendamentoException(String msg) {
        super(msg);
    }
}
