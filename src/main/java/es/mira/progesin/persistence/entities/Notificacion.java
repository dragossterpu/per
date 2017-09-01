package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * Entity creada para almacenar las notificaciones.
 * 
 * @author EZENTIS
 * 
 */

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "NOTIFICACIONES")
public class Notificacion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador de la notificación, generada por medio de una secuencia.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NOTIFICACIONES")
    @SequenceGenerator(name = "SEQ_NOTIFICACIONES", sequenceName = "SEQ_NOTIFICACIONES", allocationSize = 1)
    @Column(name = "ID_NOTIFICACION", length = 15)
    private Long idNotificacion;
    
    /**
     * Descripción de la notificación.
     */
    @Column(name = "DESCRIPCION", length = 2000)
    private String descripcion;
    
    /**
     * Fecha en la que se da de alta la notificación.
     */
    @Column(name = "FECHA_NOTIFICACION", nullable = false)
    private Date fechaAlta;
    
    /**
     * Username del usuario que registra la notificación.
     */
    @Column(name = "USUARIO_REGISTRO", nullable = false)
    private String usernameNotificacion;
    
    /**
     * Tipo de la notificación.
     */
    @Column(name = "TIPO_NOTIFICACION", length = 20)
    private String tipoNotificacion;
    
    /**
     * Sección en la que se produce la notificación.
     */
    @Column(name = "NOMBRE_SECCION", length = 50)
    private String nombreSeccion;
    
    /**
     * Fecha en la que se da de baja la notificación.
     */
    @Column(name = "FECHA_BAJA")
    private Date fechaBaja;
    
    /**
     * Username del usuario que da de baja la notificación.
     */
    @Column(name = "USUARIO_BAJA")
    private String usernameBaja;
    
}
