package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;

public interface IModeloCuestionarioService {

	void save(ModeloCuestionario modeloCuestionario);

	Iterable<ModeloCuestionario> findAll();

	ModeloCuestionario findOne(Integer id);

}
