package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.RegActividad;

public interface IRegActividadService {
	void delete(Integer id);

	void deleteAll();

	boolean exists(Integer id);

	Iterable<RegActividad> findAll();

	RegActividad findOne(Integer id);

	Iterable<RegActividad> save(Iterable<RegActividad> entities);

	RegActividad save(RegActividad entity);
}
