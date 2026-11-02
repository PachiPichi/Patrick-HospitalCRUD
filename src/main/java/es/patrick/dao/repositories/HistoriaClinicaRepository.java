package es.patrick.dao.repositories;

import es.patrick.dao.model.HistoriaClinica;

import java.util.List;

public interface HistoriaClinicaRepository {
    List<HistoriaClinica> findAllByPacienteId(Long pacienteId);
    HistoriaClinica findById(Long id);
    Long add(HistoriaClinica historiaClinica);
    boolean update(HistoriaClinica historiaClinica);
    boolean delete(Long id);
}
