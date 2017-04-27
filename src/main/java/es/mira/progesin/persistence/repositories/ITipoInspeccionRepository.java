package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.TipoInspeccion;

public interface ITipoInspeccionRepository extends CrudRepository<TipoInspeccion, String> {
    /**
     * Recupera todos los tipos de inspección que no están dados de baja lógica
     * 
     * @return lista de tipos
     */
    List<TipoInspeccion> findByFechaBajaIsNull();
    
    /**
     * @param codigo
     * @return
     */
    boolean existsByCodigoIgnoreCase(String codigo);
    
}
