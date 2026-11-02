package es.patrick.dao.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Medico {
    private Long id;
    private String nombre;
    private String especialidad;
    private String telefono;
    private Usuario usuario;
}
