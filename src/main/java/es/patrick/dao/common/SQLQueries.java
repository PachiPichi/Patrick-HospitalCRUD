package es.patrick.dao.common;

public class SQLQueries {
    public static final String FIND_USUARIO_BY_USERNAME = "SELECT * FROM usuarios WHERE username = ?";
    public static final String FIND_ALL_PACIENTES = "SELECT * FROM pacientes";
    public static final String FIND_ALL_MEDICOS = "SELECT * FROM medicos";
    public static final String FIND_PACIENTE_BY_ID = "SELECT * FROM pacientes WHERE paciente_id = ?";
    public static final String FIND_ALL_HISTORIAS_BY_PACIENTE = "SELECT * FROM historia_clinica WHERE paciente_id = ?";
    public static final String FIND_HISTORIA_BY_ID = "SELECT * FROM historia_clinica WHERE historia_id = ?";
    public static final String EXISTS_MEDICO_BY_ID = "SELECT 1 FROM medicos WHERE medico_id = ?";
    public static final String ADD_PACIENTE = "INSERT INTO pacientes (nombre, fecha_nacimiento, telefono) VALUES (?, ?, ?)";
    public static final String ADD_USUARIO = "INSERT INTO usuarios (username, password, paciente_id) VALUES (?, ?, ?)";
    public static final String ADD_HISTORIA_CLINICA = "INSERT INTO historia_clinica (paciente_id, medico_id, diagnostico, fecha_admision) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_PACIENTE = "UPDATE pacientes SET nombre = ?, fecha_nacimiento = ?, telefono = ? WHERE paciente_id = ?";
    public static final String UPDATE_HISTORIA_CLINICA = "UPDATE historia_clinica SET paciente_id = ?, medico_id = ?, diagnostico = ?, fecha_admision = ? WHERE historia_id = ?";
    public static final String DELETE_USUARIO = "DELETE FROM usuarios WHERE paciente_id = ?";
    public static final String DELETE_PACIENTE = "DELETE FROM pacientes WHERE paciente_id = ?";
    public static final String DELETE_HISTORIA_CLINICA = "DELETE FROM historia_clinica WHERE historia_id = ?";
}
