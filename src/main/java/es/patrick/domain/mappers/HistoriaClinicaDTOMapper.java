package es.patrick.domain.mappers;

import es.patrick.dao.model.HistoriaClinica;
import es.patrick.domain.dto.HistoriaClinicaDTO;

import java.util.ArrayList;
import java.util.List;

public class HistoriaClinicaDTOMapper {
    public HistoriaClinicaDTO toDTO(HistoriaClinica historiaClinica) {
        return HistoriaClinicaDTO.builder()
                .id(historiaClinica.getId())
                .pacienteId(historiaClinica.getPacienteId())
                .medicoId(historiaClinica.getMedicoId())
                .diagnostico(historiaClinica.getDiagnostico())
                .fechaAdmision(historiaClinica.getFechaAdmision())
                .build();
    }

    public HistoriaClinica toEntity(HistoriaClinicaDTO historiaClinicaDTO) {
        return HistoriaClinica.builder()
                .id(historiaClinicaDTO.getId())
                .pacienteId(historiaClinicaDTO.getPacienteId())
                .medicoId(historiaClinicaDTO.getMedicoId())
                .diagnostico(historiaClinicaDTO.getDiagnostico())
                .fechaAdmision(historiaClinicaDTO.getFechaAdmision())
                .build();
    }

    public List<HistoriaClinicaDTO> toDTOList(List<HistoriaClinica> historias) {
        List<HistoriaClinicaDTO> result = new ArrayList<>();
        historias.forEach(historia -> result.add(toDTO(historia)));
        return result;
    }
}
