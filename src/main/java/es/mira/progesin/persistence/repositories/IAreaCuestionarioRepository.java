package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;

public interface IAreaCuestionarioRepository extends CrudRepository<AreasCuestionario, Long> {

	@EntityGraph(value = "AreasCuestionario.preguntas", type = EntityGraphType.LOAD)
	List<AreasCuestionario> findDistinctByIdCuestionario(Integer idCuestionario);
}
