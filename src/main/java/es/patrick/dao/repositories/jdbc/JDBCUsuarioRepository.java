package es.patrick.dao.repositories.jdbc;

import es.patrick.dao.model.Usuario;
import es.patrick.dao.repositories.UsuarioRepository;

import java.util.Optional;

//UsuarioRepositoyImpl
public class JDBCUsuarioRepository implements UsuarioRepository {


    @Override
    public Optional<Usuario> findByUsername(String username) {
        return Optional.of(new Usuario("paciente1", "1234"));
    }
}
