package es.patrick.dao.repositories;

import es.patrick.dao.model.Medico;

import java.util.List;

public interface MedicoRepository {
    List<Medico> findAll();
    boolean existsById(Long id);
}
