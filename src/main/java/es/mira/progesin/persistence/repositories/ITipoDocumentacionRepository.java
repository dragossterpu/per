package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.TipoDocumentacion;

public interface ITipoDocumentacionRepository extends CrudRepository<TipoDocumentacion, Integer> {
	@Override
	List<TipoDocumentacion> findAll();

}
