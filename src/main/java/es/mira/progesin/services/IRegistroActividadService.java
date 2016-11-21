package es.mira.progesin.services;

import java.io.Serializable;
import java.util.List;

import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.web.beans.RegActividadBusqueda;

public interface IRegistroActividadService extends Serializable {
	void delete(Integer id);

	void deleteAll();

	boolean exists(Integer id);

	Iterable<RegistroActividad> findAll();

	RegistroActividad findOne(Integer id);

	RegistroActividad save(RegistroActividad entity);

	List<RegistroActividad> buscarRegActividadCriteria(RegActividadBusqueda regActividadBusqueda);

	void altaRegActivError(String nombreSeccion, Exception e);
}