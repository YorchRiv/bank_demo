package com.jmejicanos.prueba.cliente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "CLIENTES")
public class Cliente {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_cliente")
    private Integer id;
    
    @Column(name = "dpi")
    @NotBlank(message =  "El DPI es obligatorio")
    @Size(min = 13, max = 13, message = "El DPI debe contener exactamente 13 caracteres")
    private String dpi;
    
    @Column(name = "nombres")
    @NotBlank(message =  "El nombre es obligatorio")
    private String nombres;
    
    @Column(name = "apellidos")
    @NotBlank(message =  "El apellido es obligatorio")
    private String apellidos;
    
    @Column(name = "correo")
    @NotBlank(message =  "El correo es obligatorio")
    @Email(message =  "El formato del correo no es valido")
    private String correo;
    
    @Column(name = "activo")    
    @NotNull(message =  "El campo activo es obligatorio")
    private Boolean activo;
    
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }    
}
