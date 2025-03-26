/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.repositorio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseConnection;
/**
 *
 * @author alna7
 */
public class IbaseRepo<T> {
    private final String tabla;
    private final String[ ] columnas; 
    private final Mapper<T> mapper;
    
        public interface Mapper<T> {
        T map(ResultSet rs)  throws SQLException;  
        }
        
        public IbaseRepo(String tabla, String[ ] columnas, Mapper<T> mapper)  {
        this.tabla = tabla;
        this.columnas = columnas; 
        this.mapper = mapper;
       }       
        
        
    // Método para guardar un objeto en la base de datos
    public void guardar(T entidad, String sqlInsert) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {
            // Ejecutar la consulta
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
      // Método para obtener un objeto por su ID
    public T obtenerPorId(int id) {
        String sql = "SELECT * FROM " + tabla + " WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapper.map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Método para obtener todos los objetos de la tabla
    public List<T> obtenerTodos() {
        List<T> entidades = new ArrayList<>();
        String sql = "SELECT * FROM " + tabla;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                entidades.add(mapper.map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entidades;
    }
    
// Método para actualizar un objeto en la base de datos
    public void actualizar(T entidad, String sqlUpdate) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlUpdate)) {
            // Ejecutar la consulta
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
      // Método para eliminar un objeto por su ID
    public void eliminar(int id) {
        String sql = "DELETE FROM " + tabla + " WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
