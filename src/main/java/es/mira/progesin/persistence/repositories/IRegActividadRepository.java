package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.RegActividad;

public interface IRegActividadRepository extends CrudRepository<RegActividad, Integer> {
}
