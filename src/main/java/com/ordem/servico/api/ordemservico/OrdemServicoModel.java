package com.ordem.servico.api.ordemservico;

import com.ordem.servico.api.cliente.ClienteModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class OrdemServicoModel {

    private Long id;
    private ClienteModel cliente;
    private String descricao;
    private BigDecimal preco;
    private StatusOrdemServico status;
    private OffsetDateTime dataAbertura;
    private OffsetDateTime dataFinalizacao;

}
