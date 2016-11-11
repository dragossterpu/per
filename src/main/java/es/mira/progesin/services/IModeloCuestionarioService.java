package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.PreEnvioCuest;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;

public interface IModeloCuestionarioService {

	void save(ModeloCuestionario modeloCuestionario);

	Iterable<ModeloCuestionario> findAll();

	ModeloCuestionario findOne(Integer id);

	List<PreEnvioCuest> findAllPre();

	void savePre(PreEnvioCuest preEnvioCuest);

	PreEnvioCuest savePreAlta(PreEnvioCuest preEnvioCuest);

	void delete(Integer id);

}
