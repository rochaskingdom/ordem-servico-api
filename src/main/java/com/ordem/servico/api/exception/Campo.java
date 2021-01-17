package com.ordem.servico.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Campo {

    private String nome;
    private String mensagem;
}
