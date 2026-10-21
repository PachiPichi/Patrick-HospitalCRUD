package es.patrick.dao.repositories.jdbc;

import es.patrick.dao.common.SQLQueries;
import es.patrick.dao.mappers.PacienteRowMapper;
import es.patrick.dao.model.Paciente;
import es.patrick.dao.repositories.PacienteRepository;
import es.patrick.dao.utils.DBConnection;
import es.patrick.domain.error.DatabaseError;
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
    public Long add(Paciente paciente) {
        try (Connection conn = dbConnection.getConnection();
        PreparedStatement pstmtPaciente = conn.prepareStatement(SQLQueries.ADD_PACIENTE, Statement.RETURN_GENERATED_KEYS);
        PreparedStatement pstmtUsuario = conn.prepareStatement(SQLQueries.ADD_USUARIO)){
            try {
                conn.setAutoCommit(false);
                pstmtPaciente.setString(1, paciente.getNombre());
                pstmtPaciente.setDate(2, Date.valueOf(paciente.getFechaNacimiento()));
                pstmtPaciente.setString(3, paciente.getTelefono());

                int filasAfectadas = pstmtPaciente.executeUpdate();

                if (filasAfectadas > 0) {
                    ResultSet idsGenerados = pstmtPaciente.getGeneratedKeys();
                    if (idsGenerados.next()){
                        paciente.setId(idsGenerados.getLong(1));
                        pstmtUsuario.Set
                    }
                }
            }

        }
    }
}