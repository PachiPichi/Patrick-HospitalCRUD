package es.patrick.domain.services;

import es.patrick.dao.model.HistoriaClinica;
import es.patrick.dao.repositories.HistoriaClinicaRepository;
import es.patrick.dao.repositories.MedicoRepository;
import es.patrick.dao.repositories.PacienteRepository;
import es.patrick.domain.dto.HistoriaClinicaDTO;
import es.patrick.domain.error.ReferenciaHistoriaInvalidaError;
import es.patrick.domain.mappers.HistoriaClinicaDTOMapper;
import jakarta.inject.Inject;

import java.util.List;

public class HistoriaClinicaService {
    private final HistoriaClinicaRepository historiaClinicaRepository;
    private final HistoriaClinicaDTOMapper historiaClinicaDTOMapper;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    @Inject
    public HistoriaClinicaService(
            HistoriaClinicaRepository historiaClinicaRepository,
            HistoriaClinicaDTOMapper historiaClinicaDTOMapper,
            PacienteRepository pacienteRepository,
            MedicoRepository medicoRepository) {
        this.historiaClinicaRepository = historiaClinicaRepository;
        this.historiaClinicaDTOMapper = historiaClinicaDTOMapper;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public List<HistoriaClinicaDTO> getAllByPacienteId(Long pacienteId) {
        return historiaClinicaDTOMapper.toDTOList(historiaClinicaRepository.findAllByPacienteId(pacienteId));
    }

    public HistoriaClinicaDTO findById(Long id) {
        HistoriaClinica historiaClinica = historiaClinicaRepository.findById(id);
        return historiaClinica == null ? null : historiaClinicaDTOMapper.toDTO(historiaClinica);
    }

    public Long add(HistoriaClinicaDTO historiaClinicaDTO) {
        validateReferences(historiaClinicaDTO);
        return historiaClinicaRepository.add(historiaClinicaDTOMapper.toEntity(historiaClinicaDTO));
    }

    public boolean update(Long id, HistoriaClinicaDTO historiaClinicaDTO) {
        validateReferences(historiaClinicaDTO);
        HistoriaClinica historiaActual = historiaClinicaRepository.findById(id);
        if (historiaActual == null) {
            return false;
        }

        historiaActual.setPacienteId(historiaClinicaDTO.getPacienteId());
        historiaActual.setMedicoId(historiaClinicaDTO.getMedicoId());
        historiaActual.setDiagnostico(historiaClinicaDTO.getDiagnostico());
        historiaActual.setFechaAdmision(historiaClinicaDTO.getFechaAdmision());
        return historiaClinicaRepository.update(historiaActual);
    }

    public boolean delete(Long id) {
        return historiaClinicaRepository.delete(id);
    }

    private void validateReferences(HistoriaClinicaDTO historiaClinicaDTO) {
        Long pacienteId = historiaClinicaDTO.getPacienteId();
        if (pacienteId == null || pacienteRepository.findById(pacienteId) == null) {
            throw new ReferenciaHistoriaInvalidaError("No existe un paciente con id " + pacienteId + ".");
        }

        Long medicoId = historiaClinicaDTO.getMedicoId();
        if (medicoId == null || !medicoRepository.existsById(medicoId)) {
            throw new ReferenciaHistoriaInvalidaError("No existe un médico con id " + medicoId + ".");
        }
    }
}
