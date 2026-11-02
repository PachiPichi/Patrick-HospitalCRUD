package es.patrick.ui;

import es.patrick.domain.dto.HistoriaClinicaDTO;
import es.patrick.domain.error.AppError;
import es.patrick.domain.error.DatabaseError;
import es.patrick.domain.error.ReferenciaHistoriaInvalidaError;
import es.patrick.domain.services.HistoriaClinicaService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class HistoriaClinicaUI {
    private final HistoriaClinicaService historiaClinicaService;

    @Inject
    public HistoriaClinicaUI(HistoriaClinicaService historiaClinicaService) {
        this.historiaClinicaService = historiaClinicaService;
    }

    public void getAllByPaciente() {
        try {
            IO.println("Introduzca el id del paciente:");
            Long pacienteId = Long.parseLong(IO.readln());
            IO.println(historiaClinicaService.getAllByPacienteId(pacienteId));
        } catch (NumberFormatException e) {
            IO.println("El id introducido no es válido.");
        } catch (DatabaseError e) {
            log.error("Error de BD {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado", e);
            throw new AppError("Error crítico");
        }
    }

    public void save() {
        try {
            IO.println("Introduzca el id del paciente:");
            Long pacienteId = Long.parseLong(IO.readln());
            IO.println("Introduzca el id del médico:");
            Long medicoId = Long.parseLong(IO.readln());
            IO.println("Introduzca el diagnóstico:");
            String diagnostico = IO.readln();
            IO.println("Introduzca la fecha de admisión (yyyy-MM-dd):");
            LocalDate fechaAdmision = LocalDate.parse(IO.readln());

            HistoriaClinicaDTO historiaClinicaDTO = HistoriaClinicaDTO.builder()
                    .pacienteId(pacienteId)
                    .medicoId(medicoId)
                    .diagnostico(diagnostico)
                    .fechaAdmision(fechaAdmision)
                    .build();

            Long id = historiaClinicaService.add(historiaClinicaDTO);
            IO.println(id != null ? "Registro añadido con id: " + id : "No se pudo añadir el registro.");
        } catch (ReferenciaHistoriaInvalidaError e) {
            IO.println(e.getMessage());
        } catch (NumberFormatException e) {
            IO.println("Los ids introducidos no son válidos.");
        } catch (DatabaseError e) {
            log.error("Error de base de datos al añadir registro", e);
            IO.println("No se pudo añadir el registro por un error de base de datos.");
        } catch (Exception e) {
            log.error("Error al añadir registro a la historia", e);
            IO.println("No se pudo añadir el registro.");
        }
    }

    public void update() {
        try {
            IO.println("Introduzca el id del registro a actualizar:");
            Long id = Long.parseLong(IO.readln());
            HistoriaClinicaDTO historiaActual = historiaClinicaService.findById(id);
            if (historiaActual == null) {
                IO.println("No existe ningún registro con ese id.");
                return;
            }

            IO.println("Paciente actual: " + historiaActual.getPacienteId() + ". Introduzca nuevo id de paciente (vacío para mantener):");
            String pacienteInput = IO.readln();
            IO.println("Médico actual: " + historiaActual.getMedicoId() + ". Introduzca nuevo id de médico (vacío para mantener):");
            String medicoInput = IO.readln();
            IO.println("Diagnóstico actual: " + historiaActual.getDiagnostico() + ". Introduzca nuevo diagnóstico (vacío para mantener):");
            String diagnostico = IO.readln();
            IO.println("Fecha de admisión actual: " + historiaActual.getFechaAdmision() + ". Introduzca nueva fecha (yyyy-MM-dd, vacío para mantener):");
            String fechaInput = IO.readln();

            HistoriaClinicaDTO historiaClinicaDTO = HistoriaClinicaDTO.builder()
                    .id(id)
                    .pacienteId(pacienteInput.isBlank() ? historiaActual.getPacienteId() : Long.parseLong(pacienteInput))
                    .medicoId(medicoInput.isBlank() ? historiaActual.getMedicoId() : Long.parseLong(medicoInput))
                    .diagnostico(diagnostico.isBlank() ? historiaActual.getDiagnostico() : diagnostico)
                    .fechaAdmision(fechaInput.isBlank() ? historiaActual.getFechaAdmision() : LocalDate.parse(fechaInput))
                    .build();

            boolean actualizado = historiaClinicaService.update(id, historiaClinicaDTO);
            IO.println(actualizado ? "Registro actualizado correctamente." : "No se pudo actualizar el registro.");
        } catch (ReferenciaHistoriaInvalidaError e) {
            IO.println(e.getMessage());
        } catch (NumberFormatException e) {
            IO.println("Los datos introducidos no son válidos.");
        } catch (DatabaseError e) {
            log.error("Error de base de datos al actualizar historia", e);
            IO.println("No se pudo actualizar el registro por un error de base de datos.");
        } catch (Exception e) {
            log.error("Error inesperado al actualizar la historia", e);
            IO.println("No se pudo actualizar el registro.");
        }
    }

    public void delete() {
        try {
            IO.println("Introduzca el id del registro a borrar:");
            Long id = Long.parseLong(IO.readln());
            boolean borrado = historiaClinicaService.delete(id);
            IO.println(borrado ? "Registro borrado correctamente." : "No existe ningún registro con ese id.");
        } catch (NumberFormatException e) {
            IO.println("El id introducido no es válido.");
        } catch (DatabaseError e) {
            log.error("Error de base de datos al borrar registro", e);
            IO.println("No se pudo borrar el registro.");
        } catch (Exception e) {
            log.error("Error inesperado al borrar registro", e);
            IO.println("No se pudo borrar el registro.");
        }
    }
}
