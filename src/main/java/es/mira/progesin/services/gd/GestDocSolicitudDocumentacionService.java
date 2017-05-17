package es.mira.progesin.services.gd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;
import es.mira.progesin.persistence.repositories.gd.IGestDocSolicitudDocumentacionRepository;

/**
 * 
 * Implementación del servicio de documentos de solicitud de documentación previa
 * 
 * @author EZENTIS
 *
 */
@Service
public class GestDocSolicitudDocumentacionService implements IGestDocSolicitudDocumentacionService {
    
    @Autowired
    IGestDocSolicitudDocumentacionRepository gestSolicitudDocumentacionRepository;
    
    @Override
    @Transactional(readOnly = false)
    public GestDocSolicitudDocumentacion save(GestDocSolicitudDocumentacion documento) {
        return gestSolicitudDocumentacionRepository.save(documento);
    }
    
    @Override
    public List<GestDocSolicitudDocumentacion> findByIdSolicitud(Long idSolicitud) {
        return gestSolicitudDocumentacionRepository.findByIdSolicitud(idSolicitud);
    }
    
    @Override
    @Transactional(readOnly = false)
    public void delete(GestDocSolicitudDocumentacion documento) {
        gestSolicitudDocumentacionRepository.delete(documento.getId());
        
    }
    
}
