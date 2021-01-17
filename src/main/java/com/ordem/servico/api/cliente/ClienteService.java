package com.ordem.servico.api.cliente;

import com.ordem.servico.api.exception.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> lista() {
        return clienteRepository.findAll();
    }

    public ResponseEntity<Cliente> buscaCliente(Long clienteId) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Cliente> insere(@Valid Cliente cliente) {
        Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
        if (nonNull(cliente.getId())) {
            return ResponseEntity.badRequest().build();
        } else if (nonNull(clienteExistente) && !clienteExistente.equals(cliente)) {
            throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
        }
        Cliente clienteInsere = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteInsere);
    }

    public ResponseEntity<Cliente> atualiza(Long clienteId, @Valid Cliente cliente) {
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }
        cliente.setId(clienteId);
        cliente = clienteRepository.save(cliente);
        return ResponseEntity.ok(cliente);
    }

    public ResponseEntity<?> exclui(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepository.deleteById(clienteId);
        return ResponseEntity.noContent().build();
    }

}
