package com.backend.java.saude_e_bem_estar.exceptions;

public class UsuarioNaoEncontradoException extends ResourceNotFoundException {
    public UsuarioNaoEncontradoException() {
        super("Usuário não encontrado.");
    }

    public UsuarioNaoEncontradoException(String msg) {
        super(msg);
    }
}
