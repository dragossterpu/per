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

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "DEPARTAMENTO")
public class Departamento implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DEPARTAMENTO")
    @SequenceGenerator(name = "SEQ_DEPARTAMENTO", sequenceName = "SEQ_DEPARTAMENTO", allocationSize = 1)
    @Column(name = "SEQ_DEPARTAMENTO", length = 5)
    private Long id;
    
    @Column(name = "descripcion", length = 100)
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
    
    @Column(name = "username_baja")
    private String usernameBaja;
    
}
