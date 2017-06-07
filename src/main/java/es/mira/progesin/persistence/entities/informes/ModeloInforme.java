package es.mira.progesin.persistence.entities.informes;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity asociada a un modelo de informe.
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
@Table(name = "MODELOS_INFORME")
@NamedEntityGraph(name = "ModeloInforme.areas", attributeNodes = @NamedAttributeNode("areas"))
public class ModeloInforme implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador del modelo de informe. Generada por secuencia.
     */
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    
    /**
     * Nombre del modelo.
     */
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    
    /**
     * Areas que conforman el modelo de informe.
     */
    @OneToMany(mappedBy = "modeloInforme", fetch = FetchType.LAZY)
    private List<AreaInforme> areas;
}