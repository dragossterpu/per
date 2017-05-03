package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.web.beans.SolicitudDocPreviaBusqueda;

public interface ISolicitudDocumentacionService {
    
    SolicitudDocumentacionPrevia save(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia);
    
    List<SolicitudDocumentacionPrevia> findAll();
    
    SolicitudDocumentacionPrevia findNoFinalizadaPorCorreoDestinatario(String correo);
    
    SolicitudDocumentacionPrevia findEnviadaNoFinalizadaPorCorreoDestinatario(String correo);
    
    void delete(Long id);
    
    void transaccSaveCreaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia, User usuarioProv);
    
    void transaccSaveElimUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia, String usuarioProv);
    
    void transaccSaveInactivaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
            String usuarioProv);
    
    void transaccSaveActivaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
            String usuarioProv);
    
    List<SolicitudDocumentacionPrevia> findFinalizadasPorInspeccion(Inspeccion inspeccion);
    
    void transaccDeleteElimDocPrevia(Long idSolicitud);
    
    SolicitudDocumentacionPrevia findNoFinalizadaPorInspeccion(Inspeccion inspeccion);
    
    List<SolicitudDocumentacionPrevia> findEnviadasNoCumplimentadas();
    
    int getCountSolicitudDocPreviaCriteria(SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda);
    
    List<SolicitudDocumentacionPrevia> buscarSolicitudDocPreviaCriteria(int first, int pageSize, String sortField,
            SortOrder sortOrder, SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda);
    
}
