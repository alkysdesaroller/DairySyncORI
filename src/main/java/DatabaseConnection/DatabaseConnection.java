
package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author alna7
 */
public class DatabaseConnection {
       //guardamos el url para utilizarlo luego en la ejecucion 
    private static final String url =  "jdbc:mysql://127.0.0.1:3306/?user=root";
    private static final String user = "root";
    private static final String password = "1234567";
     public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
 
     //instanciamos el metodo conectar
     public static Connection conectar(){
     Connection conn = null; 
     try{
        Class.forName("com.mysql.cj.jdbc.Driver");// Cargamos el driver 
        conn = DriverManager.getConnection(url, user, password);
        System.out.println("Conexion exitosa a MySQL!!!");  
        }
        //Excepcion en caso de que no haya conexion
        catch(ClassNotFoundException e){
        System.out.println("Error: Driver no encontrado");
        e.printStackTrace();
        }catch(SQLException e){
        System.err.println("Error de conexion a Mysql: " + e.getMessage());
        } 
        return conn;//retornamos la conexion independiente del resueltado.
     }
     
     public static void main(String[] args){
     Connection conn = conectar();
     if(conn != null){
        System.out.println("Base de datos conectada existosamente");
        }
     }
}


