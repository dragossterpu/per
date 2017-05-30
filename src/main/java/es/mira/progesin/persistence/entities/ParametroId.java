package es.mira.progesin.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad para los datos paramétricos de la aplicación.
 * 
 * @author EZENTIS
 *
 */
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class ParametroId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Sección del parámetro.
     */
    String seccion;
    
    /**
     * Clave del parámetro.
     */
    String clave;
    
    /**
     * Valor del parámetro.
     */
    @Column(length = 4000)
    String valor;
}
