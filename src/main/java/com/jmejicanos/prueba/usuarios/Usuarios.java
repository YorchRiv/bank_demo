package com.jmejicanos.prueba.usuarios;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "USUARIOS")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private Integer id;
    
    @Column(name="id_rol", nullable = false)
    @NotNull(message =  "El Rol es Obligatorio")
    private Integer id_rol;
    
    @Column(name="username", nullable = false) 
    @NotBlank(message = "El Usuario es Obligatorio")
    @Size(max = 50, message = "El usuario no puede superar 100 caracteres")
    private String username;
    
    @Column(name="password_hash", nullable = false) 
    @NotBlank(message = "La contraseña es Obligatorio")    
    private String password_hash;        

    @Column(name = "activo")    
    @NotNull(message =  "El campo activo es obligatorio")
    private Boolean activo = true;        
}
