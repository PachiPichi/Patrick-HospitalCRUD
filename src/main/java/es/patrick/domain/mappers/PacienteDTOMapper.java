package es.patrick.domain.mappers;

import es.patrick.dao.model.Paciente;
import es.patrick.dao.model.Usuario;
import es.patrick.domain.dto.PacienteDTO;
import es.patrick.domain.dto.PacienteDTOAlta;

import java.util.ArrayList;
import java.util.List;

public class PacienteDTOMapper {
    public PacienteDTO toDTO(Paciente paciente) {
        return PacienteDTO.builder()
                .id(paciente.getId())
                .nombre(paciente.getNombre())
                .fechaNacimiento(paciente.getFechaNacimiento())
                .telefono(paciente.getTelefono())
                .build();
    }

    public Paciente toEntity(PacienteDTO pacienteDTO) {
        return Paciente.builder()
                .id(pacienteDTO.getId())
                .nombre(pacienteDTO.getNombre())
                .fechaNacimiento(pacienteDTO.getFechaNacimiento())
                .telefono(pacienteDTO.getTelefono())
                .build();
    }

    public Paciente toEntity(PacienteDTOAlta pacienteDTO) {
        return Paciente.builder()
                .nombre(pacienteDTO.getNombre())
                .fechaNacimiento(pacienteDTO.getFechaNacimiento())
                .telefono(pacienteDTO.getTelefono())
                .usuario(Usuario.builder()
                        .username(pacienteDTO.getUsername())
                        .password(pacienteDTO.getPassword())
                        .build())
                .build();
    }

    public List<PacienteDTO> toDTOList(List<Paciente> pacientes) {
        List<PacienteDTO> listaDtos = new ArrayList<>();
        pacientes.forEach(paciente -> listaDtos.add(toDTO(paciente)));
        return listaDtos;
    }
}