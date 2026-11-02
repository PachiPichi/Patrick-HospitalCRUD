package es.patrick.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class HistoriaClinicaDTO {
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private String diagnostico;
    private LocalDate fechaAdmision;
}
