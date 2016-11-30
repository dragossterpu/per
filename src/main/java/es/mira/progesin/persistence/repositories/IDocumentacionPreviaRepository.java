package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.DocumentacionPrevia;

public interface IDocumentacionPreviaRepository extends CrudRepository<DocumentacionPrevia, String> {

	List<DocumentacionPrevia> findByIdSolicitud(Long idSolicitud);

	void deleteByIdSolicitud(Long idSolicitud);

}
