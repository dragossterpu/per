package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.DatosModeloCuestionario;
import es.mira.progesin.persistence.entities.ModeloCuestionario;

public interface IDatosModeloCuestionarioRepository extends CrudRepository<DatosModeloCuestionario, Long> {

	List<DatosModeloCuestionario> findByModeloCuestionario(ModeloCuestionario modeloCuestionario);
}
