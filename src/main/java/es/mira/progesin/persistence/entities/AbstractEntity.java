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

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements Serializable {
    
    @CreatedBy
    @Column(name = "username_alta", nullable = false)
    protected String usernameAlta;
    
    @CreatedDate
    @Column(name = "fecha_alta", nullable = false)
    protected Date fechaAlta;
    
    @Column(name = "username_baja")
    protected String usernameBaja;
    
    @Column(name = "fecha_baja")
    protected Date fechaBaja;
    
    @LastModifiedBy
    @Column(name = "username_modif")
    protected String usernameModif;
    
    @LastModifiedDate
    @Column(name = "fecha_modificacion")
    protected Date fechaModificacion;
    
}
