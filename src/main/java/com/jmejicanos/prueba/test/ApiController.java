package com.jmejicanos.prueba.test;

import java.math.BigDecimal;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    
    @GetMapping("/hola")
    public String hola(){
        return "Hola Jorge xd";
    }
    
    @PostMapping("/nombre")
    public Map<String, Object> persona(@RequestBody Map<String, Object> datos){
        String nombre = (String) datos.get("nombre");
        int edad = ((Number) datos.get("edad")).intValue();
        
        return Map.of("mensaje", "Hola " + nombre, "edad",edad);
    }
    
    @PostMapping("/cuenta")
    public Map<String, Object> cuenta(@RequestBody Map<String, Object> datos){
        BigDecimal saldo = new BigDecimal(datos.get("saldo").toString());
        BigDecimal retiro = new BigDecimal(datos.get("retiro").toString());
        BigDecimal saldoActual = saldo.subtract(retiro);
        
        if (retiro.compareTo(saldo) <= 0){
            return Map.of("mensaje", "Retiro realizado exitosamente", "saldoAnterior", saldo,"saldoActual", saldoActual);
        }
        else {
            return Map.of("mensaje", "No cuenta con saldo suficiente");
        }
    }
}
