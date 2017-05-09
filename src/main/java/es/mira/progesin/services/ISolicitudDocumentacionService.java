package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.web.beans.SolicitudDocPreviaBusqueda;

/**
 * 
 * Interfaz del servicio de solicitudes de documentación
 * 
 * @author Ezentis
 *
 */
public interface ISolicitudDocumentacionService {
    
    /**
     * Guarda la información de una solicitud en la bdd.
     * 
     * @author Ezentis
     * @param solicitudDocumentacionPrevia
     * @return solicitud
     */
    SolicitudDocumentacionPrevia save(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia);
    
    /**
     * Recupera todas las solicitudes existentes
     * 
     * @author Ezentis
     * @return lista
     */
    List<SolicitudDocumentacionPrevia> findAll();
    
    /**
     * Recupera las solicitudes ya finalizadas pertenecientes a un mismo destinatario
     * 
     * @author Ezentis
     * @param correo
     * @return lista
     */
    SolicitudDocumentacionPrevia findNoFinalizadaPorCorreoDestinatario(String correo);
    
    /**
     * Recupera las solicitudes ya enviadas pero sin finalizar pertenecientes a un mismo destinatario
     * 
     * @author Ezentis
     * @param correo
     * @return lista
     */
    SolicitudDocumentacionPrevia findEnviadaNoFinalizadaPorCorreoDestinatario(String correo);
    
    /**
     * Elimina una solicitud a partir de su id
     * 
     * @author Ezentis
     * @param id
     */
    void delete(Long id);
    
    /**
     * Guarda los datos de una solicitud y crea el usuario provisional que debe cumplimentarla una vez enviada
     * 
     * @author Ezentis
     * @param solicitudDocumentacionPrevia
     * @param usuarioProv
     */
    void transaccSaveCreaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia, User usuarioProv);
    
    /**
     * Guarda los datos de una solicitud y elimina el usuario provisional que la ha cumplimentado una vez finalizada o
     * anulada
     * 
     * @author Ezentis
     * @param solicitudDocumentacionPrevia
     * @param usuarioProv
     */
    void transaccSaveElimUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia, String usuarioProv);
    
    /**
     * Guarda los datos de una solicitud e inactiva el usuario provisional que la ha cumplimentado
     * 
     * @author Ezentis
     * @param solicitudDocumentacionPrevia
     * @param usuarioProv
     */
    void transaccSaveInactivaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia, String usuarioProv);
    
    /**
     * Guarda los datos de una solicitud y activa el usuario provisional que debe cumplimentarla de nuevo en caso de no
     * conformidad
     * 
     * @author Ezentis
     * @param solicitudDocumentacionPrevia
     * @param usuarioProv
     */
    void transaccSaveActivaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia, String usuarioProv);
    
    /**
     * Recupera las solicitudes ya finalizadas asociadas a una inspección
     * 
     * @author Ezentis
     * @param inspeccion
     * @return lista
     */
    List<SolicitudDocumentacionPrevia> findFinalizadasPorInspeccion(Inspeccion inspeccion);
    
    /**
     * Elimina una solicitud y todos los documentos subidos por el usuario asociados a la misma
     * 
     * @author Ezentis
     * @param idSolicitud
     */
    void transaccDeleteElimDocPrevia(Long idSolicitud);
    
    /**
     * Recupera las solicitudes no finalizadas asociadas a una inspección
     * 
     * @author Ezentis
     * @param inspeccion
     * @return lista
     */
    SolicitudDocumentacionPrevia findNoFinalizadaPorInspeccion(Inspeccion inspeccion);
    
    /**
     * Recupera las solicitudes enviadas pero aún no cumplimentadas
     * 
     * @author Ezentis
     * @return lista
     */
    List<SolicitudDocumentacionPrevia> findEnviadasNoCumplimentadas();
    
    /**
     * Método que devuelve el número de solicitudes previas totales en una consulta basada en criteria.
     * 
     * @param solicitudDocPreviaBusqueda
     * @return número de registros
     * @author EZENTIS
     */
    int getCountSolicitudDocPreviaCriteria(SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda);
    
    /**
     * Método que devuelve la lista de solicitudes previas en una consulta basada en criteria.
     * 
     * @param solicitudDocPreviaBusqueda
     * @param first
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return devuelve la lista de registros tipo SolicitudDocPreviaBusqueda.
     * @author EZENTIS
     */
    List<SolicitudDocumentacionPrevia> buscarSolicitudDocPreviaCriteria(int first, int pageSize, String sortField,
            SortOrder sortOrder, SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda);
    
}
