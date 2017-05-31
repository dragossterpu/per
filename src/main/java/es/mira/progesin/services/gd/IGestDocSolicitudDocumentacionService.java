package es.mira.progesin.services.gd;

import java.util.List;

import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;

/**
 * 
 * Implementación del servivio de Tipos de GestDocSolicitudDocumentacion.
 * 
 * @author EZENTIS
 *
 */
public interface IGestDocSolicitudDocumentacionService {
    
    /**
     * Guarda un documento.
     * 
     * @param documento a guaradar
     * @return documento guardado
     */
    GestDocSolicitudDocumentacion save(GestDocSolicitudDocumentacion documento);
    
    /**
     * Busca una lista de documentos filtrando por id.
     * 
     * @param idSolicitud filtro
     * @return lista documentos
     */
    List<GestDocSolicitudDocumentacion> findByIdSolicitud(Long idSolicitud);
    
    /**
     * Borra un documento.
     * 
     * @param documento a borrar
     */
    void delete(GestDocSolicitudDocumentacion documento);
    
}
