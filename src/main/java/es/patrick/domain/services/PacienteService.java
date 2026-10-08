package es.patrick.domain.services;

import es.patrick.dao.repositories.PacienteRepository;
import es.patrick.domain.dto.PacienteDTO;
import es.patrick.domain.mappers.PacienteDTOMapper;
import jakarta.inject.Inject;

import java.util.List;

public class PacienteService {
    private final PacienteRepository pacienteRepository;
    private final PacienteDTOMapper pacienteDTOMapper;

    @Inject
    public PacienteService (PacienteRepository pacienteRepository, PacienteDTOMapper pacienteDTOMapper) {
        this.pacienteRepository =  pacienteRepository;
        this.pacienteDTOMapper = pacienteDTOMapper;
    }

    public List<PacienteDTO> getAll() {
        return pacienteDTOMapper.toDTOList(pacienteRepository.findAll());
    }
}