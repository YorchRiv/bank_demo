package com.jmejicanos.prueba.cliente;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
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
    //Eliminar Cliente
}
