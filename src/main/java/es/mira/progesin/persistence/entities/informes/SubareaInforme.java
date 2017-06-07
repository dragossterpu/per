package es.mira.progesin.persistence.entities.informes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad para la subarea de un informe.
 * 
 * @author EZENTIS
 */
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "SUBAREAS_INFORME")
public class SubareaInforme implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * ID.
     */
    @Id
    @Column(name = "ID")
    private Long id;
    
    /**
     * Descripción.
     */
    @Column(name = "DESCRIPCION", length = 1000, nullable = false)
    private String descripcion;
    
    /**
     * Area a la que pertenece.
     */
    @ManyToOne
    @JoinColumn(name = "area_id", foreignKey = @ForeignKey(name = "fk_area_informe"))
    private AreaInforme area;
    
}
