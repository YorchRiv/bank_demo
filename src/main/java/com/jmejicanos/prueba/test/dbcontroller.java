/*package com.jmejicanos.prueba.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class dbcontroller implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;
    public dbcontroller(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {

        String db = jdbcTemplate.queryForObject(
            "SELECT DB_NAME()",
            String.class
        );

        System.out.println("Conexion exitosa");
        System.out.println("Base de datos: " + db);
    }
}*/
