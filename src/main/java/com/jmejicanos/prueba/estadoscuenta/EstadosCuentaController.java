package com.jmejicanos.prueba.estadoscuenta;
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
public class EstadosCuentaController {
    private final EstadosCuentaService service;

    public EstadosCuentaController(EstadosCuentaService service) {
        this.service = service;
    }
    
    @GetMapping("/estadoscuenta")
    public List<EstadosCuenta> obtenerEstadosCuenta() {
        return service.obtenerEstadosCuenta();
    }
    
    @GetMapping("/estadoscuenta/{id}")
    public ResponseEntity<?> obtenerEstadosCuenta(@PathVariable Integer id){
        Optional<EstadosCuenta> estadoscuenta = service.obtenerEstadosCuenta(id);
        if (estadoscuenta.isPresent()) {
            return ResponseEntity.ok(estadoscuenta.get());
        }
        else {
            return ResponseEntity.status(404).body(Map.of("error:","Estado de Cuenta no encontrado"));
        }
    }
    
    @PostMapping("/estadoscuenta")
    public ResponseEntity<?> crearEstadosCuenta(@Valid @RequestBody EstadosCuenta nuevoEstadosCuenta){       
        try{
            EstadosCuenta tcuentaguardado = service.crearEstadosCuenta(nuevoEstadosCuenta);
            return ResponseEntity.status(HttpStatus.CREATED).body(tcuentaguardado);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensaje:",e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();;
            return ResponseEntity.internalServerError().body(Map.of("mensaje:","Ocurrio un error inesperado","error:",e.getMessage()));
        }
        
    }
    
    @PatchMapping("/estadoscuenta/{id}")
    public ResponseEntity<?> actualizarEstadosCuenta(@PathVariable Integer id, @RequestBody EstadosCuenta datosActualizados){       
        try{
            EstadosCuenta cliente = service.actualizarEstadosCuenta(id, datosActualizados);
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
    
    @DeleteMapping("estadoscuenta/{id}")
    public ResponseEntity<?> eliminarEstadosCuenta(@PathVariable Integer id){
        try{
            service.eliminarEstadosCuenta(id);
            return ResponseEntity.ok(Map.of("mensaje:","Estado de Cuenta eliminado correctamente"));       
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
