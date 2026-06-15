package com.backend.java.saude_e_bem_estar.exceptions;

public class EmailJaCadastradoException extends BusinessException {
    public EmailJaCadastradoException() {
        super("E-mail já cadastrado.");
    }

    public EmailJaCadastradoException(String msg) {
        super(msg);
    }
}
