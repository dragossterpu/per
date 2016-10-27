package es.mira.progesin.services.gd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;
import es.mira.progesin.persistence.repositories.gd.IGestDocSolicitudDocumentacionRepository;

@Service
public class GestDocSolicitudDocumentacionService implements IGestDocSolicitudDocumentacionService {

	@Autowired
	IGestDocSolicitudDocumentacionRepository gestSolicitudDocumentacionRepository;

	@Override
	@Transactional(readOnly = false)
	public void save(GestDocSolicitudDocumentacion documento) {
		gestSolicitudDocumentacionRepository.save(documento);
	}

	@Override
	public List<GestDocSolicitudDocumentacion> findByIdSolicitud(Integer idSolicitud) {
		return gestSolicitudDocumentacionRepository.findByIdSolicitud(idSolicitud);
	}

}
