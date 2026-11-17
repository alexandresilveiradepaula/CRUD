package com.assistencia.controller;

import com.assistencia.repository.UsuarioRepository;
import com.assistencia.service.AgendamentoService;
import com.assistencia.service.ClienteService;
import com.assistencia.service.EquipamentoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final ClienteService clienteService;
    private final EquipamentoService equipamentoService;
    private final AgendamentoService agendamentoService;
    private final UsuarioRepository usuarioRepository;

    public DashboardController(ClienteService clienteService,
                               EquipamentoService equipamentoService,
                               AgendamentoService agendamentoService,
                               UsuarioRepository usuarioRepository) {
        this.clienteService = clienteService;
        this.equipamentoService = equipamentoService;
        this.agendamentoService = agendamentoService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String login = userDetails != null ? userDetails.getUsername() : "Usuário";
        String nomeCompleto = usuarioRepository.findByLogin(login)
                .map(u -> u.getNome())
                .orElse(login);

        model.addAttribute("nomeUsuario", nomeCompleto);
        model.addAttribute("totalClientes", clienteService.totalClientes());
        model.addAttribute("totalEquipamentos", equipamentoService.totalEquipamentos());
        model.addAttribute("totalAgendamentos", agendamentoService.totalAgendamentos());
        model.addAttribute("proximosAgendamentos", agendamentoService.listarOrdenadosPorData());

        return "dashboard";
    }
}
