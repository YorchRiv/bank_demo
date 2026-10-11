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
}
