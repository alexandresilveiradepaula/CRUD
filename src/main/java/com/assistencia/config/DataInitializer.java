package com.assistencia.config;

import com.assistencia.model.Usuario;
import com.assistencia.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        try {
            // Garante a existência do usuário 'admin' para que o login funcione imediatamente em qualquer banco
            if (usuarioRepository.findByLogin("admin").isEmpty()) {
                Usuario admin = new Usuario("Administrador do Sistema", "admin", passwordEncoder.encode("123456"));
                admin.setUltimoAcesso(LocalDateTime.now());
                usuarioRepository.save(admin);
            }

            if (usuarioRepository.findByLogin("carlos").isEmpty()) {
                Usuario carlos = new Usuario("Carlos Atendente", "carlos", passwordEncoder.encode("123456"));
                carlos.setUltimoAcesso(LocalDateTime.now());
                usuarioRepository.save(carlos);
            }

            if (usuarioRepository.findByLogin("mariana").isEmpty()) {
                Usuario mariana = new Usuario("Mariana Especialista", "mariana", passwordEncoder.encode("123456"));
                mariana.setUltimoAcesso(LocalDateTime.now());
                usuarioRepository.save(mariana);
            }
        } catch (Exception e) {
            System.err.println("Aviso na inicialização automática de usuários: " + e.getMessage());
        }
    }
}
