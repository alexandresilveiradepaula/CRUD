package com.assistencia.controller;

import com.assistencia.service.ClienteService;
import com.assistencia.service.EquipamentoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/equipamentos")
public class EquipamentoController {

    private final EquipamentoService equipamentoService;
    private final ClienteService clienteService;

    public EquipamentoController(EquipamentoService equipamentoService, ClienteService clienteService) {
        this.equipamentoService = equipamentoService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("equipamentos", equipamentoService.listarTodos());
        model.addAttribute("clientes", clienteService.listarTodos());
        return "equipamentos";
    }

    @PostMapping("/salvar")
    public String salvar(@RequestParam("clienteId") Long clienteId,
                         @RequestParam("tipo") String tipo,
                         @RequestParam("marca") String marca,
                         @RequestParam("modelo") String modelo,
                         @RequestParam(value = "numeroSerie", required = false) String numeroSerie,
                         @RequestParam(value = "defeitoDeclarado", required = false) String defeitoDeclarado) {

        equipamentoService.salvar(clienteId, tipo, marca, modelo, numeroSerie, defeitoDeclarado);
        return "redirect:/equipamentos?sucesso=true";
    }
}
