package com.ordem.servico.api.ordemservico;

import com.ordem.servico.api.cliente.Cliente;
import com.ordem.servico.api.cliente.ClienteRepository;
import com.ordem.servico.api.comentario.Comentario;
import com.ordem.servico.api.comentario.ComentarioRepository;
import com.ordem.servico.api.exception.EntidadeNaoEncotradaException;
import com.ordem.servico.api.exception.NegocioException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ordem.servico.api.ordemservico.StatusOrdemServico.ABERTA;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class OrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<OrdemServicoModel> lista() {
        return toCollectionModel(ordemServicoRepository.findAll());
    }

    public ResponseEntity<OrdemServicoModel> buscaOrdem(Long ordemServicoId) {
        Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);
        if (ordemServico.isPresent()) {
            OrdemServicoModel ordemServicoModel = toModel(ordemServico.get());
            return ResponseEntity.ok(ordemServicoModel);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<OrdemServicoModel> insere(@Valid OrdemServico ordemServico) {
        if (nonNull(ordemServico.getId()) || isNull(ordemServico.getCliente().getId())) {
            return ResponseEntity.badRequest().build();
        }
        Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
                .orElseThrow(() -> new NegocioException("Cliente não encontrado!"));
        ordemServico.setCliente(cliente);
        ordemServico.setStatus(ABERTA);
        ordemServico.setDataAbertura(OffsetDateTime.now());
        var salvaOrdem = toModel(ordemServicoRepository.save(ordemServico));
        return ResponseEntity.status(HttpStatus.CREATED).body(salvaOrdem);
    }

    public Comentario insereComentario(Long ordemServicoId, String descricao) {
        OrdemServico ordemServico = buscaOrdemPeloId(ordemServicoId);
        Comentario comentario = Comentario.builder()
                .dataEnvio(OffsetDateTime.now())
                .descricao(descricao)
                .ordemServico(ordemServico)
                .build();
        return comentarioRepository.save(comentario);
    }

    public void finalizaOrdem(Long ordemServicoId) {
        OrdemServico ordemServico = buscaOrdemPeloId(ordemServicoId);
        ordemServico.finalizar();
        ordemServicoRepository.save(ordemServico);
    }

    private OrdemServico buscaOrdemPeloId(Long ordemServicoId) {
        return ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new EntidadeNaoEncotradaException("Ordem de serviço não encontrada"));
    }

    protected OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
        return modelMapper.map(ordemServicoInput, OrdemServico.class);
    }

    private OrdemServicoModel toModel(OrdemServico ordemServico) {
        return modelMapper.map(ordemServico, OrdemServicoModel.class);
    }

    private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico) {
        return ordensServico.stream().map(this::toModel).collect(Collectors.toList());
    }
}
