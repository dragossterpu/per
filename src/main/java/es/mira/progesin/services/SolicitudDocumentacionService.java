package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.SolicitudDocumentacion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.repositories.ISolicitudDocumentacionPreviaRepository;
import es.mira.progesin.persistence.repositories.ISolicitudDocumentacionRepository;

@Service
public class SolicitudDocumentacionService implements ISolicitudDocumentacionService {
	@Autowired
	ISolicitudDocumentacionRepository solicitudDocumentacionRepository;

	@Autowired
	ISolicitudDocumentacionPreviaRepository solicitudDocumentacionPreviaRepository;

	@Override
	@Transactional(readOnly = false)
	public void save(SolicitudDocumentacion documento) {
		solicitudDocumentacionRepository.save(documento);
	}

	@Override
	public SolicitudDocumentacionPrevia savePrevia(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia) {
		return solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
	}

	@Override
	public List<SolicitudDocumentacion> findAll() {
		return solicitudDocumentacionRepository.findAll();
	}

	@Override
	public List<SolicitudDocumentacionPrevia> findAllPrevia() {
		return solicitudDocumentacionPreviaRepository.findAll();
	}

	@Override
	public List<SolicitudDocumentacionPrevia> findAllPreviaEnvio() {
		return solicitudDocumentacionPreviaRepository.findByFechaValidApoyoIsNotNull();
	}

	@Override
	public List<SolicitudDocumentacionPrevia> findAllFinalizadas() {
		return solicitudDocumentacionPreviaRepository.findByFechaFinalizacionIsNotNull();
	}

	@Override
	public SolicitudDocumentacionPrevia findByCorreoDestiantario(String correo) {
		return solicitudDocumentacionPreviaRepository.findByCorreoDestiantario(correo);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		solicitudDocumentacionPreviaRepository.delete(id);
	}

}
