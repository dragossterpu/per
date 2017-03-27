package es.mira.progesin.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
@Table(name = "EMPLEO")
// @NamedQuery(name = "Empleo.findAll", query = "SELECT t FROM Empleo t")
@NamedQuery(name = "EmpleoCuerpo.find", query = "SELECT t FROM Empleo t where t.cuerpo = :cuerpoSeleccionado")
public class Empleo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EMPLEO")
    @SequenceGenerator(name = "SEQ_EMPLEO", sequenceName = "SEQ_EMPLEO", allocationSize = 1, initialValue = 1)
    @Column(name = "ID", length = 2)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_CUERPO", foreignKey = @ForeignKey(name = "FK_EM_CUERPO"))
    private CuerpoEstado cuerpo;
    
    @Column(name = "DESCRIPCION", length = 100)
    private String descripcion;
    
    @Column(name = "NOMBRE_CORTO", length = 20)
    private String nombreCorto;
    
}
