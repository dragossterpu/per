package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;

public interface ICuestionarioEnvioRepository extends CrudRepository<CuestionarioEnvio, Long> {

	CuestionarioEnvio findByInspeccion(Inspeccion inspeccion);

	CuestionarioEnvio findByCorreoEnvioAndFechaFinalizacionIsNull(String correo);

	CuestionarioEnvio findDistinctById(Long idCuestionarioEnviado);

	CuestionarioEnvio findByInspeccionAndFechaFinalizacionIsNull(Inspeccion inspeccion);

	CuestionarioEnvio findByCuestionarioPersonalizado(CuestionarioPersonalizado cuestionario);

	@Override
	List<CuestionarioEnvio> findAll();
}
