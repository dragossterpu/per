package es.mira.progesin.persistence.repositories.gd;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;

public interface IGestDocSolicitudDocumentacionRepository
		extends CrudRepository<GestDocSolicitudDocumentacion, Integer> {
	List<GestDocSolicitudDocumentacion> findByIdSolicitud(Integer idSolicitud);

}
