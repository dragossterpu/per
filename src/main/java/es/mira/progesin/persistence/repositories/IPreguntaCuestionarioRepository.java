package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.PreguntasCuestionario;

public interface IPreguntaCuestionarioRepository extends CrudRepository<PreguntasCuestionario, Long> {

	List<PreguntasCuestionario> findByIdArea(Long idArea);
}
