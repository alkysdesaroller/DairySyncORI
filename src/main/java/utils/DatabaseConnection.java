package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author darie
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/dairysync";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "1437";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
