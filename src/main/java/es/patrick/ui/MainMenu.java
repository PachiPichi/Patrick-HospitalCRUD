package es.patrick.ui;

import jakarta.inject.Inject;

public class MainMenu {

    private final UsuarioUi usuarioUi;

    @Inject
    public MainMenu(UsuarioUi usuarioUi) {
        this.usuarioUi = usuarioUi;
    }
    public void run() {
        try {
            IO.println("Hospital App");
            usuarioUi.login();
        } catch (Exception e){
            System.err.println("Error grave: " + e.getMessage());
            System.exit(1);
        }
    }
}
