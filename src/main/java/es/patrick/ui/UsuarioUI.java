package es.patrick.ui;

import es.patrick.domain.dto.UsuarioDTO;
import es.patrick.domain.services.UsuarioService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UsuarioUI {
    private final UsuarioService usuarioService;

    @Inject
    public UsuarioUI(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    public void login() {

        final int MAX_INTENTOS = 3;
        int intento = 0;
        IO.println("Por favor, introduzca sus credenciales: ");

        while (intento < MAX_INTENTOS) {
            IO.println("Usuario (0 para salir): ");
            String username = IO.readln().trim();
            if (username.equals("0")) { return;}

            IO.println("Contraseña: ");
            String password = IO.readln();
            if (password.isEmpty() || username.isEmpty()) {
                IO.println("Por favor, introduzca sus credenciales");
                continue;
            }
            UsuarioDTO credenciales = new UsuarioDTO(username, password);

            boolean ok = usuarioService.login(credenciales);
            if (ok) {
                IO.println("Bienvenido al sistema.");
                log.info("Bienvenido al sistema.");
                break;
            } else {
                IO.println("Credenciales incorrectas, inténtalo de nuevo. ");
                intento++;
            }


        }
        IO.println("Has superado el número de intentos");
    }
}
