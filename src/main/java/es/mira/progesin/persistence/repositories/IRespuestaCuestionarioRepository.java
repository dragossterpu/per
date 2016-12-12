package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionarioId;

public interface IRespuestaCuestionarioRepository
		extends CrudRepository<RespuestaCuestionario, RespuestaCuestionarioId> {

	List<RespuestaCuestionario> findByRespuestaIdCuestionarioEnviadoAndRespuestaTextoNotNull(
			CuestionarioEnvio cuestionarioEnviado);

	List<RespuestaCuestionario> findByRespuestaIdCuestionarioEnviado(CuestionarioEnvio cuestionarioEnviado);

	@Query("select r from RespuestaCuestionario r where r.respuestaId.cuestionarioEnviado = :cuestionarioEnviado and (r.respuestaId.pregunta.tipoRespuesta like 'TABLA%' or r.respuestaId.pregunta.tipoRespuesta like 'MATRIZ%')")
	List<RespuestaCuestionario> findRespuestasTablaMatrizByCuestionarioEnviado(
			@Param("cuestionarioEnviado") CuestionarioEnvio cuestionarioEnviado);

}
