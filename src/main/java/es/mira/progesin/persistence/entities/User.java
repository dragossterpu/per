package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author EZENTIS
 * 
 * Entidad para un Usuario
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "username")
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static final String PROVISIONAL = "provisional";
    
    @Id
    @Column(name = "username")
    protected String username;
    
    @Column(name = "password", length = 100, nullable = false)
    protected String password;
    
    @Column(name = "estado", length = 8, nullable = false)
    @Enumerated(EnumType.STRING)
    protected EstadoEnum estado;
    
    @Column(name = "nombre", length = 50, nullable = false)
    protected String nombre;
    
    @Column(name = "prim_apellido", length = 50, nullable = false)
    protected String apellido1;
    
    @Column(name = "segundo_apellido", length = 50)
    protected String apellido2;
    
    @Column(name = "doc_identidad", length = 10, nullable = false)
    protected String docIdentidad;
    
    @Column(name = "telefono", length = 12)
    protected String telefono;
    
    @Column(name = "tfno_movil_oficial", length = 12)
    protected String telefonoMovilOficial;
    
    @Column(name = "tfno_movil_particular", length = 12)
    protected String telefonoMovilParticular;
    
    @Column(name = "correo", length = 50, nullable = false)
    protected String correo;
    
    @Column(name = "role", length = 25, nullable = false)
    @Enumerated(EnumType.STRING)
    protected RoleEnum role;
    
    @Column(name = "despacho", length = 20)
    protected String despacho;
    
    @ManyToOne
    @JoinColumn(name = "ID_CUERPO", foreignKey = @ForeignKey(name = "FK_U_CUERPO"))
    private CuerpoEstado cuerpoEstado;
    
    @ManyToOne
    @JoinColumn(name = "ID_EMPLEO", foreignKey = @ForeignKey(name = "FK_U_EMPLEO"))
    protected Empleo empleo;
    
    @ManyToOne
    @JoinColumn(name = "ID_PUESTO", foreignKey = @ForeignKey(name = "FK_U_PUESTO"))
    private PuestoTrabajo puestoTrabajo;
    
    @ManyToOne
    @JoinColumn(name = "ID_DEPARTAMENTO", foreignKey = @ForeignKey(name = "FK_U_DEPARTAMENTO"))
    protected Departamento departamento;
    
    @Column(name = "nivel")
    private Integer nivel;
    
    @ManyToOne
    @JoinColumn(name = "ID_CLASE", foreignKey = @ForeignKey(name = "FK_U_CLASE"))
    protected ClaseUsuario claseUsuario;
    
    @Column(name = "categoria", length = 20)
    protected String categoria;
    
    @Column(name = "fecha_destino_ipss")
    protected Date fechaDestinoIPSS;
    
    @Column(name = "fecha_ingreso")
    protected Date fechaIngreso;
    
    @Column(name = "fecha_inactivo")
    protected Date fechaInactivo;
    
    /**
     * Constructor de usuarios provisionales principales
     * 
     * @param username id del usuario
     * @param password contraseña cifrada
     * @param role rol del usuario en la aplicación
     */
    public User(String username, String password, RoleEnum role) {
        this.setUsername(username);
        this.setPassword(password);
        this.setRole(role);
        this.setEstado(EstadoEnum.ACTIVO);
        this.setNombre(PROVISIONAL);
        this.setApellido1(PROVISIONAL);
        this.setDocIdentidad("000000000T");
        this.setCorreo(username);
        this.setFechaDestinoIPSS(new Date());
        this.setNivel(0);
        this.setFechaAlta(new Date());
        this.setUsernameAlta(SecurityContextHolder.getContext().getAuthentication().getName());
    }
    
    /**
     * Constructor de usuarios provisionales secundarios para la cumplimentación de cuestionarios (inicialmente
     * inactivos hasta que se les asigne un area)
     * 
     * @param username id del usuario
     * @param password contraseña cifrada
     * @param role rol del usuario en la aplicación
     * @param correoPrincipal correo de envío del cuestionario
     */
    public User(String username, String password, RoleEnum role, String correoPrincipal) {
        this.setUsername(username);
        this.setPassword(password);
        this.setRole(role);
        this.setEstado(EstadoEnum.INACTIVO);
        this.setNombre(PROVISIONAL);
        this.setApellido1(PROVISIONAL);
        this.setDocIdentidad("000000000T");
        this.setCorreo(correoPrincipal);
        this.setFechaDestinoIPSS(new Date());
        this.setNivel(0);
        this.setFechaAlta(new Date());
        this.setUsernameAlta(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
