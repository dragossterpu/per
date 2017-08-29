package es.mira.progesin.persistence.entities.gd;

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
 * 
 * Entity para almacenar los tipos posibles de documentos que se manejarán desde el gestor documental.
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
@Table(name = "tipoDocumento")
public class TipoDocumento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador de la entidad.
     */
    @Id
    @Column(name = "id")
    Long id;
    
    /**
     * Nombre del tipo de documento.
     */
    @Column(name = "nombre")
    String nombre;
    
    /**
     * Sobreescritura del método toString para por usar el SelectItemsConverter de manera genérica, devolviendo siempre
     * la clave primaria.
     */
    @Override
    public String toString() {
        return id.toString();
    }
}
