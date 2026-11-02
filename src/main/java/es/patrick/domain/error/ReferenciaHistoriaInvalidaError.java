package es.patrick.domain.error;

public class ReferenciaHistoriaInvalidaError extends RuntimeException {
    public ReferenciaHistoriaInvalidaError(String message) {
        super(message);
    }
}
