package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;

public interface ISolicitudDocumentacionPreviaRepository extends CrudRepository<SolicitudDocumentacionPrevia, Integer> {

	SolicitudDocumentacionPrevia findByCorreoDestiantario(String correo);

}
