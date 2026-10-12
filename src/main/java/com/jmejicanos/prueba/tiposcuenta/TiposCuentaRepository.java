package com.jmejicanos.prueba.tiposcuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiposCuentaRepository extends JpaRepository<TiposCuenta, Integer>{
    public Object findAllById(Integer idTiposCuenta);
}
