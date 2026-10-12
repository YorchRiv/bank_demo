package com.jmejicanos.prueba.estadoscuenta;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class EstadosCuentaService {
    private final EstadosCuentaRepository repository;
    private final Validator validator;

    public EstadosCuentaService(EstadosCuentaRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    //Consultar todos los tipos de cuenta
    public List<EstadosCuenta> obtenerEstadosCuenta(){
        return repository.findAll();
    }
    
     //Consultar solo un tipo de cuenta por id    
    public Optional<EstadosCuenta> obtenerEstadosCuenta(Integer idEstadosCuenta) {
        return repository.findById(idEstadosCuenta);
    }
    
    //Crear Tipo de cuenta
    public EstadosCuenta crearEstadosCuenta(EstadosCuenta nuevoEstadosCuenta){  
        return repository.save(nuevoEstadosCuenta);
    }
    
    //Actualizar Cliente
    public EstadosCuenta actualizarEstadosCuenta(Integer idEstadosCuenta, EstadosCuenta datoActual){
        //Buscar Cliente
        EstadosCuenta estadoscuenta = repository.findById(idEstadosCuenta).orElseThrow(() -> new IllegalArgumentException("El estado de cuenta no existe"));

        //Actualizar cliente            
        if (datoActual.getNombre() != null) {
            estadoscuenta.setNombre(datoActual.getNombre());            
        } 
        
        //Validar el cliente completo
        Set<ConstraintViolation<EstadosCuenta>> errores = validator.validate(estadoscuenta);
        
        if(!errores.isEmpty()){
            throw new IllegalArgumentException(errores.iterator().next().getMessage());
        }
        
        //Guardar Cambios
        return repository.save(estadoscuenta);
    }
    
    //Eliminar Cliente
    public void eliminarEstadosCuenta(Integer idEstadosCuenta){
        EstadosCuenta estadoscuenta = repository.findById(idEstadosCuenta).orElseThrow(() -> new IllegalArgumentException("El estado de cuenta no existe"));
        repository.deleteById(idEstadosCuenta);
    }
}
