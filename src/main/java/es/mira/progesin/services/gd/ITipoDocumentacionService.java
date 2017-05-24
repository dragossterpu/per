package es.mira.progesin.services.gd;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;

/**
 * Servicio de tipos de documentación previa
 * 
 * @author EZENTIS
 *
 */
public interface ITipoDocumentacionService {
    
    /**
     * Devuelve un listado con todos los tipos de documentación previa
     * 
     * @return lista de tipos de documentación
     */
    List<TipoDocumentacion> findAll();
    
    /**
     * Elimina un tipo de documentación previa
     * 
     * @param id identificador de tipo de documentación
     */
    void delete(Long id);
    
    /**
     * Guarda en base de datos un tipo de documentación previa
     * 
     * @param entity tipo a guardar
     * @return tipo guardado
     */
    TipoDocumentacion save(TipoDocumentacion entity);
    
    /**
     * Devuelve un listado con todos los tipos de documentación previa
     * 
     * @param ambito ámbito del que se buscan los tipos de documentación
     * @return lista de tipos de documentación
     */
    
    List<TipoDocumentacion> findByAmbito(AmbitoInspeccionEnum ambito);
    
    /**
     * Guarda una entidad tipo DocumentacionPrevia en base de datos
     * 
     * @param entity documentación a guardar
     * @return documentación guardada
     */
    DocumentacionPrevia save(DocumentacionPrevia entity);
    
    /**
     * Recupera un listado de documentacione previa buscando por una solicitud
     * 
     * @param idSolicitud id de la solicitud
     * @return listado de documentaciones resultado de la búsqueda
     */
    List<DocumentacionPrevia> findByIdSolicitud(Long idSolicitud);
    
}
