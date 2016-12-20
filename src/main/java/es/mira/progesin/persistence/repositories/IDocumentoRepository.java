package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Documento;

public interface IDocumentoRepository extends CrudRepository<Documento, Long> {
	
	List<Documento> findByFechaBajaIsNull();
	
	@EntityGraph(value = "Documento.fichero", type = EntityGraph.EntityGraphType.LOAD)
	Documento findById(Long id);
	


}
