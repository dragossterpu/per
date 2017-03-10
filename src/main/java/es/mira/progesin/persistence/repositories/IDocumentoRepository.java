package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Documento;

/**
 * Repositorio de operaciones de base de datos para la entidad Documento
 * 
 * @author Ezentis
 *
 */
public interface IDocumentoRepository extends CrudRepository<Documento, Long> {

	/**
	 * Recupera el listado de documentos que no han sido dados de baja
	 * 
	 * @return List<Documento>
	 */
	List<Documento> findByFechaBajaIsNull();

	/**
	 * Recupera documento de la base de datos identificado por el id recibido como parámetro
	 * 
	 * @param id Long Identificador del fichero
	 * @return Documento
	 */
	@EntityGraph(value = "Documento.fichero", type = EntityGraph.EntityGraphType.LOAD)
	Documento findById(Long id);

}
