package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.DatosModeloCuestionario;
import es.mira.progesin.persistence.entities.ModeloCuestionario;

public interface IDatosModeloCuestionarioService {

	void delete(Long id);

	void delete(Iterable<DatosModeloCuestionario> entities);

	void delete(DatosModeloCuestionario entity);

	void deleteAll();

	boolean exists(Long id);

	Iterable<DatosModeloCuestionario> findAll();

	Iterable<DatosModeloCuestionario> findAll(Iterable<Long> ids);

	DatosModeloCuestionario findOne(Long id);

	Iterable<DatosModeloCuestionario> save(Iterable<DatosModeloCuestionario> entities);

	DatosModeloCuestionario save(DatosModeloCuestionario entity);

	List<DatosModeloCuestionario> findByModeloCuestionario(ModeloCuestionario modeloCuestionario);
}
