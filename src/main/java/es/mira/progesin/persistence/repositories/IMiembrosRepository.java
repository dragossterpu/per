package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;

/**
 * 
 * Interfaz del repositorio de miembros de equipo.
 * 
 * @author EZENTIS
 *
 */
public interface IMiembrosRepository extends CrudRepository<Miembro, Long> {
    
    /**
     * Recupera los miembros que pertenecen a un equipo determinado.
     * 
     * @param equipo seleccionado
     * @return lista de miembros por equipo
     */
    List<Miembro> findByEquipo(Equipo equipo);
    
    /**
     * Comprueba si un usuario pertenece a algún equipo como miembro con un rol determinado.
     * 
     * @param username login del usuario
     * @param rol del miembro
     * @return verdadero o falso
     */
    boolean existsByUsuarioUsernameAndPosicion(String username, RolEquipoEnum rol);
    
    /**
     * Busca un miembro de un equipo teniendo su login.
     * 
     * @param user Usuario
     * @param equipo de busqueda
     * @return miembro encontrado si existe
     */
    
    Miembro findByUsuarioAndEquipo(User user, Equipo equipo);
    
}
