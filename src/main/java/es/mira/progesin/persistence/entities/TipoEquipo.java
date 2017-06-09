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
 * Entidad para los tipos de equipo.
 * 
 * @author EZENTIS
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "TIPO_EQUIPO")
public class TipoEquipo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * ID.
     */
    @Id
    @SequenceGenerator(name = "seq_tipo_equipo", sequenceName = "seq_tipo_equipo", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_equipo")
    @Column(name = "ID")
    private Long id;
    
    /**
     * Código.
     */
    @Column(name = "CODIGO", length = 5, unique = true)
    private String codigo;
    
    /**
     * Descripción.
     */
    @Column(name = "DESCRIPCION", length = 100)
    private String descripcion;
    
}
