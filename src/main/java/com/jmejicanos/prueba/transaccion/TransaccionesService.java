
package com.jmejicanos.prueba.transaccion;

import com.jmejicanos.prueba.cuentas.CuentasRepository;
import com.jmejicanos.prueba.movimientos.MovimientosRepository;
import com.jmejicanos.prueba.usuarios.Usuarios;
import com.jmejicanos.prueba.cuentas.Cuentas;
import com.jmejicanos.prueba.movimientos.Movimientos;
import com.jmejicanos.prueba.usuarios.UsuariosRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransaccionesService {
       
    private final TransaccionesRepository repository;        
    private final MovimientosRepository movimientosRepository;
    private final CuentasRepository cuentasRepository;
    private final UsuariosRepository usuariosRepository;
    private final EntityManager entityManager;

    public TransaccionesService(TransaccionesRepository repository, 
            MovimientosRepository movimientosRepository, 
            CuentasRepository cuentasRepository, 
            UsuariosRepository usuariosRepository, 
            EntityManager entityManager) {
        this.repository = repository;        
        this.movimientosRepository = movimientosRepository;
        this.cuentasRepository = cuentasRepository;
        this.usuariosRepository = usuariosRepository;
        this.entityManager = entityManager;
    }
    
    //Consultar todos los cuentas
    public List<Transacciones> obtenerTransacciones(){
        return repository.findAll();
    }
    
    //Consultar solo un cuenta por id    
    public Transacciones obtenerTransacciones(Integer id_transaccion) {                
        return repository.findById(id_transaccion).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La transacción no existe"));
    }    
    
    @Transactional
    public Transacciones realizarTransferencia(TransaccionesRequest request){
        // Validar que la transacción no exista previamente
        Transacciones duplicado = new Transacciones();
        duplicado.setIdempotency_key(request.getIdempotency_key());

        boolean existe = repository.exists(Example.of(duplicado));

        if (existe) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe una transacción con esta clave de idempotencia"
            );
        }

        //Buscar Usuario       
        Usuarios usuarios = usuariosRepository.findById(request.getId_usuario())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "El Usuario no existe"));
        
        if(!Boolean.TRUE.equals(usuarios.getActivo())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El usuario no se encuentra activo");
        }                

        Integer idOrigen = request.getId_cuenta_origen();
        Integer idDestino = request.getId_cuenta_destino();

        // Evitar transferencias a la misma cuenta
        if (idOrigen.equals(idDestino)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cuenta origen y destino no pueden ser la misma");
        }

        // Determinar el orden de bloqueo
        Integer primerId = Math.min(idOrigen, idDestino);
        Integer segundoId = Math.max(idOrigen, idDestino);

        // Bloquear ambas cuentas
        Cuentas primeraCuenta = entityManager.find(
                Cuentas.class, primerId, LockModeType.PESSIMISTIC_WRITE);

        Cuentas segundaCuenta = entityManager.find(
                Cuentas.class, segundoId, LockModeType.PESSIMISTIC_WRITE);
        
        Cuentas cuentaorigen = idOrigen.equals(primerId)
        ? primeraCuenta : segundaCuenta;

        Cuentas cuentadestino = idDestino.equals(primerId)
                ? primeraCuenta : segundaCuenta;
        
        if (cuentaorigen == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, 
                    "La cuenta origen no existe");
        }

        if (cuentadestino == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, 
                    "La cuenta destino no existe");
        }

        
        if (!Integer.valueOf(1).equals(cuentaorigen.getId_estado())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cuenta origen no se encuentra activa");
        }

        if (!Integer.valueOf(1).equals(cuentadestino.getId_estado())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cuenta destino no se encuentra activa");
        }     
        
        //Comprobar Saldo de cuenta origen.
        if(cuentaorigen.getSaldo().compareTo(request.getMonto()) < 0){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, 
                    "Saldo insuficiente para la transferencia");
        }
        
        //Calcular nuevos saldos
        BigDecimal nuevoSaldoOrigen = cuentaorigen.getSaldo().subtract(request.getMonto());
        BigDecimal nuevoSaldoDestino = cuentadestino.getSaldo().add(request.getMonto());
        
        //Registrar transaccion
        Transacciones transaccion = new Transacciones();       
        transaccion.setId_usuario(request.getId_usuario());
        transaccion.setMonto(request.getMonto());
        transaccion.setReferencia(request.getReferencia());
        transaccion.setIdempotency_key(request.getIdempotency_key());
        transaccion = repository.save(transaccion);                
        
        //Guardar nuevos saldos
        cuentaorigen.setSaldo(nuevoSaldoOrigen);
        cuentadestino.setSaldo(nuevoSaldoDestino);
        cuentasRepository.save(cuentaorigen);
        cuentasRepository.save(cuentadestino);
        
        //Registro del debito.
        Movimientos debito = new Movimientos();
        debito.setId_transaccion(transaccion.getId());
        debito.setId_cuenta(cuentaorigen.getId());
        debito.setNaturaleza("DEBITO");
        debito.setMonto(request.getMonto());
        debito.setSaldo_restante(nuevoSaldoOrigen);
        movimientosRepository.save(debito);
        
        //Registro del credito.
        Movimientos credito = new Movimientos();
        credito.setId_transaccion(transaccion.getId());
        credito.setId_cuenta(cuentadestino.getId());
        credito.setNaturaleza("CREDITO");
        credito.setMonto(request.getMonto());
        credito.setSaldo_restante(nuevoSaldoDestino);
        movimientosRepository.save(credito);
        
        return transaccion;
    }
}
