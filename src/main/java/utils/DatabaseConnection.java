/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author alna7
 */
public class DatabaseConnection {
    
    private static final String url =  "jdbc:mysql://localhost:3306/DairySync";
    private static final String user = "root";
    private static final String password = "1234567";
     public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
 
}
