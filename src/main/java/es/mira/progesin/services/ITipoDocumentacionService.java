package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.TipoDocumentacion;

public interface ITipoDocumentacionService {

	List<TipoDocumentacion> findAll();

	void delete(Integer id);

	TipoDocumentacion save(TipoDocumentacion entity);

}
