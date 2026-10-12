package com.jmejicanos.prueba.cliente;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClienteService {
    
    private final ClienteRepository repository;
    private final Validator validator;

    public ClienteService(ClienteRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }
    
    //Consultar todos los clientes
    public List<Cliente> obtenerClientes(){
        return repository.findAll();
    }
    
    //Consultar solo un cliente por id    
    public Optional<Cliente> obtenerCliente(Integer idCliente) {
        return repository.findById(idCliente);
    }
    
    //Crear Cliente
    public Cliente crearCliente(Cliente nuevoCliente){
        if (repository.existsByDpi(nuevoCliente.getDpi())){
            throw new IllegalArgumentException("El DPI ya se encuentra registrado");
        }
        
        if (repository.existsByCorreo(nuevoCliente.getCorreo())){
            throw new IllegalArgumentException("El Correo ya se encuentra registrado");
        }
        
        return repository.save(nuevoCliente);
    }
    
    //Actualizar Cliente
    public Cliente actualizarCliente(Integer idCliente, Cliente datosActualizados){
        //Buscar Cliente
        Cliente cliente = repository.findById(idCliente).orElseThrow(() -> new IllegalArgumentException("El cliente no existe"));                       

        //Actualizar cliente
        //Validar DPI
        if (datosActualizados.getDpi() != null) {
            if (repository.existsByDpiAndIdNot(datosActualizados.getDpi(), idCliente)){
                throw new IllegalStateException("El DPI ya pertenece a otro cliente");
            }
            
            cliente.setDpi(datosActualizados.getDpi());
        }
                

        if (datosActualizados.getNombres() != null) {
            cliente.setNombres(datosActualizados.getNombres());
        }

        if (datosActualizados.getApellidos() != null) {
            cliente.setApellidos(datosActualizados.getApellidos());
        }
        
        if (datosActualizados.getCorreo() != null) {
            if(repository.existsByCorreoAndIdNot(datosActualizados.getCorreo(), idCliente)){
                throw new IllegalStateException("El Correo ya pertenece a otro cliente");
            }
            cliente.setCorreo(datosActualizados.getCorreo());
        }

        if (datosActualizados.getActivo() != null) {
            cliente.setActivo(datosActualizados.getActivo());
        }     
        
        //Validar el cliente completo
        Set<ConstraintViolation<Cliente>> errores = validator.validate(cliente);
        
        if(!errores.isEmpty()){
            throw new IllegalArgumentException(errores.iterator().next().getMessage());
        }
        
        //Guardar Cambios
        return repository.save(cliente);
    }
    
    //Eliminar Cliente
    public void eliminarCliente(Integer idCliente){
        Cliente cliente = repository.findById(idCliente).orElseThrow(() -> new IllegalArgumentException("El cliente no existe"));
        
        if(Boolean.FALSE.equals(cliente.getActivo())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El cliente ya se encuentra inactivo");
        }
        
        cliente.setActivo(false);
        repository.save(cliente);
    }
}
