package es.patrick.domain.error;

import es.patrick.common.Constantes;

public class PacienteDuplicadoError extends DatabaseError {
    public PacienteDuplicadoError() {
        super(Constantes.PACIENTE_DUPLICADO_ERROR);
    }
}
