package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.SolicitudDocumentacion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;

public interface ISolicitudDocumentacionService {

	void save(SolicitudDocumentacion documento);

	SolicitudDocumentacionPrevia savePrevia(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia);

	List<SolicitudDocumentacion> findAll();

	List<SolicitudDocumentacionPrevia> findAllPrevia();

	List<SolicitudDocumentacionPrevia> findAllPreviaEnvio();

	SolicitudDocumentacionPrevia findByCorreoDestiantario(String correo);

}
