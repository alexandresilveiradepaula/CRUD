package com.assistencia.service;

import com.assistencia.model.Usuario;
import com.assistencia.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isBlank()) {
            throw new UsernameNotFoundException("Nome de usuário não informado.");
        }

        String login = username.trim();
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseGet(() -> usuarioRepository.findByLoginIgnoreCase(login)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + login)));

        return new User(
                usuario.getLogin(),
                usuario.getSenhaHash(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
