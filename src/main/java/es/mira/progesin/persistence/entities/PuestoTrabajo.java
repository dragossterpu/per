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
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad para un Puesto de trabajo.
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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "PUESTOSTRABAJO")
public class PuestoTrabajo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PUESTO_TRABAJO")
    @SequenceGenerator(name = "SEQ_PUESTO_TRABAJO", sequenceName = "SEQ_PUESTO_TRABAJO", allocationSize = 1)
    @Column(name = "ID", length = 2)
    private Long id;
    
    @Column(name = "DESCRIPCION", length = 100)
    private String descripcion;
    
    @CreatedDate
    @Column(name = "fecha_alta", nullable = false)
    private Date fechaAlta;
    
    @CreatedBy
    @Column(name = "username_alta", nullable = false)
    private String usernameAlta;
    
    @LastModifiedDate
    @Column(name = "fecha_modif")
    private Date fechaModif;
    
    @LastModifiedBy
    @Column(name = "username_modif")
    private String usernameModif;
    
    @Column(name = "fecha_baja")
    private Date fechaBaja;
    
    @Column(name = "username_baja", length = 12)
    private String usernameBaja;
    
}
