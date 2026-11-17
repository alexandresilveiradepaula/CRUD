package com.assistencia.service;

import com.assistencia.model.Agendamento;
import com.assistencia.model.Cliente;
import com.assistencia.model.Equipamento;
import com.assistencia.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteService clienteService;
    private final EquipamentoService equipamentoService;

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              ClienteService clienteService,
                              EquipamentoService equipamentoService) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteService = clienteService;
        this.equipamentoService = equipamentoService;
    }

    public List<Agendamento> listarOrdenadosPorData() {
        return agendamentoRepository.findAllOrderByDataAgendamentoAsc();
    }

    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado com o ID: " + id));
    }

    @Transactional
    public Agendamento agendar(Long clienteId, Long equipamentoId, LocalDateTime dataAgendamento, String status, String observacoes) {
        Cliente cliente = clienteService.buscarPorId(clienteId);
        Equipamento equipamento = equipamentoService.buscarPorId(equipamentoId);
        Agendamento agendamento = new Agendamento(cliente, equipamento, dataAgendamento, status, observacoes);
        return agendamentoRepository.save(agendamento);
    }

    @Transactional
    public Agendamento reagendar(Long id, LocalDateTime novaData) {
        Agendamento agendamento = buscarPorId(id);
        agendamento.setDataAgendamento(novaData);
        return agendamentoRepository.save(agendamento);
    }

    public long totalAgendamentos() {
        return agendamentoRepository.count();
    }
}
