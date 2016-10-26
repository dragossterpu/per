package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.SolicitudDocumentacion;

public interface ISolicitudDocumentacionRepository extends CrudRepository<SolicitudDocumentacion, Integer> {
	@Override
	List<SolicitudDocumentacion> findAll();

}
