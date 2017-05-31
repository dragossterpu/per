package es.mira.progesin.persistence.repositories.gd;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;

/**
 * Repositorio de documentos asociados de solicitudes de documentación previa.
 * 
 * @author EZENTIS
 *
 */
public interface IGestDocSolicitudDocumentacionRepository extends CrudRepository<GestDocSolicitudDocumentacion, Long> {
    /**
     * Busca la lista solicitudes con documentos asociados por id de solicitud.
     * 
     * @param idSolicitud identificador de la solicitud
     * @return lista solicitudes con documentos asociados
     */
    List<GestDocSolicitudDocumentacion> findByIdSolicitud(Long idSolicitud);
    
    /**
     * Busca la lista de solicitudes con documentos asociados filtrando por id de documento.
     * 
     * @param idDocumento identificador del documento
     * @return lista de solicitudes con documentos asociados
     */
    List<GestDocSolicitudDocumentacion> findByIdDocumento(Long idDocumento);
}
