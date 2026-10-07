package es.patrick.ui;

import es.patrick.dao.model.Usuario;
import es.patrick.domain.services.UsuarioService;
import jakarta.inject.Inject;

public class UsuarioUi {
    private final UsuarioService usuarioService;

    @Inject
    public UsuarioUi(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    public static void login() {
        IO.println("Por favor, introduzca sus credenciales: ");

        while (true) {
            IO.println("Usuario: ");
            String username = IO.readln();
            if (username.isEmpty()) continue;

            IO.println("Contraseña: ");
            String password = IO.readln();
            if (password.isEmpty()) continue;
            Usuario credenciales = new Usuario(username, password);


        }
    }
}
