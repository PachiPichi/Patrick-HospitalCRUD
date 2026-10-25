package es.patrick.dao.repositories;

import es.patrick.dao.model.Paciente;

import java.util.List;

public interface PacienteRepository {
    List<Paciente> findAll();
    Paciente findById(Long id);
    Long add(Paciente paciente);
    boolean update(Paciente paciente);
    boolean delete(Long id);
}