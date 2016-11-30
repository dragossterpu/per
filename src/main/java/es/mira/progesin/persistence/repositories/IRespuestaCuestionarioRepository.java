package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;

public interface IRespuestaCuestionarioRepository extends CrudRepository<RespuestaCuestionario, Long> {

}
