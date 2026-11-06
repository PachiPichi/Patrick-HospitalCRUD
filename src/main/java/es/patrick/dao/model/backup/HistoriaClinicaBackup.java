package es.patrick.dao.model.backup;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoriaClinicaBackup {

    private Long id;
    private Long medicoId;
    private String diagnostico;
    private LocalDate fechaAdmision;
}