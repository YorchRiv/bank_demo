
package com.jmejicanos.prueba.movimientos;

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
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

@Entity
@Table(name = "MOVIMIENTOS")
public class Movimientos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_movimiento")
    private Integer id;
    
    @Column(name="id_transaccion", nullable = false)
    @NotNull(message =  "La transaccion es Obligatoria")
    private Integer id_transaccion;
    
    @Column(name="id_cuenta", nullable = false)
    @NotNull(message =  "La cuenta es Obligatoria")
    private Integer id_cuenta;
    
    @Column(name="naturaleza", nullable = false) 
    @NotBlank(message = "La naturaleza es obligatoria")
    @Pattern(
        regexp = "DEBITO|CREDITO",
        message = "La naturaleza debe ser DEBITO o CREDITO")  
    
    private String naturaleza;
    
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    @Digits(integer = 16, fraction = 2)
    @Column(name = "monto", nullable = false, precision = 18, scale = 2)
    @NotNull(message = "El monto es obligatorio")
    private BigDecimal monto;
    
    @DecimalMin(value = "0.00", message = "El saldo restante no puede ser negativo")
    @Digits(integer = 16, fraction = 2)
    @Column(name = "saldo_restante", nullable = false, precision = 18, scale = 2)
    @NotNull(message = "El saldo restante es obligatorio")
    private BigDecimal saldo_restante;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_transaccion() {
        return id_transaccion;
    }

    public void setId_transaccion(Integer id_transaccion) {
        this.id_transaccion = id_transaccion;
    }

    public Integer getId_cuenta() {
        return id_cuenta;
    }

    public void setId_cuenta(Integer id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getSaldo_restante() {
        return saldo_restante;
    }

    public void setSaldo_restante(BigDecimal saldo_restante) {
        this.saldo_restante = saldo_restante;
    }
    
    
}
