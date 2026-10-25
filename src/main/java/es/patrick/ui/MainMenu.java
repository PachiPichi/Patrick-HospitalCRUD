package es.patrick.ui;

import es.patrick.domain.error.AppError;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MainMenu {

    private static final int OPCION_LISTAR_PACIENTES = 1;
    private static final int OPCION_AÑADIR_PACIENTE = 2;
    private static final int OPCION_ACTUALIZAR_PACIENTE = 3;
    private static final int OPCION_ELIMINAR_PACIENTE = 4;
    private static final int OPCION_MOSTRAR_MEDICOS = 5;
    private static final int OPCION_MOSTRAR_HISTORIA_PACIENTE = 6;
    private static final int OPCION_AÑADIR_HISTORIA_PACIENTE = 7;
    private static final int OPCION_ACTUALIZAR_HISTORIA_PACIENTE = 8;
    private static final int OPCION_ELIMINAR_HISTORIA_PACIENTE = 9;
    private static final int OPCION_SALIR = 10;

    private final UsuarioUI usuarioUi;
    private final PacienteUI pacienteUi;
    private final MedicoUI medicoUi;
    private final HistoriaClinicaUI historiaClinicaUi;

    @Inject
    public MainMenu(UsuarioUI usuarioUi, PacienteUI pacienteUi, MedicoUI medicoUi, HistoriaClinicaUI historiaClinicaUi) {
        this.usuarioUi = usuarioUi;
        this.pacienteUi = pacienteUi;
        this.medicoUi = medicoUi;
        this.historiaClinicaUi = historiaClinicaUi;
    }

    public void run() {
        try {
            IO.println("=== Hospital App ===");

            usuarioUi.login();

            int opcion;
            do {
                printMenu();
                opcion = readOption();

                switch (opcion) {
                    case OPCION_LISTAR_PACIENTES:
                        pacienteUi.getAll();
                        break;
                    case OPCION_AÑADIR_PACIENTE:
                        pacienteUi.save();
                        break;
                    case OPCION_ACTUALIZAR_PACIENTE:
                        pacienteUi.update();
                        break;
                    case OPCION_ELIMINAR_PACIENTE:
                        pacienteUi.delete();
                        break;
                    case OPCION_MOSTRAR_MEDICOS:
                        medicoUi.getAll();
                        break;
                    case OPCION_MOSTRAR_HISTORIA_PACIENTE:
                        historiaClinicaUi.getAllByPaciente();
                        break;
                    case OPCION_AÑADIR_HISTORIA_PACIENTE:
                        historiaClinicaUi.save();
                        break;
                    case OPCION_ACTUALIZAR_HISTORIA_PACIENTE:
                        historiaClinicaUi.update();
                        break;
                    case OPCION_ELIMINAR_HISTORIA_PACIENTE:
                        historiaClinicaUi.delete();
                        break;
                    case OPCION_SALIR:
                        IO.println("Hasta la vista.");
                        break;
                    default:
                        if (opcion != -1) {
                            IO.println("Opción no válida.");
                        }
                }

            } while (opcion != OPCION_SALIR);

        } catch (AppError e) {
            System.err.println("Fallo grave: " + e.getMessage());
            log.error("Fallo grave {}", e.getMessage());
            System.exit(1);
        }
    }

    private void printMenu() {
        IO.println("\n=== Menú principal ===");
        IO.println(OPCION_LISTAR_PACIENTES + ". Mostrar todos los pacientes");
        IO.println(OPCION_AÑADIR_PACIENTE + ". Añadir paciente");
        IO.println(OPCION_ACTUALIZAR_PACIENTE + ". Actualizar paciente");
        IO.println(OPCION_ELIMINAR_PACIENTE + ". Eliminar paciente");
        IO.println(OPCION_MOSTRAR_MEDICOS + ". Mostrar todos los médicos");
        IO.println(OPCION_MOSTRAR_HISTORIA_PACIENTE + ". Mostrar la historia de un paciente");
        IO.println(OPCION_AÑADIR_HISTORIA_PACIENTE + ". Añadir un registro a la historia");
        IO.println(OPCION_ACTUALIZAR_HISTORIA_PACIENTE + ". Actualizar un registro de la historia");
        IO.println(OPCION_ELIMINAR_HISTORIA_PACIENTE + ". Borrar un registro de la historia");
        IO.println(OPCION_SALIR + ". Salir");
        IO.println("Introduzca una opción:");
    }

    private int readOption() {
        String linea = IO.readln();

        if (linea == null || linea.trim().isEmpty()) {
            IO.println("Debe introducir una opción.");
            return -1;
        }

        try {
            return Integer.parseInt(linea.trim());
        } catch (NumberFormatException e) {
            IO.println("Opción no válida. Debe introducir un número.");
            return -1;
        }
    }
}