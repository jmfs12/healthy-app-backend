package com.backend.java.saude_e_bem_estar.exceptions;

public class DataNaoMarcadaException extends ResourceNotFoundException {
    public DataNaoMarcadaException() {
        super("Data não marcada.");
    }

    public DataNaoMarcadaException(String msg) {
        super(msg);
    }
}
