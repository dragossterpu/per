package es.mira.progesin.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity creada para almacenar los Cuerpos del estado.
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
@Table(name = "CUERPOSESTADO")
public class CuerpoEstado extends AbstractEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * ID.
     */
    @Id
    @SequenceGenerator(name = "seq_cuerpos", sequenceName = "seq_cuerpos", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cuerpos")
    @Column(name = "ID", length = 2)
    private Integer id;
    
    /**
     * Descripción.
     */
    @Column(name = "DESCRIPCION", length = 100, nullable = false)
    private String descripcion;
    
    /**
     * Nombre corto.
     */
    @Column(name = "nombre_corto", length = 10)
    private String nombreCorto;
    
    /**
     * Sobreescritura del método toString para por usar el SelectItemsConverter de manera genérica, devolviendo siempre
     * la clave primaria.
     */
    @Override
    public String toString() {
        return id.toString();
    }
    
}
