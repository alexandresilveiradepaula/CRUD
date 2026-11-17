package com.assistencia.controller;

import com.assistencia.service.AgendamentoService;
import com.assistencia.service.ClienteService;
import com.assistencia.service.EquipamentoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;
    private final ClienteService clienteService;
    private final EquipamentoService equipamentoService;

    public AgendamentoController(AgendamentoService agendamentoService,
                                 ClienteService clienteService,
                                 EquipamentoService equipamentoService) {
        this.agendamentoService = agendamentoService;
        this.clienteService = clienteService;
        this.equipamentoService = equipamentoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("agendamentos", agendamentoService.listarOrdenadosPorData());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("equipamentos", equipamentoService.listarTodos());
        return "agendamentos";
    }

    @PostMapping("/salvar")
    public String salvar(@RequestParam("clienteId") Long clienteId,
                         @RequestParam("equipamentoId") Long equipamentoId,
                         @RequestParam("dataAgendamento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataAgendamento,
                         @RequestParam(value = "status", defaultValue = "AGENDADO") String status,
                         @RequestParam(value = "observacoes", required = false) String observacoes) {

        agendamentoService.agendar(clienteId, equipamentoId, dataAgendamento, status, observacoes);
        return "redirect:/agendamentos?sucesso=cadastrado";
    }

    @PostMapping("/reagendar")
    public String reagendar(@RequestParam("id") Long id,
                            @RequestParam("novaData") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime novaData) {
        agendamentoService.reagendar(id, novaData);
        return "redirect:/agendamentos?sucesso=reagendado";
    }
}
