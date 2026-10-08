package es.patrick.domain.mappers;

import es.patrick.dao.model.Paciente;
import es.patrick.domain.dto.PacienteDTO;

import java.util.ArrayList;
import java.util.List;

public class PacienteDTOMapper
{
    public PacienteDTO toDTO(Paciente paciente) {
        return PacienteDTO.builder()
                .id(paciente.getId())
                .nombre(paciente.getNombre())
                .fechaNacimiento(paciente.getFechaNacimiento())
                .telefono(paciente.getTelefono())
                .build();
    }

    public Paciente toEntity(PacienteDTO pacienteDTO) {
        // Completar
        return Paciente.builder()
                .build();
    }

    public List<PacienteDTO> toDTOList(List<Paciente> pacientes) {

        List<PacienteDTO> listaDtos = new ArrayList<>();
        for (Paciente paciente : pacientes)  {
            listaDtos.add(toDTO(paciente));
        }
        return listaDtos;
    }

}