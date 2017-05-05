package es.mira.progesin.services;

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
}
