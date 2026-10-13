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