package com.backend.java.saude_e_bem_estar.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    // Construtor que recebe a mensagem customizada de erro
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}