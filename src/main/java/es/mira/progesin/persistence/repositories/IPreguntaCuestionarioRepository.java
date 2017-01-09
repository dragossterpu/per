package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;

public interface IPreguntaCuestionarioRepository extends CrudRepository<PreguntasCuestionario, Long> {

	// List<PreguntasCuestionario> findByIdArea(Long idArea);
	List<PreguntasCuestionario> findByArea(AreasCuestionario idArea);

	@Query("select c.preguntasElegidas from CuestionarioPersonalizado c where c.id = :idCuestionarioPersonalizado")
	List<PreguntasCuestionario> findPreguntasElegidasCuestionarioPersonalizado(
			@Param("idCuestionarioPersonalizado") Long idCuestionarioPersonalizado);
}
