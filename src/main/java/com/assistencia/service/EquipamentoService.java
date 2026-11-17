package com.assistencia.service;

import com.assistencia.model.Cliente;
import com.assistencia.model.Equipamento;
import com.assistencia.repository.EquipamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;
    private final ClienteService clienteService;

    public EquipamentoService(EquipamentoRepository equipamentoRepository, ClienteService clienteService) {
        this.equipamentoRepository = equipamentoRepository;
        this.clienteService = clienteService;
    }

    public List<Equipamento> listarTodos() {
        return equipamentoRepository.findAll();
    }

    public Equipamento buscarPorId(Long id) {
        return equipamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado com o ID: " + id));
    }

    @Transactional
    public Equipamento salvar(Long clienteId, String tipo, String marca, String modelo, String numeroSerie, String defeitoDeclarado) {
        Cliente cliente = clienteService.buscarPorId(clienteId);
        Equipamento equipamento = new Equipamento(cliente, tipo, marca, modelo, numeroSerie, defeitoDeclarado);
        return equipamentoRepository.save(equipamento);
    }

    public long totalEquipamentos() {
        return equipamentoRepository.count();
    }
}
