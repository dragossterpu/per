package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * Entity creada para almacenar las alertas.
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
@Table(name = "ALERTAS")
public class Alerta implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador de la entidad. Generado por secuencia.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ALERTA")
    @SequenceGenerator(name = "SEQ_ALERTA", sequenceName = "SEQ_ALERTA", allocationSize = 1)
    @Column(name = "ID_ALERTA", length = 5)
    private Long idAlerta;
    
    /**
     * Nombre de la sección donde se produce la alerta.
     */
    @Column(name = "NOMBRE_SECCION", length = 50)
    private String nombreSeccion;
    
    /**
     * Descripción de la alerta.
     */
    @Column(name = "DESCRIPCION", length = 2000)
    private String descripcion;
    
    /**
     * Fecha en que se produce la alerta.
     */
    @CreatedDate
    @Column(name = "FECHA_REGISTRO")
    private Date fechaAlta;
    
    /**
     * Usuario que registra la alerta.
     */
    @CreatedBy
    @Column(name = "USUARIO_REGISTRO")
    private String usernameAlerta;
    
    /**
     * Fecha de baja de la alerta.
     */
    @Column(name = "FECHA_BAJA")
    private Date fechaBaja;
    
    /**
     * Usuario que da de baja la alerta.
     */
    @Column(name = "USUARIO_BAJA")
    private String usernameBaja;
    
}
