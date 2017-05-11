package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.TipoInspeccion;

public interface ITipoInspeccionRepository extends CrudRepository<TipoInspeccion, String> {
    
    /**
     * @param codigo
     * @return
     */
    boolean existsByCodigoIgnoreCase(String codigo);
    
}
