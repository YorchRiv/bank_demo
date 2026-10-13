
package com.jmejicanos.prueba.transaccion;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TransaccionesService {
       
    private final TransaccionesRepository repository;

    public TransaccionesService(TransaccionesRepository repository) {
        this.repository = repository;
    }
    
    //Consultar todos los cuentas
    public List<Transacciones> obtenerTransacciones(){
        return repository.findAll();
    }
    
    //Consultar solo un cuenta por id    
    public Transacciones obtenerTransacciones(Integer id_transaccion) {                
        return repository.findById(id_transaccion).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La transacción no existe"));
    }    
}
