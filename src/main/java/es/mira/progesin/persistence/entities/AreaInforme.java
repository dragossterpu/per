package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad para almacenar el área de un informe.
 * 
 * @author EZENTIS
 *
 */
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
     * Lista de subáreas.
     */
    @OneToMany(mappedBy = "area")
    private List<SubareaInforme> subareas;
}
