package es.patrick.dao.model.backup;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteBackup {

    private Long id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String telefono;

    private List<HistoriaClinicaBackup> historiasClinicas;
}