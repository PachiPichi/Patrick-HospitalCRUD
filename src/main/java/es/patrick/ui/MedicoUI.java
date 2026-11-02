package es.patrick.ui;

import es.patrick.domain.error.AppError;
import es.patrick.domain.error.DatabaseError;
import es.patrick.domain.services.MedicoService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MedicoUI {
    private final MedicoService medicoService;

    @Inject
    public MedicoUI(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    public void getAll() {
        try {
            IO.println(medicoService.getAll());
        } catch (DatabaseError e) {
            log.error("Error de BD {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado", e);
            throw new AppError("Error crítico");
        }
    }
}
