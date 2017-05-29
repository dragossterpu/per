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
import lombok.ToString;

/**
 * Entidad para almacenar los modelos de inspección.
 * 
 * @author EZENTIS
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "codigo")
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "TIPOS_INSPECCION")
public class TipoInspeccion extends AbstractEntity implements Serializable, Comparable<TipoInspeccion> {
    private static final long serialVersionUID = 1L;
    
    /**
     * Código del tipo (primary key).
     */
    @Id
    @Column(length = 10)
    private String codigo;
    
    /**
     * Descripción del tipo.
     */
    @Column(length = 100)
    private String descripcion;
    
    /**
     * Sobreescritura del método para comparar tipos y así poder ordenar colecciones.
     */
    @Override
    public int compareTo(TipoInspeccion o) {
        return this.descripcion.compareTo(o.descripcion);
    }
}
