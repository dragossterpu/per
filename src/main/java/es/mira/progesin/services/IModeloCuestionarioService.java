package es.mira.progesin.services;

import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;

public interface IModeloCuestionarioService {

	@Transactional(readOnly = false)
	public ModeloCuestionario save(ModeloCuestionario modeloCuestionario);

	public Iterable<ModeloCuestionario> findAll();

	public ModeloCuestionario findOne(Integer id);

}
