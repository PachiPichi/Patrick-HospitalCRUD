package es.patrick.domain.services;

import es.patrick.dao.model.Paciente;
import es.patrick.dao.repositories.PacienteRepository;
import es.patrick.domain.dto.PacienteDTO;
import es.patrick.domain.dto.PacienteDTOAlta;
import es.patrick.domain.mappers.PacienteDTOMapper;
import jakarta.inject.Inject;

import java.util.List;

public class PacienteService {
    private final PacienteRepository pacienteRepository;
    private final PacienteDTOMapper pacienteDTOMapper;

    @Inject
    public PacienteService(PacienteRepository pacienteRepository, PacienteDTOMapper pacienteDTOMapper) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteDTOMapper = pacienteDTOMapper;
    }

    public List<PacienteDTO> getAll() {
        return pacienteDTOMapper.toDTOList(pacienteRepository.findAll());
    }

    public PacienteDTO findById(Long id) {
        Paciente paciente = pacienteRepository.findById(id);
        return paciente == null ? null : pacienteDTOMapper.toDTO(paciente);
    }

    public Long add(PacienteDTOAlta pacienteDTO) {
        return pacienteRepository.add(pacienteDTOMapper.toEntity(pacienteDTO));
    }

    public boolean update(Long id, PacienteDTO pacienteDTO) {
        Paciente pacienteActual = pacienteRepository.findById(id);
        if (pacienteActual == null) {
            return false;
        }

        pacienteActual.setNombre(pacienteDTO.getNombre());
        pacienteActual.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
        pacienteActual.setTelefono(pacienteDTO.getTelefono());
        return pacienteRepository.update(pacienteActual);
    }

    public boolean delete(Long id) {
        return pacienteRepository.delete(id);
    }
}