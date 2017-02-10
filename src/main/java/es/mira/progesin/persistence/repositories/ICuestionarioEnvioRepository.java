package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;

public interface ICuestionarioEnvioRepository extends CrudRepository<CuestionarioEnvio, Long> {

	CuestionarioEnvio findByInspeccion(Inspeccion inspeccion);

	CuestionarioEnvio findByCorreoEnvioAndFechaFinalizacionIsNullAndFechaAnulacionIsNull(String correo);

	CuestionarioEnvio findDistinctById(Long idCuestionarioEnviado);

	CuestionarioEnvio findByFechaAnulacionIsNullAndFechaFinalizacionIsNullAndInspeccion(Inspeccion inspeccion);

	CuestionarioEnvio findByCuestionarioPersonalizado(CuestionarioPersonalizado cuestionario);

	List<CuestionarioEnvio> findByFechaAnulacionIsNullAndFechaFinalizacionIsNullAndFechaCumplimentacionIsNull();
}
