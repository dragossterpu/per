package es.mira.progesin.persistence.entities.cuestionarios;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity para guardar los tipos de respuestas existentes en un cuestionario.
 * 
 * @author EZENTIS
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "CONFIG_RESPUESTAS_CUESTIONARIO")
public class ConfiguracionRespuestasCuestionario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador de la configuración respuestas.
     */
    @EmbeddedId
    ConfiguracionRespuestasCuestionarioId config;
    
}
