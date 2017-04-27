package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.TipoEquipo;

public interface IEquipoRepository extends CrudRepository<Equipo, Long> {
    
    boolean existsByTipoEquipo(TipoEquipo tipo);
    
    List<Equipo> findByFechaBajaIsNull();
    
}
