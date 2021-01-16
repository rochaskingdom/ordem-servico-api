package com.ordem.servico.api.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> lista() {
        return clienteService.lista();
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> buscaCliente(@PathVariable Long clienteId) {
        return clienteService.buscaCliente(clienteId);
    }

    @PostMapping
    public ResponseEntity<Cliente> insere(@RequestBody @Valid Cliente cliente) {
        return clienteService.insere(cliente);
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> atualiza(@PathVariable Long clienteId, @RequestBody @Valid Cliente cliente) {
        return clienteService.atualiza(clienteId, cliente);
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<?> exclui(@PathVariable Long clienteId) {
        return clienteService.exclui(clienteId);
    }
}
