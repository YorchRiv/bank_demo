package com.jmejicanos.prueba.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    public Object findAllById(Integer idCliente);
    
}
