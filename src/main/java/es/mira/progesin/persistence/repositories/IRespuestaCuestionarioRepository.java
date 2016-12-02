package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionarioId;

public interface IRespuestaCuestionarioRepository
		extends CrudRepository<RespuestaCuestionario, RespuestaCuestionarioId> {

	List<RespuestaCuestionario> findByRespuestaIdCuestionarioEnviadoAndRespuestaTextoNotNull(
			CuestionarioEnvio cuestionarioEnviado);
}
