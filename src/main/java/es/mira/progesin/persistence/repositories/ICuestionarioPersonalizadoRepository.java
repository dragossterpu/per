package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;

public interface ICuestionarioPersonalizadoRepository extends CrudRepository<CuestionarioPersonalizado, Long> {

}
