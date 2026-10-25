package es.patrick.ui;

import es.patrick.domain.dto.PacienteDTO;
import es.patrick.domain.dto.PacienteDTOAlta;
import es.patrick.domain.error.AppError;
import es.patrick.domain.error.DatabaseError;
import es.patrick.domain.services.PacienteService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class PacienteUI {
    private final PacienteService pacienteService;

    @Inject
    public PacienteUI(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    public void getAll() {
        try {
            IO.println(pacienteService.getAll());
        } catch (DatabaseError e) {
            log.error("Error de BD {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado", e);
            throw new AppError("Error crítico");
        }
    }

    public void save() {
        try {
            IO.println("Introduzca nombre del paciente:");
            String nombre = IO.readln();
            IO.println("Introduzca fecha de nacimiento (yyyy-MM-dd):");
            String fecha = IO.readln();
            IO.println("Introduzca teléfono:");
            String telefono = IO.readln();
            IO.println("Introduzca username:");
            String username = IO.readln();
            IO.println("Introduzca password:");
            String password = IO.readln();

            PacienteDTOAlta pacienteDTO = PacienteDTOAlta.builder()
                    .nombre(nombre)
                    .fechaNacimiento(LocalDate.parse(fecha))
                    .telefono(telefono)
                    .username(username)
                    .password(password)
                    .build();
            IO.println("Paciente insertado con id: " + pacienteService.add(pacienteDTO));
        } catch (DatabaseError e) {
            log.error("Error de base de datos en PatientService.addPatient()", e);
            IO.println("No se pudo añadir el paciente.");
        } catch (Exception e) {
            log.error("Error inesperado", e);
            IO.println("Datos incorrectos. Revise los valores introducidos.");
        }
    }

    public void update() {
        try {
            IO.println("Introduzca el id del paciente a actualizar:");
            Long id = Long.parseLong(IO.readln());
            PacienteDTO pacienteActual = pacienteService.findById(id);
            if (pacienteActual == null) {
                IO.println("No existe ningún paciente con ese id.");
                return;
            }

            IO.println("Nombre actual: " + pacienteActual.getNombre() + ". Introduzca nuevo nombre (vacío para mantener):");
            String nombre = IO.readln();
            IO.println("Fecha de nacimiento actual: " + pacienteActual.getFechaNacimiento() + ". Introduzca nueva fecha (yyyy-MM-dd, vacío para mantener):");
            String fecha = IO.readln();
            IO.println("Teléfono actual: " + pacienteActual.getTelefono() + ". Introduzca nuevo teléfono (vacío para mantener):");
            String telefono = IO.readln();

            PacienteDTO pacienteDTO = PacienteDTO.builder()
                    .id(id)
                    .nombre(nombre.isBlank() ? pacienteActual.getNombre() : nombre)
                    .fechaNacimiento(fecha.isBlank() ? pacienteActual.getFechaNacimiento() : LocalDate.parse(fecha))
                    .telefono(telefono.isBlank() ? pacienteActual.getTelefono() : telefono)
                    .build();

            boolean actualizado = pacienteService.update(id, pacienteDTO);
            IO.println(actualizado ? "Paciente actualizado correctamente." : "No se pudo actualizar el paciente.");
        } catch (NumberFormatException e) {
            IO.println("El id introducido no es válido.");
        } catch (Exception e) {
            log.error("Error inesperado al actualizar paciente", e);
            IO.println("No se pudo actualizar el paciente.");
        }
    }

    public void delete() {
        try {
            IO.println("Introduzca el id del paciente a eliminar:");
            Long id = Long.parseLong(IO.readln());
            boolean eliminado = pacienteService.delete(id);
            IO.println(eliminado ? "Paciente eliminado correctamente." : "No existe ningún paciente con ese id.");
        } catch (NumberFormatException e) {
            IO.println("El id introducido no es válido.");
        } catch (DatabaseError e) {
            log.error("Error de base de datos al eliminar paciente", e);
            IO.println("No se pudo eliminar el paciente.");
        } catch (Exception e) {
            log.error("Error inesperado al eliminar paciente", e);
            IO.println("No se pudo eliminar el paciente.");
        }
    }
}