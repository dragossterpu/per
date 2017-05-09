package es.mira.progesin.persistence.entities.cuestionarios;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad que define la clave compuesta de la entidad AreaUsuarioCuestEnv
 * 
 * @author EZENTIS
 * @see es.mira.progesin.persistence.entities.cuestionarios.AreaUsuarioCuestEnv
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
@Getter
@Setter
public class AreaUsuarioCuestEnvId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long idCuestionarioEnviado;
    
    private Long idArea;
    
}
