package es.mira.progesin.persistence.entities;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name="USER2", schema="public")
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class User {
    @Id
    //@Size(min=1, max=15, message="user.username.size")
    @Column(name="username", length=15)
    protected String username;

    @NotNull
    @Column(name="password", length=15, nullable=false)
    protected String password;

    @NotNull
    @Column(length=1)
    protected String activo;
    
    @NotNull
    @Column(name="nombre", length=50)
    protected String nombre;

    @NotNull
    @Column(name="prim_apellido", length=50)
    protected String apellido1;

    @Column(name="segundo_apellido", length=50)
    protected String apellido2;

    @NotNull
    @Column(name="doc_identidad",length=12)
    protected String docIndentidad;
    
    @Column(name="telefono",length=12)
    protected String telefono;
    
    @NotNull
    @Column(name="correo", length=50)
    protected String correo;
    
    @NotNull
    protected RoleEnum role;
    
    @NotNull
    @Column(name="num_inspector", length=15)
    protected String numInspector;
    
    @NotNull
    @Column(name="envio_notif", length=2)
    protected String envioNotificacion;

    // CuerpoEstado cuerpo_estado;
    
    @NotNull
    @Column(name="fecha_alta")
    protected Date fechaAlta;
    
    @Column(name="fecha_baja")
    protected Date fechaBaja;
    
    @Column(name="fecha_modificacion")
    protected Date fechaModificacion;
    
    @Column(name="fecha_inactivo")
    protected Date fechaInactivo;
    
    @NotNull
    @Column(name="username_alta", length=12)
    protected String usernameAlta;
    
    @Column(name="username_modif", length=12)
    protected String usernameModif;
    
    @Column(name="username_baja", length=12)
    protected String usernameBaja;
    
    
    

}

