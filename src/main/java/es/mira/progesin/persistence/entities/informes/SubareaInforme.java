package es.mira.progesin.persistence.entities.informes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad para la subarea de un informe.
 * 
 * @author EZENTIS
 */
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @SequenceGenerator(name = "SEQ_SUBAREASINFORME", sequenceName = "SEQ_SUBAREASINFORME", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SUBAREASINFORME")
    private Long id;
    
    /**
     * Descripción.
     */
    @Column(name = "DESCRIPCION", length = 1000, nullable = false)
    private String descripcion;
    
    /**
     * Area a la que pertenece.
     */
    //
     @ManyToOne//(fetch = FetchType.LAZY)
     @JoinColumn(name = "area_id", foreignKey = @ForeignKey(name = "fk_area_informe"))
     private AreaInforme area;
//    @Column(name = "area_id")
//    private Long areaId;

    /**
     * Sobreescritura del método toString para que muestre sólo la descripción.
     */
    @Override
    public String toString() {
        return descripcion;
    }
    
    /**
     * Orden de la subárea dentro del área.
     */
    @Column(name = "orden")
    private Integer orden;
    
    
}
