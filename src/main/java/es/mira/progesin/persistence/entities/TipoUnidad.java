package es.mira.progesin.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad para almacenar los tipos de unidad.
 * 
 * @author EZENTIS
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@Getter
@Setter
@Entity
@Table(name = "tipos_unidad")
public class TipoUnidad implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * ID.
     */
    @Id
    @Column(name = "id", length = 10, nullable = false)
    private Long id;
    
    /**
     * Descripción del tipo.
     */
    @Column(name = "descripcion", length = 100)
    private String descripcion;
    
    /**
     * Sobreescritura del método toString para por usar el SelectItemsConverter de manera genérica, devolviendo siempre
     * la clave primaria.
     */
    @Override
    public String toString() {
        return id.toString();
    }
}
