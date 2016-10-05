package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.ModeloCuestionario;

public interface IModeloCuestionarioService {

	public void save(ModeloCuestionario modeloCuestionario);

	public Iterable<ModeloCuestionario> findAll();
}
