package es.patrick.dao.repositories.jdbc;

import es.patrick.common.Constantes;
import es.patrick.dao.common.SQLQueries;
import es.patrick.dao.mappers.HistoriaClinicaRowMapper;
import es.patrick.dao.model.HistoriaClinica;
import es.patrick.dao.repositories.HistoriaClinicaRepository;
import es.patrick.dao.utils.DBConnection;
import es.patrick.domain.error.DatabaseError;
import es.patrick.domain.error.ReferenciaHistoriaInvalidaError;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JDBCHistoriaClinicaRepository implements HistoriaClinicaRepository {
    private final DBConnection dbConnection;
    private final HistoriaClinicaRowMapper historiaClinicaRowMapper;

    @Inject
    public JDBCHistoriaClinicaRepository(DBConnection dbConnection, HistoriaClinicaRowMapper historiaClinicaRowMapper) {
        this.dbConnection = dbConnection;
        this.historiaClinicaRowMapper = historiaClinicaRowMapper;
    }

    @Override
    public List<HistoriaClinica> findAllByPacienteId(Long pacienteId) {
        List<HistoriaClinica> historias = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.FIND_ALL_HISTORIAS_BY_PACIENTE)) {
            preparedStatement.setLong(1, pacienteId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    historias.add(historiaClinicaRowMapper.mapRow(rs, rs.getRow()));
                }
            }
        } catch (SQLException e) {
            log.error("Se ha producido un error en la BD al listar la historia del paciente", e);
            throw new DatabaseError(Constantes.DATABASE_ERROR);
        }
        return historias;
    }

    @Override
    public HistoriaClinica findById(Long id) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.FIND_HISTORIA_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return historiaClinicaRowMapper.mapRow(rs, rs.getRow());
                }
                return null;
            }
        } catch (SQLException e) {
            log.error("Se ha producido un error en la BD al buscar la historia por id", e);
            throw new DatabaseError(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public Long add(HistoriaClinica historiaClinica) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.ADD_HISTORIA_CLINICA, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, historiaClinica.getPacienteId());
            preparedStatement.setLong(2, historiaClinica.getMedicoId());
            preparedStatement.setString(3, historiaClinica.getDiagnostico());
            preparedStatement.setDate(4, Date.valueOf(historiaClinica.getFechaAdmision()));

            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getLong(1);
                    }
                }
            }
            return null;
        } catch (SQLIntegrityConstraintViolationException e) {
            log.error("Referencia inválida al añadir registro de historia clínica", e);
            throw new ReferenciaHistoriaInvalidaError(
                    "No se pudo guardar el registro: el paciente o el médico indicado no existe.");
        } catch (SQLException e) {
            log.error("Se ha producido un error en la BD al añadir la historia", e);
            throw new DatabaseError(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public boolean update(HistoriaClinica historiaClinica) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.UPDATE_HISTORIA_CLINICA)) {
            preparedStatement.setLong(1, historiaClinica.getPacienteId());
            preparedStatement.setLong(2, historiaClinica.getMedicoId());
            preparedStatement.setString(3, historiaClinica.getDiagnostico());
            preparedStatement.setDate(4, Date.valueOf(historiaClinica.getFechaAdmision()));
            preparedStatement.setLong(5, historiaClinica.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            log.error("Referencia inválida al actualizar registro de historia clínica", e);
            throw new ReferenciaHistoriaInvalidaError(
                    "No se pudo actualizar el registro: el paciente o el médico indicado no existe.");
        } catch (SQLException e) {
            log.error("Se ha producido un error en la BD al actualizar la historia", e);
            throw new DatabaseError(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.DELETE_HISTORIA_CLINICA)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Se ha producido un error en la BD al borrar la historia", e);
            throw new DatabaseError(Constantes.DATABASE_ERROR);
        }
    }
}
