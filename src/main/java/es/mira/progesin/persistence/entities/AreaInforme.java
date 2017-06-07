package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@ToString
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
    private Long id;
    
    /**
     * Descripción.
     */
    @Column(name = "DESCRIPCION", length = 1000, nullable = false)
    private String descripcion;
    
    /**
     * Modelo al que pertenece el area.
     */
    @ManyToOne
    @JoinColumn(name = "modelo_informe", foreignKey = @ForeignKey(name = "fk_modelo_informe"))
    private ModeloInforme modeloInforme;
    
    /**
     * Lista de subáreas.
     */
    @OneToMany(mappedBy = "area")
    private List<SubareaInforme> subareas;
}
