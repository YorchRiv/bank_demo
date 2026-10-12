package com.jmejicanos.prueba.cliente;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        return service.obtenerClientes();        
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
    
    @PostMapping("/clientes")
    public ResponseEntity<?> crearCliente(@Valid @RequestBody Cliente nuevoCliente){       
        try{
            Cliente clienteGuardado = service.crearCliente(nuevoCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteGuardado);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensaje:",e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();;
            return ResponseEntity.internalServerError().body(Map.of("mensaje:","Ocurrio un error inesperado","error:",e.getMessage()));
        }
        
    }
    
    @PatchMapping("/clientes/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Integer id, @RequestBody Cliente datosActualizados){       
        try{
            Cliente cliente = service.actualizarCliente(id, datosActualizados);
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> manejarValidacion(MethodArgumentNotValidException e){
        String mensaje = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(Map.of("mensaje:",mensaje));
    }
}
