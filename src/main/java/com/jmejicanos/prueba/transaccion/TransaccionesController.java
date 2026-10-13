
package com.jmejicanos.prueba.transaccion;

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
public class TransaccionesController {
    private final TransaccionesService service;

    public TransaccionesController(TransaccionesService service) {
        this.service = service;
    }
    
    @GetMapping("/transacciones")
    public List<Transacciones> obtenerTransaccion() {
        return service.obtenerTransacciones();
    }
    
    @GetMapping("/transacciones/{id}")
    public ResponseEntity<?> obtenerTransacciones(@PathVariable Integer id){
        Transacciones transaccion = service.obtenerTransacciones(id);
        return ResponseEntity.ok(transaccion);
    }
    
    /*@PostMapping("/transacciones")
    public ResponseEntity<?> crearTransacciones(@Valid @RequestBody Transacciones nuevoTipoTransaccion){       
        try{
            Transacciones transaccionguardado = service.crearTransacciones(nuevoTipoTransaccion);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaccionguardado);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensaje:",e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();;
            return ResponseEntity.internalServerError().body(Map.of("mensaje:","Ocurrio un error inesperado","error:",e.getMessage()));
        }
        
    }     */
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> manejarValidacion(MethodArgumentNotValidException e){
        String mensaje = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(Map.of("mensaje:",mensaje));
    }
}
