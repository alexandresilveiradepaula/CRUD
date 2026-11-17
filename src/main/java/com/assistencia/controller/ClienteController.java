package com.assistencia.controller;

import com.assistencia.model.Cliente;
import com.assistencia.repository.ClienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // RN05: Busca por confirmação de termo e listagem dos correspondentes
    @GetMapping
    public String listar(@RequestParam(value = "termo", required = false) String termo, Model model) {
        List<Cliente> clientes;
        if (termo != null && !termo.trim().isEmpty()) {
            clientes = clienteRepository.buscarPorTermo(termo.trim());
        } else {
            clientes = clienteRepository.findAll();
        }
        model.addAttribute("clientes", clientes);
        model.addAttribute("termoBusca", termo);
        return "clientes";
    }

    // Cadastro de novo cliente
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Cliente cliente) {
        clienteRepository.save(cliente);
        return "redirect:/clientes?sucesso=cadastrado";
    }

    // RN05: Editar os dados de um cliente já cadastrado, salvando as alterações no banco de dados
    @PostMapping("/editar")
    public String editar(@RequestParam("id") Long id,
                         @RequestParam("nome") String nome,
                         @RequestParam("telefone") String telefone,
                         @RequestParam("email") String email,
                         @RequestParam(value = "endereco", required = false) String endereco) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));

        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
        if (endereco != null) {
            cliente.setEndereco(endereco);
        }

        clienteRepository.save(cliente);
        return "redirect:/clientes?sucesso=editado";
    }
}
