package es.mira.progesin.persistence.entities;

import java.io.Serializable;

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
 * POJO para los Cuerpos del estado
 * @author Ezentis
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "CUERPOSESTADO")
public class CuerpoEstado extends AbstractEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "seq_cuerpos", sequenceName = "seq_cuerpos", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cuerpos")
    @Column(name = "ID", length = 2)
    private Integer id;
    
    @Column(name = "DESCRIPCION", length = 100, nullable = false)
    private String descripcion;
    
    @Column(name = "nombre_corto", length = 10)
    private String nombreCorto;
    
}
