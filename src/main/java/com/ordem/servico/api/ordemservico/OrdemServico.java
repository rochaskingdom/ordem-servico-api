package com.ordem.servico.api.ordemservico;

import com.ordem.servico.api.cliente.Cliente;
import com.ordem.servico.api.comentario.Comentario;
import com.ordem.servico.api.exception.NegocioException;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ordem.servico.api.ordemservico.StatusOrdemServico.ABERTA;
import static com.ordem.servico.api.ordemservico.StatusOrdemServico.FINALIZADA;

@Data
@Entity
@Table(name = "ORDEM_SERVICO")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrdemServico {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private String descricao;

    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    private StatusOrdemServico status;

    private OffsetDateTime dataAbertura;

    private OffsetDateTime dataFinalizacao;

    @OneToMany(mappedBy = "ordemServico")
    private List<Comentario> comentarios = new ArrayList<>();

    public boolean podeSerFinalizada() {
        return ABERTA.equals(getStatus());
    }

    public boolean naoPodeSerFinalizada() {
        return !podeSerFinalizada();
    }

    public void finalizar() {
        if (naoPodeSerFinalizada()) {
            throw new NegocioException("Ordem de serviço não pode ser finalizada");
        }
        setStatus(FINALIZADA);
        setDataFinalizacao(OffsetDateTime.now());
    }
}
