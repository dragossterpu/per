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
    
    Miembro save(Miembro miembro);
    
    List<Miembro> save(List<Miembro> listaMiembros);
    
    List<Miembro> findByEquipo(Equipo equipo);
}
