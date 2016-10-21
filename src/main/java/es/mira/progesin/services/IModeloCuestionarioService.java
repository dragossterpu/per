package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.ModeloCuestionario;
import es.mira.progesin.persistence.entities.PreEnvioCuestionario;

public interface IModeloCuestionarioService {

	void save(ModeloCuestionario modeloCuestionario);

	Iterable<ModeloCuestionario> findAll();

	ModeloCuestionario findOne(Integer id);

	List<PreEnvioCuestionario> findAllPre();

	void savePre(PreEnvioCuestionario preEnvioCuestionario);

}
