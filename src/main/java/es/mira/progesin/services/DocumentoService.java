package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.Documento;
import es.mira.progesin.persistence.repositories.IDocumentoRepository;

@Service
public class DocumentoService implements IDocumentoService {

	@Autowired
	IDocumentoRepository documentoRepository;

	@Override
	public void delete(Long id) {
		documentoRepository.delete(id);
	}

	@Override
	public void delete(Iterable<Documento> entities) {
		documentoRepository.delete(entities);
	}

	@Override
	public void delete(Documento entity) {
		documentoRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		documentoRepository.deleteAll();
	}

	@Override
	public boolean exists(Long id) {
		return documentoRepository.exists(id);
	}

	@Override
	public Iterable<Documento> findAll() {
		return documentoRepository.findAll();
	}

	@Override
	public Iterable<Documento> findAll(Iterable<Long> ids) {
		return documentoRepository.findAll(ids);
	}

	@Override
	public Documento findOne(Long id) {
		return documentoRepository.findOne(id);
	}

	@Override
	public Iterable<Documento> save(Iterable<Documento> entities) {
		return documentoRepository.save(entities);
	}

	@Override
	public Documento save(Documento entity) {
		return documentoRepository.save(entity);
	}

}
