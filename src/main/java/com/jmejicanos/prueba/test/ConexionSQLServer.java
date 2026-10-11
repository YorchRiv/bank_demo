package com.jmejicanos.prueba.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConexionSQLServer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public ConexionSQLServer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        String sql = "SELECT name FROM sys.databases WHERE name = 'bankCore'";

        String resultado = jdbcTemplate.queryForObject(
                sql,
                String.class
        );

        System.out.println("Resultado SQL: " + resultado);
    }
}






/*package com.jmejicanos.prueba.test;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class ConexionSQLServer {
    Connection conexion = null;
    
    String usuario = "sa";
    String contrasena = "serverSQL";
    String db = "bankCore";
    String puerto = "1433";
    
    public String obtenerConexion() {
        try{
            String cadena = "jdbc:sqlserver://localhost:1433;databaseName=bankCore;encrypt=true;trustServerCertificate=true";
            conexion = DriverManager.getConnection(cadena, usuario, contrasena); 
            return "Hola conexion exitosa";
        } catch (Exception e) {
            return "nel: "+e;
        }      
    }
}*/
