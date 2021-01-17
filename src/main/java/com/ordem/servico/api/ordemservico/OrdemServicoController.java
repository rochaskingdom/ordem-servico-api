package com.ordem.servico.api.ordemservico;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService ordemServicoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<OrdemServicoModel> lista() {
        return ordemServicoService.lista();
    }

    @GetMapping("/{ordemServicoId}")
    public ResponseEntity<OrdemServicoModel> buscaOrdem(@PathVariable Long ordemServicoId) {
        return ordemServicoService.buscaOrdem(ordemServicoId);
    }

    @PostMapping
    public ResponseEntity<OrdemServicoModel> insere(@RequestBody @Valid OrdemServicoInput ordemServicoInput) {
        OrdemServico ordemServico = ordemServicoService.toEntity(ordemServicoInput);
        return ordemServicoService.insere(ordemServico);
    }

}
