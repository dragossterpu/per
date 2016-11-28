package es.mira.progesin.persistence.repositories.gd;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;

public interface IGestDocSolicitudDocumentacionRepository extends CrudRepository<GestDocSolicitudDocumentacion, Long> {
	List<GestDocSolicitudDocumentacion> findByIdSolicitud(Long idSolicitud);
}
