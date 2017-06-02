package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.web.beans.SolicitudDocPreviaBusqueda;

/**
 * Interfaz del servicio de solicitudes de documentación.
 * 
 * @author EZENTIS
 */
public interface ISolicitudDocumentacionService {
    
    /**
     * Guarda la información de una solicitud en la bdd.
     * 
     * @param solicitudDocumentacionPrevia solicitud creada o modificada
     * @return solicitud
     */
    SolicitudDocumentacionPrevia save(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia);
    
    /**
     * Recupera todas las solicitudes existentes.
     * 
     * @return lista
     */
    List<SolicitudDocumentacionPrevia> findAll();
    
    /**
     * Recupera la solicitud no finalizada perteneciente a un destinatario (no puede haber más de una).
     * 
     * @param correo destinatario de la solicitud
     * @return solicitud
     */
    SolicitudDocumentacionPrevia findNoFinalizadaPorCorreoDestinatario(String correo);
    
    /**
     * Recupera la solicitud ya enviada pero sin finalizar perteneciente a un destinatario (no puede haber más de una).
     * 
     * @param correo destinatario de la solicitud
     * @return solicitud
     */
    SolicitudDocumentacionPrevia findEnviadaNoFinalizadaPorCorreoDestinatario(String correo);
    
    /**
     * Elimina una solicitud a partir de su id.
     * 
     * @param id clave de la solicitud
     */
    void delete(Long id);
    
    /**
     * Guarda los datos de una solicitud y crea el usuario provisional que debe cumplimentarla una vez enviada.
     * 
     * @param solicitudDocumentacionPrevia solicitud modificada
     * @param usuarioProv objeto usuario
     */
    void transaccSaveCreaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia, User usuarioProv);
    
    /**
     * Guarda los datos de una solicitud y elimina el usuario provisional que la ha cumplimentado una vez finalizada o
     * anulada.
     * 
     * @param solicitudDocumentacionPrevia solicitud modificada
     * @param usuarioProv nombre usuario
     */
    void transaccSaveElimUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia, String usuarioProv);
    
    /**
     * Guarda los datos de una solicitud e inactiva el usuario provisional que la ha cumplimentado.
     * 
     * @param solicitudDocumentacionPrevia solicitud modificada
     * @param usuarioProv nombre usuario
     */
    void transaccSaveInactivaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia, String usuarioProv);
    
    /**
     * Guarda los datos de una solicitud y activa el usuario provisional que debe cumplimentarla de nuevo en caso de no
     * conformidad.
     * 
     * @param solicitudDocumentacionPrevia solicitud modificada
     * @param usuarioProv nombre usuario
     */
    void transaccSaveActivaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia, String usuarioProv);
    
    /**
     * Recupera las solicitudes ya finalizadas asociadas a una inspección.
     * 
     * @param inspeccion inspección de la solicitud
     * @return lista
     */
    List<SolicitudDocumentacionPrevia> findFinalizadasPorInspeccion(Inspeccion inspeccion);
    
    /**
     * Elimina una solicitud y todos los documentos subidos por el usuario asociados a la misma.
     * 
     * @param idSolicitud clave de la solicitud
     */
    void transaccDeleteElimDocPrevia(Long idSolicitud);
    
    /**
     * Recupera la solicitud no finalizada asociada a una inspección (no puede haber más de una).
     * 
     * @param inspeccion inspección de la solicitud
     * @return lista
     */
    SolicitudDocumentacionPrevia findNoFinalizadaPorInspeccion(Inspeccion inspeccion);
    
    /**
     * Recupera las solicitudes enviadas pero aún no cumplimentadas.
     * 
     * @return lista
     */
    List<SolicitudDocumentacionPrevia> findEnviadasNoCumplimentadas();
    
    /**
     * Método que devuelve el número de solicitudes previas totales en una consulta basada en criteria.
     * 
     * @param solicitudDocPreviaBusqueda objeto con los criterios de búsqueda
     * @return número de registros
     */
    int getCountSolicitudDocPreviaCriteria(SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda);
    
    /**
     * Método que devuelve la lista de solicitudes previas en una consulta basada en criteria.
     * 
     * @param solicitudDocPreviaBusqueda objeto con los criterios de búsqueda
     * @param first primer elemento
     * @param pageSize tamaño de cada página de resultados
     * @param sortField campo por el que se ordenan los resultados
     * @param sortOrder sentido de la ordenacion (ascendente/descendente)
     * @return la lista de solicitudes.
     */
    List<SolicitudDocumentacionPrevia> buscarSolicitudDocPreviaCriteria(int first, int pageSize, String sortField,
            SortOrder sortOrder, SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda);
    
    /**
     * Crea una solicitud de documentación y da de alta los documentos seleccionados. Colección de documentos de entre
     * los disponibles en TipoDocumentación que se asignan a la solicitud.
     * 
     * @param solicitudDocumentacionPrevia creada
     * @param documentosSeleccionados asociados a la solicitud
     */
    void transaccSaveAltaDocumentos(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
            List<TipoDocumentacion> documentosSeleccionados);
    
    /**
     * Recupera una solicitud y los documentos subidos al cumplimentarla a partir de su identificador.
     * 
     * @param id clave de la solicitud
     * @return solicitud con documentos cargados
     */
    SolicitudDocumentacionPrevia findByIdConDocumentos(Long id);
    
    /**
     * Elimina de BBDD el documento de una solicitud pasados como parámetros.
     * 
     * @param solicitud solicitud a eliminar
     * @param documento documento a eliminar
     * @return solicitud actualizada
     */
    SolicitudDocumentacionPrevia eliminarDocumentoSolicitud(SolicitudDocumentacionPrevia solicitud,
            Documento documento);
    
}
