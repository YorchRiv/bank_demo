package com.jmejicanos.prueba.movimientos;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MovimientosController {
    private final MovimientosService service;

    public MovimientosController(MovimientosService service) {
        this.service = service;
    }
    
    @GetMapping("/movimientos")
    public List<Movimientos> obtenerMovimientos() {
        return service.obtenerMovimientos();
    }
    
    @GetMapping("/movimientos/{id}")
    public ResponseEntity<?> obtenerMovimiento(@PathVariable Integer id){
        Movimientos transaccion = service.obtenerMovimiento(id);
        return ResponseEntity.ok(transaccion);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> manejarValidacion(MethodArgumentNotValidException e){
        String mensaje = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(Map.of("mensaje:",mensaje));
    }
}
