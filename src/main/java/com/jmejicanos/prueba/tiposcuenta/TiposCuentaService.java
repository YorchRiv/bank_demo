package com.jmejicanos.prueba.tiposcuenta;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class TiposCuentaService {
    
    private final TiposCuentaRepository repository;
    private final Validator validator;

    public TiposCuentaService(TiposCuentaRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    //Consultar todos los tipos de cuenta
    public List<TiposCuenta> obtenerTiposCuenta(){
        return repository.findAll();
    }
    
     //Consultar solo un tipo de cuenta por id    
    public Optional<TiposCuenta> obtenerTiposCuenta(Integer idTiposCuenta) {
        return repository.findById(idTiposCuenta);
    }
    
    //Crear Tipo de cuenta
    public TiposCuenta crearTipoCuenta(TiposCuenta nuevoTipoCuenta){  
        return repository.save(nuevoTipoCuenta);
    }
    
    //Actualizar Cliente
    public TiposCuenta actualizarTiposCuenta(Integer idTiposCuenta, TiposCuenta datoActual){
        //Buscar Cliente
        TiposCuenta tiposcuenta = repository.findById(idTiposCuenta).orElseThrow(() -> new IllegalArgumentException("El tipo de cuenta no existe"));

        //Actualizar cliente            
        if (datoActual.getNombre() != null) {
            tiposcuenta.setNombre(datoActual.getNombre());            
        } 
        
        //Validar el cliente completo
        Set<ConstraintViolation<TiposCuenta>> errores = validator.validate(tiposcuenta);
        
        if(!errores.isEmpty()){
            throw new IllegalArgumentException(errores.iterator().next().getMessage());
        }
        
        //Guardar Cambios
        return repository.save(tiposcuenta);
    }
    
    //Eliminar Cliente
    public void eliminarTiposCuenta(Integer idTiposCuenta){
        TiposCuenta tiposcuenta = repository.findById(idTiposCuenta).orElseThrow(() -> new IllegalArgumentException("El tipo de cuenta no existe"));
        repository.deleteById(idTiposCuenta);
    }
}
