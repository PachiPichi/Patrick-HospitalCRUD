package es.patrick.dao.repositories.jdbc;

import es.patrick.dao.common.SQLQueries;
import es.patrick.dao.mappers.MedicoRowMapper;
import es.patrick.dao.model.Medico;
import es.patrick.dao.repositories.MedicoRepository;
import es.patrick.dao.utils.DBConnection;
import es.patrick.domain.error.DatabaseError;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JDBCMedicoRepository implements MedicoRepository {
    private final DBConnection dbConnection;
    private final MedicoRowMapper medicoRowMapper;

    @Inject
    public JDBCMedicoRepository(DBConnection dbConnection, MedicoRowMapper medicoRowMapper) {
        this.dbConnection = dbConnection;
        this.medicoRowMapper = medicoRowMapper;
    }

    @Override
    public List<Medico> findAll() {
        List<Medico> medicos = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.FIND_ALL_MEDICOS);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                medicos.add(medicoRowMapper.mapRow(rs, rs.getRow()));
            }
        } catch (SQLException e) {
            log.error("Se ha producido un error en la BD al listar médicos", e);
            throw new DatabaseError("Ocurrió un error en la base de datos.");
        }
        return medicos;
    }

    @Override
    public boolean existsById(Long id) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.EXISTS_MEDICO_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            log.error("Se ha producido un error en la BD al validar el médico", e);
            throw new DatabaseError("Ocurrió un error en la base de datos.");
        }
    }
}
