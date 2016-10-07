package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.TipoEquipo;

public interface ITipoEquipoService {

	Iterable<TipoEquipo> findAll();

	void delete(Integer id);

}
