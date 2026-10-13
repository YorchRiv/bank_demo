package com.jmejicanos.prueba.cuentas;
import com.jmejicanos.prueba.cliente.ClienteRepository;
import com.jmejicanos.prueba.estadoscuenta.EstadosCuentaRepository;
import com.jmejicanos.prueba.tiposcuenta.TiposCuentaRepository;

import jakarta.validation.Validator;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CuentasService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private TiposCuentaRepository tiposCuentaRepository;
    
    @Autowired
    private EstadosCuentaRepository estadosCuentaRepository;
    
    private final CuentasRepository repository;
    private final Validator validator;

    public CuentasService(CuentasRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }
    
    //Consultar todos los cuentas
    public List<Cuentas> obtenerCuentas(){
        return repository.findAll();
    }
    
    //Consultar solo un cuenta por id    
    public Optional<Cuentas> obtenerCuentas(Integer idCuentas) {
        return repository.findById(idCuentas);
    }
    
    //Crear Cuentas
    public Cuentas crearCuentas(Cuentas nuevoCuentas){
        
        if(nuevoCuentas.getId_cliente() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID de cliente es Obligatorio");
        }
        
        if(!clienteRepository.existsById(nuevoCuentas.getId_cliente())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente no existe");
        }  
        
        if(nuevoCuentas.getId_tipo() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tipo de cuenta es Obligatorio");
        }
        
        if(!tiposCuentaRepository.existsById(nuevoCuentas.getId_tipo())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de Cuenta no Valido");
        }      
        
        if(nuevoCuentas.getId_estado()== null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado de cuenta es invalido");
        }
        
        if(!estadosCuentaRepository.existsById(nuevoCuentas.getId_estado())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado de Cuenta no Valido");
        }                                      
        
        if (nuevoCuentas.getSaldo() == null) {
            nuevoCuentas.setSaldo(BigDecimal.ZERO);
        }
        
        if(nuevoCuentas.getSaldo().compareTo(BigDecimal.ZERO) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El saldo no puede ser negativo");
        }
        
        if(nuevoCuentas.getNumero_cuenta() != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El numero de cuenta se genera automaitcamente");
        }
        
        if (nuevoCuentas.getId() != null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "No debe enviar el ID de la cuenta"
            );
        }
        
        return repository.save(nuevoCuentas);
    }
    
    //Cambiar Estado Cuenta
    public Cuentas estadoCuentas(Integer idCuentas, Cuentas datosActualizados){
        //Buscar Cuentas
        Cuentas cuenta = repository.findById(idCuentas).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La cuenta no existe"));
        
        if(datosActualizados.getId_estado() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado de cuenta es Obligatorio");
        }   
        
        if(!estadosCuentaRepository.existsById(datosActualizados.getId_estado())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado de cuenta no existe");
        }
        
        //Actualizar cuenta        
        cuenta.setId_estado(datosActualizados.getId_estado());
                
        //Guardar Cambios
        return repository.save(cuenta);
    }
}
