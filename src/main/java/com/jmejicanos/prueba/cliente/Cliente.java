package com.jmejicanos.prueba.cliente;
import jakarta.persistence.*;

@Entity
@Table(name = "CLIENTES")
public class Cliente {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_cliente")
    private Integer id;
    
    @Column(name = "dpi")
    private String dpi;
    
    @Column(name = "nombres")
    private String nombres;
    
    @Column(name = "apellidos")
    private String apellidos;
    
    @Column(name = "correo")
    private String correo;
    
    @Column(name = "activo")
    private String activo;
    
    public Cliente(){
    }

    public Integer getIdCliente() {
        return id;
    }

    public void setIdCliente(Integer id) {
        this.id = id;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }    
}
