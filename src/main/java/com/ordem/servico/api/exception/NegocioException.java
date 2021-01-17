package com.ordem.servico.api.exception;

public class NegocioException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NegocioException(String message) {
        super(message);
    }

    public static String mensagem001() {
        throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
    }
}
