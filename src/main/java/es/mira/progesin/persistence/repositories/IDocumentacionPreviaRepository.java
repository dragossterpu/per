package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.DocumentacionPrevia;

/**
 * Repositiorio de tipos de documentación asociados a una solicitud.
 * 
 * @author EZENTIS
 */
public interface IDocumentacionPreviaRepository extends CrudRepository<DocumentacionPrevia, Long> {
    
    /**
     * Recupera un listado de documentacion previa buscando por una solicitud.
     * 
     * @param idSolicitud id de la solicitud
     * @return listado de documentaciones resultado de la búsqueda
     */
    List<DocumentacionPrevia> findByIdSolicitud(Long idSolicitud);
    
    /**
     * Elimina todos los tipos de documentación asociados a la misma.
     * 
     * @param idSolicitud clave de la solicitud
     */
    void deleteByIdSolicitud(Long idSolicitud);
    
}
