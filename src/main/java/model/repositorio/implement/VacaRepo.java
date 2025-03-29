
package model.repositorio.implement;
import model.repositorio.IbaseRepo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Vaca;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ProduccionDiaria;
import utils.DatabaseConnection;

/**
 *
 * @author alna7
 */
public class VacaRepo extends IbaseRepo<Vaca> {
    
   public VacaRepo() {
       
        super("Vaca", new String[]{"id", "raza", "edad", "granjaId"}, new Mapper<Vaca>() {
            @Override
            public Vaca map(ResultSet rs) throws SQLException {
                // Mapeamos los datos del ResultSet a una instancia de Vaca
                int id = rs.getInt("id");
                String raza = rs.getString("raza");
                int edad = rs.getInt("edad");
                int granjaId = rs.getInt("granjaId");

                return new Vaca(id, raza, edad, granjaId);
            }
        });
    }

   
   public void registrarProduccion(ProduccionDiaria produccion){
      //validamos que la produccion no sea nula, para evitar posibles conflicto con el metodo getTime.
       if(produccion.getFecha() == null){
       throw new IllegalArgumentException("La fecha de produccion no puede ser nula. "); 
       }
       //Validamos que los IDs no sean nulos
          if (produccion.getVacaId() <= 0){
       throw new IllegalArgumentException("El ID de la vaca no puede ser nulo o menor o igual a cero."); 
       }
               if (produccion.getGranjaId() <= 0){
       throw new IllegalArgumentException("El ID de la granja no puede ser nulo o menor o igual a cero."); 
       }
         //Consulta para insertar la produccion en la base de datos 
       String sqlInsert = "INSERT  INTO produccion (fecha, litros, vacaId, GranjaId) VALUES (?, ?, ?, ?) ";
       try(Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sqlInsert)){
           //Asignamos los valores a los parametros 
        stmt.setDate(1, java.sql.Date.valueOf(produccion.getFecha()));
        stmt.setFloat(2, (float) produccion.getLitros()); // Litros
        stmt.setInt(3, produccion.getVacaId()); // ID de la vaca
        stmt.setInt(4, produccion.getGranjaId()); // ID de la granja
           stmt.executeUpdate(); 
       } catch (SQLException e) {
    System.err.println("Error al registrar la producción: " + e.getMessage());
    e.printStackTrace();
       }
   };
   
public List<Vaca> obtenerVacasPorGranja(int granjaId) {
    String sql = "SELECT * FROM Vaca WHERE granjaId = ?";
    List<Vaca> vacas = new ArrayList<>(); // Lista para almacenar las vacas
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, granjaId); // Establecer el parámetro de la consulta
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Vaca vaca = new Vaca(); // Usar el constructor vacío
            vaca.setId(rs.getInt("id"));
            vaca.setRaza(rs.getString("raza"));
            vaca.setEdad(rs.getInt("edad"));
            vaca.setMadreId(rs.getObject("madreId", Integer.class)); // Manejar valores nulos
            vaca.setPadreId(rs.getObject("padreId", Integer.class)); // Manejar valores nulos
            vaca.setGranjaId(rs.getInt("granjaId"));
            vacas.add(vaca); // Agregar la vaca a la lista
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return vacas; // Devolver la lista de vacas
    }
}