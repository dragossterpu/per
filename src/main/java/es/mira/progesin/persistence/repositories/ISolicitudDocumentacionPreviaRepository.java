package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;

public interface ISolicitudDocumentacionPreviaRepository extends CrudRepository<SolicitudDocumentacionPrevia, Integer> {
	@Override
	List<SolicitudDocumentacionPrevia> findAll();

	List<SolicitudDocumentacionPrevia> findByFechaValidApoyoIsNotNull();

	SolicitudDocumentacionPrevia findByCorreoDestiantario(String correo);

}
