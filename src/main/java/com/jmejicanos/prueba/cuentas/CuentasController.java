package com.jmejicanos.prueba.cuentas;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CuentasController {
    private final CuentasService service;

    public CuentasController(CuentasService service) {
        this.service = service;
    }
    
    @GetMapping("/cuentas")
    public List<Cuentas> obtenerCuentas() {
        return service.obtenerCuentas();
    }
    
    @GetMapping("/cuentas/{id}")
    public ResponseEntity<?> obtenerCuentas(@PathVariable Integer id){
        Optional<Cuentas> cuentas = service.obtenerCuentas(id);
        if (cuentas.isPresent()) {
            return ResponseEntity.ok(cuentas.get());
        }
        else {
            return ResponseEntity.status(404).body(Map.of("error:","La Cuenta no se encontró"));
        }
    }
    
    @PostMapping("/cuentas")
    public ResponseEntity<?> crearCuentas(@Valid @RequestBody Cuentas nuevoTipoCuenta){       
        try{
            Cuentas tcuentaguardado = service.crearCuentas(nuevoTipoCuenta);
            return ResponseEntity.status(HttpStatus.CREATED).body(tcuentaguardado);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensaje:",e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();;
            return ResponseEntity.internalServerError().body(Map.of("mensaje:","Ocurrio un error inesperado","error:",e.getMessage()));
        }
        
    }
    
    @PatchMapping("/cuentas/{id}")
    public ResponseEntity<?> estadoCuentas(@PathVariable Integer id, @RequestBody Cuentas datosActualizados){       
        try{
            Cuentas cuenta = service.estadoCuentas(id, datosActualizados);
            return ResponseEntity.ok(cuenta);       
        } catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensaje", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();;
            return ResponseEntity.internalServerError().body(Map.of("mensaje:","Ocurrio un error inesperado","error:",e.getMessage()));
        }
    }       
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> manejarValidacion(MethodArgumentNotValidException e){
        String mensaje = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(Map.of("mensaje:",mensaje));
    }
}
