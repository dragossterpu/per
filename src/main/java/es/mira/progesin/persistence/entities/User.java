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
 * 
 * Entidad para el almacenamiento de un Usuario.
 *
 * @author EZENTIS
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
    
    /**
     * Constante para usuarios provisionales.
     */
    private static final String PROVISIONAL = "provisional";
    
    /**
     * Login (ID).
     */
    @Id
    @Column(name = "username")
    protected String username;
    
    /**
     * Contraseña del usuario.
     */
    @Column(name = "password", length = 100, nullable = false)
    protected String password;
    
    /**
     * Estado: activo o inactivo.
     */
    @Column(name = "estado", length = 8, nullable = false)
    @Enumerated(EnumType.STRING)
    protected EstadoEnum estado;
    
    /**
     * Nombre del usuario.
     */
    @Column(name = "nombre", length = 50, nullable = false)
    protected String nombre;
    
    /**
     * Primer apellido.
     */
    @Column(name = "prim_apellido", length = 50, nullable = false)
    protected String apellido1;
    
    /**
     * Segundo apellido.
     */
    @Column(name = "segundo_apellido", length = 50)
    protected String apellido2;
    
    /**
     * Documento de identidad.
     */
    @Column(name = "doc_identidad", length = 10, nullable = false)
    protected String docIdentidad;
    
    /**
     * Teléfono del usuario.
     */
    @Column(name = "telefono", length = 12)
    protected String telefono;
    
    /**
     * Teléfono móvil oficial.
     */
    @Column(name = "tfno_movil_oficial", length = 12)
    protected String telefonoMovilOficial;
    
    /**
     * Teléfono móvil particular.
     */
    @Column(name = "tfno_movil_particular", length = 12)
    protected String telefonoMovilParticular;
    
    /**
     * Correo electronico del usuario.
     */
    @Column(name = "correo", length = 50, nullable = false)
    protected String correo;
    
    /**
     * Rol del usuario.
     */
    @Column(name = "role", length = 25, nullable = false)
    @Enumerated(EnumType.STRING)
    protected RoleEnum role;
    
    /**
     * Despacho del usuario.
     */
    @Column(name = "despacho", length = 20)
    protected String despacho;
    
    /**
     * Cuerpo al que pertenece el usuario.
     */
    @ManyToOne
    @JoinColumn(name = "ID_CUERPO", foreignKey = @ForeignKey(name = "FK_U_CUERPO"))
    private CuerpoEstado cuerpoEstado;
    
    /**
     * Empleo que tiene el usuario.
     */
    @ManyToOne
    @JoinColumn(name = "ID_EMPLEO", foreignKey = @ForeignKey(name = "FK_U_EMPLEO"))
    protected Empleo empleo;
    
    /**
     * Puesto que tiene el usuario.
     */
    @ManyToOne
    @JoinColumn(name = "ID_PUESTO", foreignKey = @ForeignKey(name = "FK_U_PUESTO"))
    private PuestoTrabajo puestoTrabajo;
    
    /**
     * Departamento que tiene el usuario.
     */
    @ManyToOne
    @JoinColumn(name = "ID_DEPARTAMENTO", foreignKey = @ForeignKey(name = "FK_U_DEPARTAMENTO"))
    protected Departamento departamento;
    
    /**
     * Nivel del usuario.
     */
    @Column(name = "nivel")
    private Integer nivel;
    
    /**
     * Clase del usuario.
     */
    @ManyToOne
    @JoinColumn(name = "ID_CLASE", foreignKey = @ForeignKey(name = "FK_U_CLASE"))
    protected ClaseUsuario claseUsuario;
    
    /**
     * Categoría del usuario.
     */
    @Column(name = "categoria", length = 20)
    protected String categoria;
    
    /**
     * Fecha de destino en el IPSS.
     */
    @Column(name = "fecha_destino_ipss")
    protected Date fechaDestinoIPSS;
    
    /**
     * Fecha de ingreso.
     */
    @Column(name = "fecha_ingreso")
    protected Date fechaIngreso;
    
    /**
     * Fecha inactivo.
     */
    @Column(name = "fecha_inactivo")
    protected Date fechaInactivo;
    
    /**
     * Constructor de usuarios provisionales principales.
     * 
     * @param name id del usuario
     * @param clave contraseña cifrada
     * @param rol rol del usuario en la aplicación
     */
    public User(String name, String clave, RoleEnum rol) {
        this.setUsername(name);
        this.setPassword(clave);
        this.setRole(rol);
        this.setEstado(EstadoEnum.ACTIVO);
        this.setNombre(PROVISIONAL);
        this.setApellido1(PROVISIONAL);
        this.setDocIdentidad("000000000T");
        this.setCorreo(username);
        this.setFechaDestinoIPSS(new Date());
        this.setNivel(0);
    }
    
    /**
     * Constructor de usuarios provisionales secundarios para la cumplimentación de cuestionarios (inicialmente
     * inactivos hasta que se les asigne un area).
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
    }
}
