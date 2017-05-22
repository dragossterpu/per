package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;

/**
 * @author Ezentis
 *
 */

public interface IMiembroService {
    
    /**
     * Devuelve un miembro con un login especifico
     * 
     * @param username
     * @return devuelve un miembro
     */
    Miembro buscaMiembroByUsername(String username);
    
    /**
     * Guarda la información de un miembro de equipo en la bdd.
     * 
     * @author Ezentis
     * @param miembro
     * @return miembro con id
     */
    Miembro save(Miembro miembro);
    
    /**
     * Recupera los miembros que pertenecen a un equipo determinado
     * 
     * @author Ezentis
     * @param equipo
     * @return lista de miembros
     */
    List<Miembro> findByEquipo(Equipo equipo);
    
    /**
     * Comprueba si un usuario es jefe de algún equipo de inspecciones
     * 
     * @param username
     * @return verdadero o falso
     */
    boolean esJefeEquipo(String username);
    
}
