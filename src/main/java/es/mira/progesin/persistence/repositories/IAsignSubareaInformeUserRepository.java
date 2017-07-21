package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.informes.AsignSubareaInformeUser;
import es.mira.progesin.persistence.entities.informes.AsignSubareaInformeUserId;
import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;

/**
 * Repositorio de asignación de subáreas de un informe a inspectores.
 * 
 * @author EZENTIS
 */
public interface IAsignSubareaInformeUserRepository
        extends CrudRepository<AsignSubareaInformeUser, AsignSubareaInformeUserId> {
    
    /**
     * Busca las asignaciones de subáreas a inspectores que realizan el informe.
     * 
     * @param informe informe al que pertenecen las asignaciones
     * @return lista de asignaciones
     */
    List<AsignSubareaInformeUser> findByInforme(Informe informe);
    
    /**
     * Busca la asignación de un subárea de un informe.
     * 
     * @param subarea subárea del informe
     * @param informe informe en curso
     * @return asignación si es que existe o null
     */
    AsignSubareaInformeUser findBySubareaAndInforme(SubareaInforme subarea, Informe informe);
    
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
    
    /**
     * Borra la asignación de un subárea de un informe a un inspector.
     * 
     * @param subarea subárea del informe
     * @param informe informe en curso
     */
    void deleteBySubareaAndInforme(SubareaInforme subarea, Informe informe);
    
}
