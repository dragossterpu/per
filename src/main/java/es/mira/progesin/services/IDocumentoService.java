package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.Documento;

public interface IDocumentoService {
	void delete(Long id);

	void delete(Iterable<Documento> entities);

	void delete(Documento entity);

	void deleteAll();

	boolean exists(Long id);

	Iterable<Documento> findAll();

	Iterable<Documento> findAll(Iterable<Long> ids);

	Documento findOne(Long id);

	Iterable<Documento> save(Iterable<Documento> entities);

	Documento save(Documento entity);

}
