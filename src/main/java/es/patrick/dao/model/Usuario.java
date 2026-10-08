package es.patrick.dao.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Usuario {
    private Long id;
    private String username;
    private String password;
    private Long pacienteId;
    private Long medicoId;
}
