package es.mira.progesin.services.gd;

import java.util.List;

import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;

public interface IGestDocSolicitudDocumentacionService {
    
    GestDocSolicitudDocumentacion save(GestDocSolicitudDocumentacion documento);
    
    List<GestDocSolicitudDocumentacion> findByIdSolicitud(Long idSolicitud);
    
    void delete(GestDocSolicitudDocumentacion documento);
    
}
