package es.patrick.dao.mappers;

import es.patrick.dao.model.Paciente;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PacienteRowMapper {
    public Paciente mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Paciente.builder()
                .id(rs.getLong("paciente_id"))
                .nombre(rs.getString("nombre"))
                .fechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate())
                .telefono(rs.getString("telefono"))
                .build();
    }
}