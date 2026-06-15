package com.backend.java.saude_e_bem_estar.exceptions;

public class CpfJaCadastradoException extends BusinessException {
    public CpfJaCadastradoException() {
        super("CPF já cadastrado.");
    }

    public CpfJaCadastradoException(String msg) {
        super(msg);
    }
}
