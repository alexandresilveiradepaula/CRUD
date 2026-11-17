package com.assistencia.controller;

import com.assistencia.model.Cliente;
import com.assistencia.model.Equipamento;
import com.assistencia.repository.ClienteRepository;
import com.assistencia.repository.EquipamentoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/equipamentos")
public class EquipamentoController {

    private final EquipamentoRepository equipamentoRepository;
    private final ClienteRepository clienteRepository;

    public EquipamentoController(EquipamentoRepository equipamentoRepository, ClienteRepository clienteRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("equipamentos", equipamentoRepository.findAll());
        model.addAttribute("clientes", clienteRepository.findAll());
        return "equipamentos";
    }

    // RN07: Obrigatoriamente associar o equipamento a um cliente previamente cadastrado
    @PostMapping("/salvar")
    public String salvar(@RequestParam("clienteId") Long clienteId,
                         @RequestParam("tipo") String tipo,
                         @RequestParam("marca") String marca,
                         @RequestParam("modelo") String modelo,
                         @RequestParam(value = "numeroSerie", required = false) String numeroSerie,
                         @RequestParam(value = "defeitoDeclarado", required = false) String defeitoDeclarado) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente obrigatório não encontrado: " + clienteId));

        Equipamento equipamento = new Equipamento(cliente, tipo, marca, modelo, numeroSerie, defeitoDeclarado);
        equipamentoRepository.save(equipamento);

        return "redirect:/equipamentos?sucesso=true";
    }
}
