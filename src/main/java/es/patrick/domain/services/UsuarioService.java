package es.patrick.domain.services;

import es.patrick.dao.model.Usuario;
import es.patrick.dao.repositories.UsuarioRepository;
import jakarta.inject.Inject;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Inject
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    public boolean login(Usuario usuario) {
        return usuarioRepository.findByUsername(usuario.getUsername())
                .map(u -> u.getUsername().equals(usuario.getUsername())
                && u.getPassword().equals(usuario.getPassword()))
                .orElse(false);
    }
}
