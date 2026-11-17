package com.assistencia.service;

import com.assistencia.model.Cliente;
import com.assistencia.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public List<Cliente> buscarPorTermo(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return listarTodos();
        }
        return clienteRepository.buscarPorTermo(termo.trim());
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com o ID: " + id));
    }

    @Transactional
    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente editar(Long id, String nome, String telefone, String email, String endereco) {
        Cliente cliente = buscarPorId(id);
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
        if (endereco != null) {
            cliente.setEndereco(endereco);
        }
        return clienteRepository.save(cliente);
    }

    public long totalClientes() {
        return clienteRepository.count();
    }
}
