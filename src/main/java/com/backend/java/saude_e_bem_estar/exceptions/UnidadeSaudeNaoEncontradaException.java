package com.backend.java.saude_e_bem_estar.exceptions;

public class UnidadeSaudeNaoEncontradaException extends ResourceNotFoundException {
    public UnidadeSaudeNaoEncontradaException() {
        super("Unidade de saúde não encontrada.");
    }

    public UnidadeSaudeNaoEncontradaException(String msg) {
        super(msg);
    }
}
