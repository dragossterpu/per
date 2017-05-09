package es.mira.progesin.persistence.entities.cuestionarios;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Definición de la clave primaria que se usará en la entity ConfiguracionRespuestasCuestionario
 * 
 * @author EZENTIS
 *
 */
@EqualsAndHashCode()
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class ConfiguracionRespuestasCuestionarioId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    String seccion;
    
    String clave;
    
    String valor;
}
