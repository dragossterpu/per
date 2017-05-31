package es.mira.progesin.services.gd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.persistence.repositories.IDocumentacionPreviaRepository;
import es.mira.progesin.persistence.repositories.gd.ITipoDocumentacionRepository;

/**
 * 
 * Implementación del servivio de Tipos de documentación previa.
 * 
 * @author EZENTIS
 *
 */
@Service
public class TipoDocumentacionService implements ITipoDocumentacionService {
    
    /**
     * Variable utilizada para inyectar el repositorio de tipo de documentación.
     * 
     */
    @Autowired
    ITipoDocumentacionRepository tipoDocumentacionRepository;
    
    /**
     * Variable utilizada para inyectar el repositorio de documentación previa.
     * 
     */
    @Autowired
    IDocumentacionPreviaRepository documentacionPreviaRepository;
    
    /**
     * Devuelve un listado con todos los tipos de documentación previa.
     * 
     * @return lista de tipos de documentación
     */
    @Override
    public List<TipoDocumentacion> findAll() {
        return (List<TipoDocumentacion>) tipoDocumentacionRepository.findAll();
    }
    
    /**
     * Elimina un tipo de documentación previa.
     * 
     * @param id identificador de tipo de documentación
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        tipoDocumentacionRepository.delete(id);
    }
    
    /**
     * Guarda en base de datos un tipo de documentación previa.
     * 
     * @param entity tipo a guardar
     * @return tipo guardado
     */
    @Override
    @Transactional(readOnly = false)
    public TipoDocumentacion save(TipoDocumentacion entity) {
        return tipoDocumentacionRepository.save(entity);
    }
    
    /**
     * Devuelve un listado con todos los tipos de documentación previa.
     * 
     * @param ambito ámbito del que se buscan los tipos de documentación
     * @return lista de tipos de documentación
     */
    @Override
    public List<TipoDocumentacion> findByAmbito(AmbitoInspeccionEnum ambito) {
        return tipoDocumentacionRepository.findByAmbito(ambito);
    }
    
    /**
     * Guarda una entidad tipo DocumentacionPrevia en base de datos.
     * 
     * @param entity documentación a guardar
     * @return documentación guardada
     */
    @Override
    @Transactional(readOnly = false)
    public DocumentacionPrevia save(DocumentacionPrevia entity) {
        return documentacionPreviaRepository.save(entity);
    }
    
    /**
     * Recupera un listado de documentacione previa buscando por una solicitud.
     * 
     * @param idSolicitud id de la solicitud
     * @return listado de documentaciones resultado de la búsqueda
     */
    @Override
    public List<DocumentacionPrevia> findByIdSolicitud(Long idSolicitud) {
        return documentacionPreviaRepository.findByIdSolicitud(idSolicitud);
    }
    
}
