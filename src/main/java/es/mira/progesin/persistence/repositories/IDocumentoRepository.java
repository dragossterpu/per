package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Documento;

public interface IDocumentoRepository extends CrudRepository<Documento, Long> {
	
	List<Documento> findByFechaBajaIsNull();


}
