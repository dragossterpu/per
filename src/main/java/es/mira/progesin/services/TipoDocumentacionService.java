package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.TipoDocumentacion;
import es.mira.progesin.persistence.repositories.ITipoDocumentacionRepository;

@Service
public class TipoDocumentacionService implements ITipoDocumentacionService {
	@Autowired
	ITipoDocumentacionRepository tipoDocumentacionRepository;

	@Override
	public List<TipoDocumentacion> findAll() {
		return tipoDocumentacionRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		tipoDocumentacionRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public TipoDocumentacion save(TipoDocumentacion entity) {
		return tipoDocumentacionRepository.save(entity);
	}
}
