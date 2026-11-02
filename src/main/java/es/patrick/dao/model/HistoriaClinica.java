package es.patrick.dao.model;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HistoriaClinica {
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private String diagnostico;
    private LocalDate fechaAdmision;
}
