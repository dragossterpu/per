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
    boolean existsByTipoEquipo(TipoEquipo tipo);
    
    /**
     * @return devuelve todos los equipos que no se encuentran en situación de baja lógica
     */
    List<Equipo> findByFechaBajaIsNull();
    
    @Query("SELECT e FROM Equipo e, Miembro m WHERE m.equipo = e.id AND upper(m.username) LIKE upper(:infoLogin))")
    public abstract List<Equipo> buscarEquiposByUsername(@Param("infoLogin") String paramLogin);
    
}
