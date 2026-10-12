package com.jmejicanos.prueba.tiposcuenta;

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
public class TiposCuentaController {
    private final TiposCuentaService service;

    public TiposCuentaController(TiposCuentaService service) {
        this.service = service;
    }
    
    @GetMapping("/tiposcuenta")
    public List<TiposCuenta> obtenerTiposCuenta() {
        return service.obtenerTiposCuenta();
    }
    
    @GetMapping("/tiposcuenta/{id}")
    public ResponseEntity<?> obtenerTiposCuenta(@PathVariable Integer id){
        Optional<TiposCuenta> tiposcuenta = service.obtenerTiposCuenta(id);
        if (tiposcuenta.isPresent()) {
            return ResponseEntity.ok(tiposcuenta.get());
        }
        else {
            return ResponseEntity.status(404).body(Map.of("error:","Tipo de Cuenta no encontrado"));
        }
    }
    
    @PostMapping("/tiposcuenta")
    public ResponseEntity<?> crearTipoCuenta(@Valid @RequestBody TiposCuenta nuevoTipoCuenta){       
        try{
            TiposCuenta tcuentaguardado = service.crearTipoCuenta(nuevoTipoCuenta);
            return ResponseEntity.status(HttpStatus.CREATED).body(tcuentaguardado);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensaje:",e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();;
            return ResponseEntity.internalServerError().body(Map.of("mensaje:","Ocurrio un error inesperado","error:",e.getMessage()));
        }
        
    }
    
    @PatchMapping("/tiposcuenta/{id}")
    public ResponseEntity<?> actualizarTiposCuenta(@PathVariable Integer id, @RequestBody TiposCuenta datosActualizados){       
        try{
            TiposCuenta cliente = service.actualizarTiposCuenta(id, datosActualizados);
            return ResponseEntity.ok(cliente);       
        } catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensaje", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();;
            return ResponseEntity.internalServerError().body(Map.of("mensaje:","Ocurrio un error inesperado","error:",e.getMessage()));
        }
    }
    
    @DeleteMapping("tiposcuenta/{id}")
    public ResponseEntity<?> eliminarTipoCuenta(@PathVariable Integer id){
        try{
            service.eliminarTiposCuenta(id);
            return ResponseEntity.ok(Map.of("mensaje:","Tipo de Cuenta eliminado correctamente"));       
        } catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensaje", e.getMessage()));
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
