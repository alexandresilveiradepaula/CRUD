package com.assistencia.controller;

import com.assistencia.repository.AgendamentoRepository;
import com.assistencia.repository.ClienteRepository;
import com.assistencia.repository.EquipamentoRepository;
import com.assistencia.repository.UsuarioRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final ClienteRepository clienteRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public DashboardController(ClienteRepository clienteRepository,
                               EquipamentoRepository equipamentoRepository,
                               AgendamentoRepository agendamentoRepository,
                               UsuarioRepository usuarioRepository) {
        this.clienteRepository = clienteRepository;
        this.equipamentoRepository = equipamentoRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // RN04: Exibir o nome do usuário logado e dados gerais
        String login = userDetails != null ? userDetails.getUsername() : "Usuário";
        String nomeCompleto = usuarioRepository.findByLogin(login)
                .map(u -> u.getNome())
                .orElse(login);

        model.addAttribute("nomeUsuario", nomeCompleto);
        model.addAttribute("totalClientes", clienteRepository.count());
        model.addAttribute("totalEquipamentos", equipamentoRepository.count());
        model.addAttribute("totalAgendamentos", agendamentoRepository.count());
        model.addAttribute("proximosAgendamentos", agendamentoRepository.findAllOrderByDataAgendamentoAsc());

        return "dashboard";
    }
}
