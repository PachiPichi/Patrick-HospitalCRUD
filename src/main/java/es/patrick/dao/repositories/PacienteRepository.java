package es.patrick.dao.repositories;

import es.patrick.dao.model.Paciente;

import java.util.List;

public interface PacienteRepository {
    List<Paciente> findAll();
}