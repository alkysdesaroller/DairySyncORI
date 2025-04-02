import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.DatabaseConnection;

public class PruebasConexion {
    @Test
    void testConexionBD() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            assertFalse(conn.isClosed(), "La conexión debería estar abierta");
            System.out.println("Conexión a BD exitosa");
        } catch (SQLException e) {
            fail("Error al conectar a BD: " + e.getMessage());
        }
    }
}