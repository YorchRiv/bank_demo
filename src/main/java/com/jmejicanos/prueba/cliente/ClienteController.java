package com.jmejicanos.prueba.cliente;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }
    
    @GetMapping("/clientes")
    public List<Cliente> obtenerClientes() {
        try {
            return service.obtenerClientes();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> obtenerCliente(@PathVariable Integer id){
        Optional<Cliente> cliente = service.obtenerCliente(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        }
        else {
            return ResponseEntity.status(404).body(Map.of("error:","Cliente no encontrado"));
        }
    }
}
