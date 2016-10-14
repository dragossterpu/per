package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.RegActividad;
import es.mira.progesin.web.beans.RegActividadBusqueda;

public interface IRegActividadService {
	void delete(Integer id);

	void deleteAll();

	boolean exists(Integer id);

	Iterable<RegActividad> findAll();

	RegActividad findOne(Integer id);

	RegActividad save(RegActividad entity);
	
	List<RegActividad> buscarRegActividadCriteria(RegActividadBusqueda regActividadBusqueda);
}
