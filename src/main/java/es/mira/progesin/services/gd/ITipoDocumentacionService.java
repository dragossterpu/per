package es.mira.progesin.services.gd;

import java.util.List;

import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;

public interface ITipoDocumentacionService {

	List<TipoDocumentacion> findAll();

	void delete(Integer id);

	TipoDocumentacion save(TipoDocumentacion entity);

	DocumentacionPrevia save(DocumentacionPrevia entity);

	List<DocumentacionPrevia> findByIdSolicitud(Integer idSolicitud);

}
