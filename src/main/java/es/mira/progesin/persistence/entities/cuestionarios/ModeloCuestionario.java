package es.mira.progesin.persistence.entities.cuestionarios;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.mira.progesin.persistence.entities.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * Entity para un modelo de cuestionario.
 * 
 * @author EZENTIS
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
@Builder
@Getter
@Setter
@Entity
@Table(name = "modeloscuestionarios")
public class ModeloCuestionario extends AbstractEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * ID.
     */
    @Id
    @SequenceGenerator(name = "seq_modeloscuestionarios", sequenceName = "seq_modeloscuestionarios", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_modeloscuestionarios")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    /**
     * Código.
     */
    @Column(name = "codigo", nullable = false)
    private String codigo;
    
    /**
     * Descripción.
     */
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    
    /**
     * Listas de areas.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idCuestionario")
    @OrderBy("orden")
    private List<AreasCuestionario> areas;
    
    /**
     * Determina si es un modelo estandar.
     */
    @Column(name = "estandar")
    private Boolean estandar;
    
    /**
     * Sobreescritura del método toString para por usar el SelectItemsConverter de manera genérica, devolviendo siempre
     * la clave primaria.
     */
    @Override
    public String toString() {
        return id.toString();
    }
    
}
