package es.patrick.dao.mappers;

import es.patrick.dao.model.HistoriaClinica;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoriaClinicaRowMapper {
    public HistoriaClinica mapRow(ResultSet rs, int rowNum) throws SQLException {
        return HistoriaClinica.builder()
                .id(rs.getLong("historia_id"))
                .pacienteId(rs.getLong("paciente_id"))
                .medicoId(rs.getLong("medico_id"))
                .diagnostico(rs.getString("diagnostico"))
                .fechaAdmision(rs.getDate("fecha_admision").toLocalDate())
                .build();
    }
}
