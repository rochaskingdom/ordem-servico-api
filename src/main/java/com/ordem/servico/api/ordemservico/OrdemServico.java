package com.ordem.servico.api.ordemservico;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ordem.servico.api.cliente.Cliente;
import com.ordem.servico.api.utils.ValidationGroups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Entity
@Table(name = "ORDEM_SERVICO")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrdemServico {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @NotNull
    @ManyToOne
    @ConvertGroup(to = ValidationGroups.ClienteId.class)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @NotBlank
    private String descricao;

    @NotNull
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    @JsonProperty(access = READ_ONLY)
    private StatusOrdemServico status;

    @JsonProperty(access = READ_ONLY)
    private OffsetDateTime dataAbertura;

    @JsonProperty(access = READ_ONLY)
    private LocalDateTime dataFinalizacao;

}
