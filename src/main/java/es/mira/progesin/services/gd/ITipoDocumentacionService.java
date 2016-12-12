package es.mira.progesin.services.gd;

import java.util.List;

import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;

public interface ITipoDocumentacionService {

	List<TipoDocumentacion> findAll();

	void delete(Long id);

	TipoDocumentacion save(TipoDocumentacion entity);

	List<TipoDocumentacion> findByAmbito(AmbitoInspeccionEnum ambito);

	DocumentacionPrevia save(DocumentacionPrevia entity);

	List<DocumentacionPrevia> findByIdSolicitud(Long idSolicitud);

}
