package es.patrick.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class MedicoDTO {
    private Long id;
    private String nombre;
    private String especialidad;
    private String telefono;
}
