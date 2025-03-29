/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.repositorio.implement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Granja;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.repositorio.IbaseRepo;
import utils.DatabaseConnection;
/**
 *
 * @author alna7
 */
public class GranjaRepo extends IbaseRepo<Granja> {

    public GranjaRepo() { 
        super("Granja", new String[]{"id", "direccion", "cantidadAnminal", "granjeroId"}, new Mapper<Granja>() {
            @Override
            
            public Granja map(ResultSet rs) throws SQLException {
                Granja granja = new Granja();
                
                granja.setId(rs.getInt("id"));
                granja.setDireccion(rs.getString("direccion"));
                granja.setcantidadAnimal(rs.getString("cantidadAnminal"));
                granja.setGranjeroId(rs.getInt("granjeroId"));
                return granja;
            }
        });
    }
    
    public List<Granja> obtenerGranjasporGranjero(int granjeroId) throws SQLException{
        String sql ="SELECT * FROM Granja WHERE granjeroId = ?"; 
        List<Granja> granjas = new ArrayList<>();
        try(Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, granjeroId);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                Granja granja = new Granja(); 
                granja.setId(rs.getInt("Id"));
                granja.setDireccion(rs.getString("direccion")); 
                granja.setcantidadAnimal(rs.getString("cantidadAnminal"));
                granja.setGranjeroId(rs.getInt("granjeroId"));
                granjas.add(granja);
           }
    } catch (SQLException e) {
        e.printStackTrace();
        }
    return granjas;
    }
    
    public void actualizarCantidadAnimales(int granjaId, String nuevaCantidad){
    String sql = "UPDATE Granja SET cantidadAnimal = ? WHERE Id = ?"; 
       try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
       stmt.setString(1, nuevaCantidad);
       stmt.setInt(2, granjaId); 
       stmt.executeUpdate();
       }catch(SQLException e){
       e.printStackTrace();
       }
    }
}
  
    