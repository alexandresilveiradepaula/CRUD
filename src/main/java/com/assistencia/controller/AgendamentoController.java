package com.assistencia.controller;

import com.assistencia.model.Agendamento;
import com.assistencia.model.Cliente;
import com.assistencia.model.Equipamento;
import com.assistencia.repository.AgendamentoRepository;
import com.assistencia.repository.ClienteRepository;
import com.assistencia.repository.EquipamentoRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final EquipamentoRepository equipamentoRepository;

    public AgendamentoController(AgendamentoRepository agendamentoRepository,
                                 ClienteRepository clienteRepository,
                                 EquipamentoRepository equipamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;
        this.equipamentoRepository = equipamentoRepository;
    }

    // RN06: Listagem dos atendimentos ordenados por data de agendamento com dados de cliente e equipamento
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("agendamentos", agendamentoRepository.findAllOrderByDataAgendamentoAsc());
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("equipamentos", equipamentoRepository.findAll());
        return "agendamentos";
    }

    // Cadastro de novo agendamento
    @PostMapping("/salvar")
    public String salvar(@RequestParam("clienteId") Long clienteId,
                         @RequestParam("equipamentoId") Long equipamentoId,
                         @RequestParam("dataAgendamento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataAgendamento,
                         @RequestParam(value = "status", defaultValue = "AGENDADO") String status,
                         @RequestParam(value = "observacoes", required = false) String observacoes) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + clienteId));
        Equipamento equipamento = equipamentoRepository.findById(equipamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Equipamento inválido: " + equipamentoId));

        Agendamento agendamento = new Agendamento(cliente, equipamento, dataAgendamento, status, observacoes);
        agendamentoRepository.save(agendamento);

        return "redirect:/agendamentos?sucesso=cadastrado";
    }

    // RN06: Atualizar a data de um agendamento existente, salvando no banco de dados
    @PostMapping("/reagendar")
    public String reagendar(@RequestParam("id") Long id,
                            @RequestParam("novaData") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime novaData) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado: " + id));

        agendamento.setDataAgendamento(novaData);
        agendamentoRepository.save(agendamento);

        return "redirect:/agendamentos?sucesso=reagendado";
    }
}
