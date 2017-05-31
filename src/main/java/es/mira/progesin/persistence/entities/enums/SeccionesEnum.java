package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enumerado para los diferentes tipos de secciones utilizadas para la gestión de mensajes.
 * 
 * @author EZENTIS
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SeccionesEnum {
    /**
     * Sección de administración.
     */
    ADMINISTRACION("ADMINISTRACIÓN"),
    /**
     * Sección de documentación de solicitud de documenatción previa.
     */
    DOCUMENTACION("SOLICITUD DOC. PREVIA"),
    /**
     * Sección de cuestionarios.
     */
    CUESTIONARIO("CUESTIONARIOS"),
    /**
     * Sección de gestor documental.
     */
    GESTOR("GESTOR DOCUMENTAL"),
    /**
     * Sección de guías.
     */
    GUIAS("GUÍAS"),
    /**
     * Sección de inspecciones.
     */
    INSPECCION("INSPECCIONES"),
    /**
     * Sección de informes.
     */
    INFORMES("INFORMES"),
    /**
     * Sección de login.
     */
    LOGIN("LOGIN"),
    /**
     * Sección de usuarios.
     */
    USUARIOS("USUARIOS"),
    /**
     * Sección de alertas.
     */
    ALERTAS("ALERTAS"),
    /**
     * Sección de notificaciones.
     */
    NOTIFICACIONES("NOTIFICACIONES"),
    /**
     * Sección de sugerencias.
     */
    NOMBRESECCIONSUGERENCIAS("Sugerencias de mejora");
    
    /**
     * Descripción de la sección.
     */
    private String descripcion;
    
}
