
package model.repositorio.implement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import model.ProduccionDiaria;
import model.repositorio.IbaseRepo;
import utils.DatabaseConnection;
/**
 *
 * @author alna7
 */
public class ProduccionRepo extends IbaseRepo<ProduccionDiaria> {

    public ProduccionRepo() {
        super("produccion", new String[]{"id", "fecha", "litros", "VacaId", "grajaId"}, new Mapper<ProduccionDiaria>() {
            
            @Override
            public ProduccionDiaria map(ResultSet rs) throws SQLException {
                
                ProduccionDiaria produccion = new ProduccionDiaria();
                produccion.setId(rs.getInt("id"));
                produccion.setFecha(LocalDate.EPOCH);
                produccion.setLitros(rs.getFloat("litros"));
                produccion.setVacaId(rs.getInt("VacaId"));
                produccion.setGranjaId(rs.getInt("grajaId"));
                return produccion;
            }
        });
    }
    public void registrarProduccion(ProduccionDiaria produccion) {
    String sqlInsert = "INSERT INTO produccion (fecha, litros, VacaId, grajaId) VALUES (?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {
        stmt.setDate(1, java.sql.Date.valueOf(produccion.getFecha()));
        stmt.setFloat(2, (float) produccion.getLitros());
        stmt.setInt(3, produccion.getVacaId());
        stmt.setInt(4, produccion.getGranjaId());
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public float obtenerPromedioProduccion(int VacaId){
    String sql = "SELECT AVG(litros) AS promedio FROM produccion WHERE VacaId = ?";
    try(Connection conn = DatabaseConnection.getConnection(); 
          PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, VacaId);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
           return rs.getFloat("Promedio");
            }
        }catch(SQLException e){
        e.printStackTrace();
        }
    return 0;// esto en caso de que no alla ningun promedio  
    }
    
    public ProduccionDiaria obtenerPicoProduccion(int VacaId){
    String sql = "SELECT * FROM produccion WHERE VacaId = ? ORDER BY litros DESC LIMIT 1  ";
    try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
        stmt.setInt(1, VacaId);
        ResultSet rs = stmt.executeQuery(); 
        if(rs.next()){
        ProduccionDiaria produccion = new ProduccionDiaria();
        produccion.setId(rs.getInt("Id"));
        produccion.setFecha(LocalDate.EPOCH);
        produccion.setLitros(rs.getDouble("litros"));
        produccion.setVacaId(rs.getInt("VacaId"));
        produccion.setGranjaId(rs.getInt("granjaId"));
        return produccion; 
        }
        }catch(SQLException e){
        e.printStackTrace();
        }
    return null;
     }
}
