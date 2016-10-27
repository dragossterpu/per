package es.mira.progesin.services.gd;

import java.util.List;

import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;

public interface IGestDocSolicitudDocumentacionService {

	void save(GestDocSolicitudDocumentacion documento);

	List<GestDocSolicitudDocumentacion> findByIdSolicitud(Integer idSolicitud);

}
