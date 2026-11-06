package es.patrick.domain.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import es.patrick.dao.model.backup.PacienteBackup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BackupService {

    private static final Path BACKUP_DIRECTORY =
            Path.of("backups", "pacientes");

    private final XmlMapper xmlMapper;

    public BackupService() {
        this.xmlMapper = XmlMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    public void save(PacienteBackup pacienteBackup) {
        try {
            Files.createDirectories(BACKUP_DIRECTORY);

            Path backupFile = BACKUP_DIRECTORY.resolve(
                    "paciente-" + pacienteBackup.getId() + ".xml"
            );

            xmlMapper.writeValue(backupFile.toFile(), pacienteBackup);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "No se pudo guardar el backup del paciente.",
                    e
            );
        }
    }

    public PacienteBackup load(Long pacienteId) {
        Path backupFile = BACKUP_DIRECTORY.resolve(
                "paciente-" + pacienteId + ".xml"
        );

        if (Files.notExists(backupFile)) {
            return null;
        }

        try {
            return xmlMapper.readValue(
                    backupFile.toFile(),
                    PacienteBackup.class
            );

        } catch (IOException e) {
            throw new IllegalStateException(
                    "No se pudo leer el backup del paciente.",
                    e
            );
        }
    }
}