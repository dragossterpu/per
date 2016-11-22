package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;

public interface ICuestionarioEnvioRepository extends CrudRepository<CuestionarioEnvio, Long> {

	CuestionarioEnvio findByInspeccion(Inspeccion inspeccion);
}
