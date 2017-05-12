package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;

/**
 * 
 * Interfaz del repositorio de miembros de equipo
 * 
 * @author Ezentis
 *
 */
public interface IMiembrosRepository extends CrudRepository<Miembro, Long> {
    
    /**
     * Recupera los miembros que pertenecen a un equipo determinado
     * 
     * @author Ezentis
     * @param equipo
     * @return lista de miembros
     */
    List<Miembro> findByEquipo(Equipo equipo);
    
    /**
     * Comprueba si un usuario pertenece a algún equipo como miembro con la posición indicada (rol de equipo)
     * 
     * @param username
     * @param rol del miembro
     * @return verdadero o falso
     */
    boolean existsByUsernameAndPosicion(String username, RolEquipoEnum rol);
    
    Miembro findByUsername(String username);
    
}
