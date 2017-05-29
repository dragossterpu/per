package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * Entity creada para almacenar las alertas y notificaciones asignadas a un usuario.
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
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "AlertasNotificacionesUsuario")
@IdClass(AlertasNotificacionesUsuario.class)
public class AlertasNotificacionesUsuario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Tipo de mensaje (Alerta o Notificación).
     */
    @Id
    @Column(name = "TIPO_MENSAJE", length = 50)
    @Enumerated(EnumType.STRING)
    private TipoMensajeEnum tipo;
    
    /**
     * Username del usuario que tiene asignada la alerta/notificación.
     */
    @Id
    @Column(name = "USUARIO")
    private String usuario;
    
    /**
     * Id de la alerta/notificación.
     */
    @Id
    @Column(name = "ID_MENSAJE", length = 50)
    private Long idMensaje;
    
    /**
     * Sección a la que pertenece la alerta/notificación.
     */
    @Column(name = "NOMBRE_SECCION", length = 50)
    private String nombreSeccion;
    
    /**
     * Fecha de alta de la alerta/notificación.
     */
    @Column(name = "FECHA_ALTA")
    private Date fechaAlta;
}
