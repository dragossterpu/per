package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Equipo;

public interface IEquipoRepository extends CrudRepository<Equipo, Long> {

	// boolean exists(Integer id);

	// void delete(Integer id);

	// Equipo findOne(Integer id);

	
}
