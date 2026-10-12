package com.jmejicanos.prueba.estadoscuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadosCuentaRepository extends JpaRepository<EstadosCuenta, Integer> {
    public Object findAllById(Integer idTiposCuenta);
}
