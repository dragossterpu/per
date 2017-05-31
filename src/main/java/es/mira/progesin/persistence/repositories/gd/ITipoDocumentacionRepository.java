package es.mira.progesin.persistence.repositories.gd;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;

/**
 * Repositorio del tipo de documentación.
 * 
 * @author EZENTIS
 *
 */
public interface ITipoDocumentacionRepository extends CrudRepository<TipoDocumentacion, Long> {
    
    /**
     * @param ambito pasado pro parámetro
     * @return lista de tipos de documentación por ámbito
     */
    List<TipoDocumentacion> findByAmbito(AmbitoInspeccionEnum ambito);
    
}
