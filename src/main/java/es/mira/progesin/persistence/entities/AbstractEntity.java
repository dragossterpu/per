package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase abstracta para evitar la duplicidad de código en el resto de las entities.
 * 
 * @author EZENTIS
 *
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Usuario de creación.
     */
    @CreatedBy
    @Column(name = "username_alta", nullable = false)
    private String usernameAlta;
    
    /**
     * Fecha de creaión.
     */
    @CreatedDate
    @Column(name = "fecha_alta", nullable = false)
    private Date fechaAlta;
    
    /**
     * Usuario de baja.
     */
    @Column(name = "username_baja")
    private String usernameBaja;
    
    /**
     * Fecha de baja.
     */
    @Column(name = "fecha_baja")
    private Date fechaBaja;
    
    /**
     * Usuario que realiza la última modificación.
     */
    @LastModifiedBy
    @Column(name = "username_modif")
    private String usernameModif;
    
    /**
     * Fecha de última modificación.
     */
    @LastModifiedDate
    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;
    
}
