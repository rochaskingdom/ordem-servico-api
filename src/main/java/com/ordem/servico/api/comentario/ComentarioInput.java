package com.ordem.servico.api.comentario;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ComentarioInput {

    @NotBlank
    private String descricao;

}
