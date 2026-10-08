package es.patrick.ui;

import es.patrick.domain.dto.UsuarioDTO;
import es.patrick.domain.services.UsuarioService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UsuarioUi {
    private final UsuarioService usuarioService;

    @Inject
    public UsuarioUi(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    public void login() {
        IO.println("Por favor, introduzca sus credenciales: ");

        while (true) {
            IO.println("Usuario: ");
            String username = IO.readln();
            if (username.isEmpty()) continue;

            IO.println("Contraseña: ");
            String password = IO.readln();
            if (password.isEmpty()) continue;
            UsuarioDTO credenciales = new UsuarioDTO(username, password);

            boolean ok = usuarioService.login(credenciales);
            if (ok) {
                IO.println("Bienvenido al sistema.");
                log.info("Bienvenido al sistema.");
                break;
            } else {
                IO.println("Credenciales incorrectas, inténtalo de nuevo. ");
            }

        }
    }
}
