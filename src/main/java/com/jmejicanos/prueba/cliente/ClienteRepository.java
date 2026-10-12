package com.jmejicanos.prueba.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    public Object findAllById(Integer idCliente); //Obtener todos los clientes
    
    //Clientes
    boolean existsByDpi(String dpi); //Existe DPI
    boolean existsByCorreo(String correo); //Existe Correo
    
    boolean existsByDpiAndIdNot(String dpi, Integer idCliente);
    boolean existsByCorreoAndIdNot(String correo, Integer idCliente);
}
