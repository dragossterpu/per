package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.TipoEquipo;

/**
 * @author Ezentis
 *
 */
public interface IEquipoRepository extends CrudRepository<Equipo, Long> {
    
    /**
     * @param tipo de equipo
     * @return true o false dependiendo de si existe el equipo
     */
    public boolean existsByTipoEquipo(TipoEquipo tipo);
    
    /**
     *
     * @return devuelve todos los equipos que no se encuentran en situación de baja lógica
     */
    public List<Equipo> findByFechaBajaIsNull();
    
    /**
     * Búsqueda de equipos por nombre de usuario.
     * 
     * @param paramLogin nombre usuario (username)
     * @return listado de equipos a los que pertenece el usuario
     */
    @Query("SELECT e FROM Equipo e, Miembro m WHERE m.equipo = e.id AND upper(m.usuario.username) LIKE upper(:infoLogin))")
    public List<Equipo> buscarEquiposByUsername(@Param("infoLogin") String paramLogin);
    
    /**
     * Búsqueda de equipos por nombre de usuario.
     * 
     * @param paramLogin nombre usuario (username)
     * @return listado de equipos a los que pertenece el usuario
     */
    @Query("SELECT e FROM Equipo e, Miembro m WHERE m.equipo = e.id AND e.fechaBaja = null AND m.posicion = 'JEFE_EQUIPO'  AND upper(m.usuario.username) LIKE upper(:infoLogin))")
    public Equipo buscarEquipoByJefe(@Param("infoLogin") String paramLogin);
    
}
