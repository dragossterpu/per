package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;

public interface IMiembrosRepository extends CrudRepository<Miembro, Long> {
    
    List<Miembro> findByEquipo(Equipo equipo);
    
    // Comprueba si un usuario es jefe de algún equipo de inspecciones
    @Query("SELECT case when count(m)>0 then true else false end FROM Miembro m WHERE m.username=:username AND m.posicion='JEFE_EQUIPO' ")
    boolean esJefeEquipo(@Param("username") String username);
    
}
