package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enumerado para los diferentes estados por los que pasa un Informe.
 * 
 * @author EZENTIS
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum InformeEnum {
    /**
     * Informe creado.
     */
    CREADO("Creado"),
    /**
     * Informe finalizado.
     */
    FINALIZADO("Finalizado"),
    /**
     * Informe anulado.
     */
    ANULADA("Anulado");
    /**
     * Descripción de los estados de Informe.
     */
    private String descripcion;
}
