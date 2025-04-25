
package com.mycompany.reportegrafico.datos;

/**
 *
 * @author User name
 */
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;  

/**  
 * Clase para manejar la conexión a la base de datos.  
 * @author LEURY BRAND  
 */  
public class Conexion {  
    
    // URL de la base de datos  
    private static final String URL = "jdbc:mysql://localhost:3306/dairytest"; // Cambia "tu_base_de_datos" por el nombre de tu base de datos  
    private static final String USER = "root"; // Cambia "tu_usuario" por tu nombre de usuario  
    private static final String PASSWORD = "19082004"; // Cambia "tu_contraseña" por tu contraseña  

    /**  
     * Método para establecer una conexión a la base de datos.  
     * @return Connection objeto de conexión a la base de datos  
     */  
    public Connection conectar() {  
        Connection conn = null;  

        try {  
            // Cargar el driver de MySQL  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            
            // Establecer la conexión  
            conn = DriverManager.getConnection(URL, USER, PASSWORD);  
            System.out.println("Conexión establecida correctamente.");  
        } catch (SQLException e) {  
            System.err.println("Error de SQL al conectar: " + e.getMessage());  
        } catch (ClassNotFoundException e) {  
            System.err.println("Driver no encontrado: " + e.getMessage());  
        } catch (Exception e) {  
            System.err.println("Error desconocido: " + e.getMessage());  
        }  

        return conn; // Devolvemos la conexión (será null si hubo un error)  
    }  
}  