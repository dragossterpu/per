package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;

public interface IModeloCuestionarioService {

	ModeloCuestionario save(ModeloCuestionario modeloCuestionario);

	Iterable<ModeloCuestionario> findAll();

	ModeloCuestionario findOne(Integer id);
	

}
