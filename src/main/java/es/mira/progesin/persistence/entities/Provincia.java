package es.mira.progesin.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * Entidad para almacenar una provincia.
 * 
 * @author EZENTIS
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Getter
@Setter
@Entity
@Table(name = "provincias")
public class Provincia implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Código. Identificador de la provincia
     */
    @Id
    @Column(name = "codigo", length = 3)
    private String codigo;
    
    /**
     * Nombre de la provincia.
     */
    @Column(name = "nombre", length = 100)
    private String nombre;
    
    /**
     * Código MN de la provincia.
     */
    @Column(name = "codigo_mn", length = 10)
    private String codigoMN;
    
    /**
     * Sobreescritura del método toString para por usar el SelectItemsConverter de manera genérica, devolviendo siempre
     * la clave primaria.
     */
    @Override
    public String toString() {
        return codigo;
    }
}
