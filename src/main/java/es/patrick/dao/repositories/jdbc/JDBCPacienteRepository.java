package es.patrick.dao.repositories.jdbc;

import es.patrick.common.Constantes;
import es.patrick.dao.common.SQLQueries;
import es.patrick.dao.mappers.PacienteRowMapper;
import es.patrick.dao.model.Paciente;
import es.patrick.dao.repositories.PacienteRepository;
import es.patrick.dao.utils.DBConnection;
import es.patrick.domain.error.DatabaseError;
import es.patrick.domain.error.PacienteDuplicadoError;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JDBCPacienteRepository implements PacienteRepository {

    private final DBConnection dbConnection;
    private final PacienteRowMapper pacienteRowMapper;

    @Inject
    public JDBCPacienteRepository(DBConnection dbConnection, PacienteRowMapper pacienteRowMapper) {
        this.dbConnection = dbConnection;
        this.pacienteRowMapper = pacienteRowMapper;
    }

    @Override
    public List<Paciente> findAll() {
        List<Paciente> pacientes = new ArrayList<>();
        try (Connection connection= dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.FIND_ALL_PACIENTES);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                Paciente paciente = pacienteRowMapper.mapRow(rs, rs.getRow());
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            log.error("Se ha producido un error en la BD");
            throw new DatabaseError(e.getMessage());
        }
        return pacientes;
    }

    @Override
    public Paciente findById(Long id) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.FIND_PACIENTE_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return pacienteRowMapper.mapRow(rs, rs.getRow());
                }
                return null;
            }
        } catch (SQLException e) {
            log.error("Se ha producido un error en la BD al buscar paciente por id", e);
            throw new DatabaseError(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public Long add(Paciente paciente) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmtPaciente = conn.prepareStatement(SQLQueries.ADD_PACIENTE, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmtUsuario = conn.prepareStatement(SQLQueries.ADD_USUARIO)) {
            try {
                conn.setAutoCommit(false);
                pstmtPaciente.setString(1, paciente.getNombre());
                pstmtPaciente.setDate(2, Date.valueOf(paciente.getFechaNacimiento()));
                pstmtPaciente.setString(3, paciente.getTelefono());

                int filasAfectadas = pstmtPaciente.executeUpdate();

                if (filasAfectadas > 0) {
                    ResultSet idsGenerados = pstmtPaciente.getGeneratedKeys();
                    if (idsGenerados.next()) {
                        paciente.setId(idsGenerados.getLong(1));
                        pstmtUsuario.setString(1, paciente.getUsuario().getUsername());
                        pstmtUsuario.setString(2, paciente.getUsuario().getPassword());
                        pstmtUsuario.setLong(3, paciente.getId());
                        pstmtUsuario.executeUpdate();
                        conn.commit();
                    }
                }
                return paciente.getId();
            } catch (SQLIntegrityConstraintViolationException e) {
                conn.rollback();
                log.error(e.getMessage());
                throw new PacienteDuplicadoError();
            } catch (SQLException e) {
                conn.rollback();
                log.error(e.getMessage());
                throw new DatabaseError(Constantes.DATABASE_ERROR);
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseError(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public boolean update(Paciente paciente) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.UPDATE_PACIENTE)) {
            preparedStatement.setString(1, paciente.getNombre());
            preparedStatement.setDate(2, Date.valueOf(paciente.getFechaNacimiento()));
            preparedStatement.setString(3, paciente.getTelefono());
            preparedStatement.setLong(4, paciente.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Se ha producido un error en la BD al actualizar paciente", e);
            throw new DatabaseError(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = dbConnection.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement deleteUsuario = connection.prepareStatement(SQLQueries.DELETE_USUARIO);
                 PreparedStatement deletePaciente = connection.prepareStatement(SQLQueries.DELETE_PACIENTE)) {
                deleteUsuario.setLong(1, id);
                deleteUsuario.executeUpdate();
                deletePaciente.setLong(1, id);
                int affectedRows = deletePaciente.executeUpdate();
                connection.commit();
                return affectedRows > 0;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            log.error("Se ha producido un error en la BD al eliminar paciente", e);
            throw new DatabaseError(Constantes.DATABASE_ERROR);
        }
    }
}