package es.mira.progesin.persistence.entities.enums;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Define los roles del sistema. Debido al uso de Spring Security version 4 TODOS los roles deben empezar por "ROLE_",
 * de lo contrario las funciones en JSF/Primefaces isUserInRole, ifGranted, etc. devuelven siempre false
 * 
 * @author Ezentis
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum RoleEnum {
    ROLE_ADMIN("Administrador", false), ROLE_JEFE_INSPECCIONES("Jefe de inspecciones", false), ROLE_EQUIPO_INSPECCIONES(
            "Equipo de inspecciones",
            false), ROLE_SERVICIO_APOYO("Servicio de apoyo", false), ROLE_GABINETE("Gabinete de estudio y análisis",
                    false), ROLE_PROV_SOLICITUD("Provisional vista documentación previa",
                            true), ROLE_PROV_CUESTIONARIO("Provisional vista cuestionario", true);
    
    private String descripcion;
    
    private boolean prov;
    
    /**
     * @return Devuelve un listado con los roles provisionales que permiten acceder a la aplicación a usuarios externos
     * a IPSS
     */
    public static List<RoleEnum> getRolesProv() {
        List<RoleEnum> rolesProv = new ArrayList<>();
        for (RoleEnum rol : RoleEnum.values()) {
            if (rol.isProv())
                rolesProv.add(rol);
        }
        return rolesProv;
    }
    
    /**
     * @return Nombre del enum sin el prefijo "ROLE_"
     */
    public String getNombre() {
        return this.name().substring(5);
    }
}
