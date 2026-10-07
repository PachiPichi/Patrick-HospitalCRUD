package es.patrick.ui;

public class MainMenu {

    private final UsuarioUi usuarioUi;

    public MainMenu(UsuarioUi usuarioUi) {
        this.usuarioUi = usuarioUi;
    }
    public static void run() {
        try {
            IO.println("Hospital App");
            UsuarioUi.login();
        } catch (Exception e){
            System.err.println("Error grave: " + e.getMessage());
            System.exit(1);
        }
    }
}
