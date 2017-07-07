package es.mira.progesin.persistence.entities.informes;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad para almacenar el área de un informe.
 * 
 * @author EZENTIS
 *
 */
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "AREAS_INFORME")
public class AreaInforme implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * ID.
     */
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_AREASINFORME", sequenceName = "SEQ_AREASINFORME", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AREASINFORME")
    private Long id;
    
    /**
     * Descripción.
     */
    @Column(name = "DESCRIPCION", length = 1000, nullable = false)
    private String descripcion;
    
    /**
     * Modelo al que pertenece el area.
     */
    // @ManyToOne
    // @JoinColumn(name = "modelo_informe_id", foreignKey = @ForeignKey(name = "fk_area_modeloinf"))
    @Column(name = "modelo_informe_id")
    private Long modeloInformeId;
    
    /**
     * Lista de subáreas.
     */
    // @OneToMany(mappedBy = "area", fetch = FetchType.EAGER)
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "area_id")
    private List<SubareaInforme> subareas;
    
    /**
     * Orden en el que aparecerán las áreas.
     */
    @Column(name = "orden")
    private Integer orden;

    /**
     * Sobreescritura del método toString para que muestre sólo la descripción.
     */
    @Override
    public String toString() {
        return descripcion;
    }
    
    
}
