package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.Sugerencia;

public interface ISugerenciaService {
	void delete(Integer id);

	Sugerencia findOne(Integer id);

	Iterable<Sugerencia> findAll();

	Sugerencia save(Sugerencia entity);
}
