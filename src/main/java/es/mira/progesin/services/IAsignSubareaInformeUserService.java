package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.informes.AsignSubareaInformeUser;
import es.mira.progesin.persistence.entities.informes.Informe;

/**
 * Interfaz del servicio para la gestión de la asignación de subáreas de un informe para que las completen inspectores.
 * 
 * @author EZENTIS
 */
public interface IAsignSubareaInformeUserService {
    
    /**
     * Guarda una asignacion de subárea de un informe a un inspector.
     * 
     * @param asignSubareaInformeUser asignación
     * @return lista de asignaciones con id
     */
    AsignSubareaInformeUser save(AsignSubareaInformeUser asignSubareaInformeUser);
    
    /**
     * Busca las asignaciones de subáreas de un informe a inspectores.
     * 
     * @param informe informe en curso
     * @return lista de asignaciones
     */
    List<AsignSubareaInformeUser> findByInforme(Informe informe);
    
    /**
     * Borra todas las asignaciones de subáreas de un informe.
     * 
     * @param informe informe en curso
     */
    void deleteByInforme(Informe informe);
    
    /**
     * Borra las asignaciones de subáreas de un informe a un inspector.
     * 
     * @param informe informe en curso
     * @param usuario inspector
     */
    void deleteByInformeAndUser(Informe informe, User usuario);
    
}
