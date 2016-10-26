package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.TipoDocumentacion;

public interface ITipoDocumentacionService {

	List<TipoDocumentacion> findAll();

	void delete(Integer id);

	TipoDocumentacion save(TipoDocumentacion entity);

	DocumentacionPrevia save(DocumentacionPrevia entity);

	List<DocumentacionPrevia> findByIdSolicitud(Integer idSolicitud);

}
