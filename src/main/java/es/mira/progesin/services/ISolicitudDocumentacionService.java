package es.mira.progesin.services;

import java.util.List;

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
    
    List<SolicitudDocumentacionPrevia> buscarSolicitudDocPreviaCriteria(
            SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda);
    
    boolean transaccSaveCreaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia, User usuarioProv);
    
    boolean transaccSaveElimUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia, String usuarioProv);
    
    boolean transaccSaveInactivaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
            String usuarioProv);
    
    boolean transaccSaveActivaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
            String usuarioProv);
    
    List<SolicitudDocumentacionPrevia> findFinalizadasPorInspeccion(Inspeccion inspeccion);
    
    void transaccDeleteElimDocPrevia(Long idSolicitud);
    
    SolicitudDocumentacionPrevia findNoFinalizadaPorInspeccion(Inspeccion inspeccion);
    
    List<SolicitudDocumentacionPrevia> findEnviadasNoCumplimentadas();
    
}
