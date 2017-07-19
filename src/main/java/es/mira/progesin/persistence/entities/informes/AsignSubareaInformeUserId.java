/**
 * 
 */
package es.mira.progesin.persistence.entities.informes;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad que define la clave compuesta de la entidad AsignSubareaInformeUser.
 * 
 * @author EZENTIS
 * @see es.mira.progesin.persistence.entities.informes.AsignSubareaInformeUser
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
@Getter
@Setter
public class AsignSubareaInformeUserId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador del subarea.
     */
    private Long subarea;
    
    /**
     * Informe.
     */
    private Long informe;
    
}
