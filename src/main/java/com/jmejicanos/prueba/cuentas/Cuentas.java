package com.jmejicanos.prueba.cuentas;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import java.math.BigDecimal;

@Entity
@Table(name = "CUENTAS")
public class Cuentas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cuenta")
    private Integer id;
    
    @Column(name="id_cliente", nullable = false)
    @NotNull(message =  "El Cliente es Obligatorio")
    private Integer id_cliente;
    
    @Column(name="id_tipo", nullable = false)
    @NotNull(message =  "El Tipo de cuenta es Obligatorio")
    private Integer id_tipo;
    
    @Column(name="id_estado", nullable = false)
    private Integer id_estado = 1;
    
    @Generated(event = EventType.INSERT)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column (name="numero_cuenta", insertable = false, updatable = false)    
    private String numero_cuenta;
    
    @Column(name = "saldo", nullable = false, precision = 18, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Integer getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(Integer id_tipo) {
        this.id_tipo = id_tipo;
    }

    public Integer getId_estado() {
        return id_estado;
    }

    public void setId_estado(Integer id_estado) {
        this.id_estado = id_estado;
    }

    public String getNumero_cuenta() {
        return numero_cuenta;
    }

    public void setNumero_cuenta(String numero_cuenta) {
        this.numero_cuenta = numero_cuenta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    
    
}
