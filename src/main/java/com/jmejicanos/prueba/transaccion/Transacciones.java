
package com.jmejicanos.prueba.transaccion;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

@Entity
@Table(name = "TRANSACCIONES")
public class Transacciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_transaccion")
    private Integer id;
    
    @Column(name="id_usuario", nullable = false)
    @NotNull(message =  "El Usuario es Obligatorio")
    private Integer id_usuario;
    
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    @Digits(integer = 16, fraction = 2)
    @Column(name = "monto", nullable = false, precision = 18, scale = 2)
    @NotNull(message = "El monto es obligatorio")
    private BigDecimal monto;
    
    @Column(name="referencia", nullable = false) 
    @NotBlank(message = "La referencia es obligatoria")
    @Size(max = 100, message = "La referencia no puede superar 100 caracteres")
    private String referencia;
    
    @Column(name = "idempotency_key", nullable = false, unique = true)
    @NotNull(message = "La clave de idempotencia es obligatoria")
    private UUID idempotency_key;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Generated(event = EventType.INSERT)
    @Column(name = "fecha_utc", insertable = false, updatable = false)
    private LocalDateTime fecha_utc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
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

    public LocalDateTime getFecha_utc() {
        return fecha_utc;
    }

    public void setFecha_utc(LocalDateTime fecha_utc) {
        this.fecha_utc = fecha_utc;
    }
    
    
}
