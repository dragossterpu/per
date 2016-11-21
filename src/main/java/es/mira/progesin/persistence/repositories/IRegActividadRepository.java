package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.RegistroActividad;

public interface IRegActividadRepository extends CrudRepository<RegistroActividad, Integer> {
}
