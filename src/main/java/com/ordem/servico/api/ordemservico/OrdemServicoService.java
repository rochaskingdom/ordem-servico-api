package com.ordem.servico.api.ordemservico;

import com.ordem.servico.api.cliente.Cliente;
import com.ordem.servico.api.cliente.ClienteRepository;
import com.ordem.servico.api.exception.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static com.ordem.servico.api.ordemservico.StatusOrdemServico.ABERTA;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class OrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<OrdemServico> lista() {
        return ordemServicoRepository.findAll();
    }

    public ResponseEntity<OrdemServico> buscaOrdem(Long ordemServicoId) {
        Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);
        return ordemServico.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<OrdemServico> insere(@Valid OrdemServico ordemServico) {
        if (nonNull(ordemServico.getId()) || isNull(ordemServico.getCliente().getId())) {
            return ResponseEntity.badRequest().build();
        }
        Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
                .orElseThrow(() -> new NegocioException("Cliente não encontrado!"));
        ordemServico.setCliente(cliente);
        ordemServico.setStatus(ABERTA);
        ordemServico.setDataAbertura(OffsetDateTime.now());
        var salvaOrdem = ordemServicoRepository.save(ordemServico);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvaOrdem);
    }
}
