package com.ordem.servico.api.exception;

public class EntidadeNaoEncotradaException extends NegocioException{


    private static final long serialVersionUID = 1L;

    public EntidadeNaoEncotradaException(String message) {
        super(message);
    }
}
