package es.mira.progesin.persistence.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clave primera de una respuesta del informe.
 * 
 * @author EZENTIS
 *
 */
@EqualsAndHashCode(of = { "informe", "subarea" })
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RespuestaInformeId implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Informe informe;
    
    private SubareaInforme subarea;
    
}
