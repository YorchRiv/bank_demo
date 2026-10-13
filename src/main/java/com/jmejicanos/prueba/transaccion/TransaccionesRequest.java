package com.jmejicanos.prueba.transaccion;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

public class TransaccionesRequest {    
      
    @NotNull(message =  "El Usuario es Obligatorio")
    private Integer id_usuario;
    
    @NotNull(message =  "La cuenta origen es obligatoria")
    private Integer id_cuenta_origen;
    
    @NotNull(message =  "La cuenta destino es obligatoria")
    private Integer id_cuenta_destino;
    
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    @Digits(integer = 16, fraction = 2)
    @NotNull(message = "El monto es obligatorio")
    private BigDecimal monto;
    
    @NotBlank(message = "La referencia es obligatoria")
    @Size(max = 100, message = "La referencia no puede superar 100 caracteres")
    private String referencia;
        
    @NotNull(message = "La clave de idempotencia es obligatoria")
    private UUID idempotency_key;

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId_cuenta_origen() {
        return id_cuenta_origen;
    }

    public void setId_cuenta_origen(Integer id_cuenta_origen) {
        this.id_cuenta_origen = id_cuenta_origen;
    }

    public Integer getId_cuenta_destino() {
        return id_cuenta_destino;
    }

    public void setId_cuenta_destino(Integer id_cuenta_destino) {
        this.id_cuenta_destino = id_cuenta_destino;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public UUID getIdempotency_key() {
        return idempotency_key;
    }

    public void setIdempotency_key(UUID idempotency_key) {
        this.idempotency_key = idempotency_key;
    }
}
