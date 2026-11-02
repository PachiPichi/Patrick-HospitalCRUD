package es.patrick.domain.services;

import es.patrick.dao.repositories.MedicoRepository;
import es.patrick.domain.dto.MedicoDTO;
import es.patrick.domain.mappers.MedicoDTOMapper;
import jakarta.inject.Inject;

import java.util.List;

public class MedicoService {
    private final MedicoRepository medicoRepository;
    private final MedicoDTOMapper medicoDTOMapper;

    @Inject
    public MedicoService(MedicoRepository medicoRepository, MedicoDTOMapper medicoDTOMapper) {
        this.medicoRepository = medicoRepository;
        this.medicoDTOMapper = medicoDTOMapper;
    }

    public List<MedicoDTO> getAll() {
        return medicoDTOMapper.toDTOList(medicoRepository.findAll());
    }
}
