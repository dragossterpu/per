package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;

public interface IAreaCuestionarioRepository extends CrudRepository<AreasCuestionario, Long> {

	List<AreasCuestionario> findByIdCuestionario(Integer idCuestionario);
}
