package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Inspeccion;

public interface IInspeccionesRepository  extends CrudRepository<Inspeccion, Integer>{

}
