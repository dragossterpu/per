package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.web.beans.SolicitudDocPreviaBusqueda;

public interface ISolicitudDocumentacionService {

	SolicitudDocumentacionPrevia savePrevia(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia);

	List<SolicitudDocumentacionPrevia> findAllPrevia();

	List<SolicitudDocumentacionPrevia> findAllFinalizadas();

	List<SolicitudDocumentacionPrevia> findAllPreviaEnvio();

	SolicitudDocumentacionPrevia findByCorreoDestiantario(String correo);

	void delete(Integer id);

	List<SolicitudDocumentacionPrevia> buscarSolicitudDocPreviaCriteria(
			SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda);

}
