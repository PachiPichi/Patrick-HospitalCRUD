package es.patrick.domain.mappers;

import es.patrick.dao.model.Medico;
import es.patrick.domain.dto.MedicoDTO;

import java.util.ArrayList;
import java.util.List;

public class MedicoDTOMapper {
    public MedicoDTO toDTO(Medico medico) {
        return MedicoDTO.builder()
                .id(medico.getId())
                .nombre(medico.getNombre())
                .especialidad(medico.getEspecialidad())
                .telefono(medico.getTelefono())
                .build();
    }

    public List<MedicoDTO> toDTOList(List<Medico> medicos) {
        List<MedicoDTO> listaDtos = new ArrayList<>();
        medicos.forEach(medico -> listaDtos.add(toDTO(medico)));
        return listaDtos;
    }
}
