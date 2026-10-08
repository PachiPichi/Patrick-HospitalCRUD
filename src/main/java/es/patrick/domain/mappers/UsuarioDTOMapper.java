package es.patrick.domain.mappers;

import es.patrick.dao.model.Usuario;
import es.patrick.domain.dto.UsuarioDTO;

public class UsuarioDTOMapper {
    public UsuarioDTO toDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .build();
    }

    public Usuario toEntity(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .username(usuarioDTO.getUsername())
                .password(usuarioDTO.getPassword())
                .build();
    }
}
