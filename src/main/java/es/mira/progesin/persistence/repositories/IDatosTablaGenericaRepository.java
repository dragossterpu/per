package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.model.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;

public interface IDatosTablaGenericaRepository extends CrudRepository<DatosTablaGenerica, Long> {

	List<DatosTablaGenerica> findByRespuestaRespuestaIdCuestionarioEnviado(CuestionarioEnvio cuestionarioEnviado);
}
