package es.mira.progesin.services.gd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.persistence.repositories.IDocumentacionPreviaRepository;
import es.mira.progesin.persistence.repositories.gd.ITipoDocumentacionRepository;

@Service
public class TipoDocumentacionService implements ITipoDocumentacionService {
	@Autowired
	ITipoDocumentacionRepository tipoDocumentacionRepository;

	@Autowired
	IDocumentacionPreviaRepository documentacionPreviaRepository;

	@Override
	public List<TipoDocumentacion> findAll() {
		return tipoDocumentacionRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		tipoDocumentacionRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public TipoDocumentacion save(TipoDocumentacion entity) {
		return tipoDocumentacionRepository.save(entity);
	}

	@Override
	public List<TipoDocumentacion> findByAmbito(AmbitoInspeccionEnum ambito) {
		return tipoDocumentacionRepository.findByAmbito(ambito);
	}

	@Override
	@Transactional(readOnly = false)
	public DocumentacionPrevia save(DocumentacionPrevia entity) {
		return documentacionPreviaRepository.save(entity);
	}

	@Override
	public List<DocumentacionPrevia> findByIdSolicitud(Long idSolicitud) {
		return documentacionPreviaRepository.findByIdSolicitud(idSolicitud);
	}

}
