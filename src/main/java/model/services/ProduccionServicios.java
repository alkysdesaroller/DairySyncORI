
package model.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.ProduccionDiaria;
import java.sql.SQLException;
import model.repositorio.implement.ProduccionRepo;
import utils.DatabaseConnection;

/**
 *
 * @author alna7
 */
    

public class ProduccionServicios {
    private final ProduccionRepo produccionRepo;

    public ProduccionServicios() {
        this.produccionRepo = new ProduccionRepo();
    }

    public void registrarProduccion(ProduccionDiaria produccion) {
        String sqlInsert = "INSERT INTO produccion (fecha, litros, VacaId, grajaId) VALUES (?, ?, ?, ?)";
        produccionRepo.guardar(produccion, sqlInsert);
    }

    public float obtenerPromedioProduccion(int vacaId) {
        String sql = "SELECT AVG(litros) AS promedio FROM produccion WHERE VacaId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vacaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getFloat("promedio");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}