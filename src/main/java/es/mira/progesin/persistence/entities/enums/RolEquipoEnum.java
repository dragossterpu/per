package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enumerado para gestionar el rol de un miembro del equipo.
 * 
 * @author EZENTIS
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum RolEquipoEnum {
    /**
     * Jefe del equipo.
     */
    
    JEFE_EQUIPO("Jefe de equipo"),
    /**
     * Componente del equipo.
     */
    
    MIEMBRO("Componente"),
    /**
     * Colaborador del equipo.
     */
    
    COLABORADOR("Colaborador");
    
    /**
     * Descripción del rol.
     */
    private String descripcion;
}
