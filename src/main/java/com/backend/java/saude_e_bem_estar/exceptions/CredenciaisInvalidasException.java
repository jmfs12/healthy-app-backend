package com.backend.java.saude_e_bem_estar.exceptions;

public class CredenciaisInvalidasException extends BusinessException {
    public CredenciaisInvalidasException() {
        super("E-mail ou senha incorretos.");
    }

    public CredenciaisInvalidasException(String msg) {
        super(msg);
    }
}
