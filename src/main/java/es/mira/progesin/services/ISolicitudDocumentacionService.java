package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.SolicitudDocumentacion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.web.beans.SolicitudDocPreviaBusqueda;

public interface ISolicitudDocumentacionService {

	void save(SolicitudDocumentacion documento);

	SolicitudDocumentacionPrevia savePrevia(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia);

	List<SolicitudDocumentacion> findAll();

	List<SolicitudDocumentacionPrevia> findAllPrevia();

	List<SolicitudDocumentacionPrevia> findAllFinalizadas();

	List<SolicitudDocumentacionPrevia> findAllPreviaEnvio();

	SolicitudDocumentacionPrevia findByCorreoDestiantario(String correo);

	void delete(Integer id);

	List<SolicitudDocumentacionPrevia> buscarSolicitudDocPreviaCriteria(
			SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda);

}
