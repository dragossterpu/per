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
    private String username;
    
    /**
     * Contraseña del usuario.
     */
    @Column(name = "password", length = 100, nullable = false)
    private String password;
    
    /**
     * Estado: activo o inactivo.
     */
    @Column(name = "estado", length = 8, nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoEnum estado;
    
    /**
     * Nombre del usuario.
     */
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;
    
    /**
     * Primer apellido.
     */
    @Column(name = "prim_apellido", length = 50, nullable = false)
    private String apellido1;
    
    /**
     * Segundo apellido.
     */
    @Column(name = "segundo_apellido", length = 50)
    private String apellido2;
    
    /**
     * Documento de identidad.
     */
    @Column(name = "doc_identidad", length = 10, nullable = false)
    private String docIdentidad;
    
    /**
     * Número de identificación personal.
     */
    @Column(name = "num_identificacion", length = 20)
    private String numIdentificacion;
    
    /**
     * Teléfono del usuario.
     */
    @Column(name = "telefono", length = 12)
    private String telefono;
    
    /**
     * Teléfono móvil oficial.
     */
    @Column(name = "tfno_movil_oficial", length = 12)
    private String telefonoMovilOficial;
    
    /**
     * Teléfono móvil particular.
     */
    @Column(name = "tfno_movil_particular", length = 12)
    private String telefonoMovilParticular;
    
    /**
     * Correo electronico del usuario.
     */
    @Column(name = "correo", length = 100, nullable = false)
    private String correo;
    
    /**
     * Rol del usuario.
     */
    @Column(name = "role", length = 25, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    
    /**
     * Despacho del usuario.
     */
    @Column(name = "despacho", length = 20)
    private String despacho;
    
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
    private Empleo empleo;
    
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
    private Departamento departamento;
    
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
    private ClaseUsuario claseUsuario;
    
    /**
     * Categoría del usuario.
     */
    @Column(name = "categoria", length = 20)
    private String categoria;
    
    /**
     * Fecha de destino en el IPSS.
     */
    @Column(name = "fecha_destino_ipss")
    private Date fechaDestinoIPSS;
    
    /**
     * Fecha de ingreso.
     */
    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;
    
    /**
     * Fecha inactivo.
     */
    @Column(name = "fecha_inactivo")
    private Date fechaInactivo;
    
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
     * @param name id del usuario
     * @param clave contraseña cifrada
     * @param rol rol del usuario en la aplicación
     * @param correoP correo de envío del cuestionario
     */
    public User(String name, String clave, RoleEnum rol, String correoP) {
        this.setUsername(name);
        this.setPassword(clave);
        this.setRole(rol);
        this.setEstado(EstadoEnum.INACTIVO);
        this.setNombre(PROVISIONAL);
        this.setApellido1(PROVISIONAL);
        this.setDocIdentidad("000000000T");
        this.setCorreo(correoP);
        this.setFechaDestinoIPSS(new Date());
        this.setNivel(0);
    }
    
    /**
     * Devuelve el nombre completo del usuario.
     * 
     * @return Cadena formada por la concatenación de nombre y apellidos del usuario
     */
    public String getNombreCompleto() {
        StringBuilder nombreCompleto = new StringBuilder();
        nombreCompleto.append(nombre);
        nombreCompleto.append(' ');
        nombreCompleto.append(apellido1);
        if (apellido2 != null) {
            nombreCompleto.append(' ');
            nombreCompleto.append(apellido2);
        }
        return nombreCompleto.toString();
    }
}
