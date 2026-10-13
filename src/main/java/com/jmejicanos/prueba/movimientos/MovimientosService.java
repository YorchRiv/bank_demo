package com.jmejicanos.prueba.movimientos;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class MovimientosService {
    private final MovimientosRepository repository;

    public MovimientosService(MovimientosRepository repository) {
        this.repository = repository;
    }
    
    //Consultar todos los cuentas
    public List<Movimientos> obtenerMovimientos(){
        return repository.findAll();
    }
    
    //Consultar solo un cuenta por id    
    public Movimientos obtenerMovimiento(Integer id) {                
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La transacción no existe"));
    }  
}
