package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Notificacion;

public interface INotificacionService {
	void delete(Integer id);

	void deleteAll();

	boolean exists(Integer id);

	List<Notificacion> findAll();

	Notificacion findOne(Integer id);

	Iterable<Notificacion> save(Iterable<Notificacion> entities);

	Notificacion save(Notificacion entity);
}
