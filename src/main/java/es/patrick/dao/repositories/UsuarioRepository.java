package es.patrick.dao.repositories;

import es.patrick.dao.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> findByUsername(String username);
}
