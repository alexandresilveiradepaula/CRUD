package com.assistencia.controller;

import com.assistencia.model.Cliente;
import com.assistencia.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listar(@RequestParam(value = "termo", required = false) String termo, Model model) {
        model.addAttribute("clientes", clienteService.buscarPorTermo(termo));
        model.addAttribute("termoBusca", termo);
        return "clientes";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Cliente cliente) {
        clienteService.salvar(cliente);
        return "redirect:/clientes?sucesso=cadastrado";
    }

    @PostMapping("/editar")
    public String editar(@RequestParam("id") Long id,
                         @RequestParam("nome") String nome,
                         @RequestParam("telefone") String telefone,
                         @RequestParam("email") String email,
                         @RequestParam(value = "endereco", required = false) String endereco) {
        clienteService.editar(id, nome, telefone, email, endereco);
        return "redirect:/clientes?sucesso=editado";
    }
}
