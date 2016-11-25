package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Notificacion;

public interface INotificacionService {

	void delete(Integer id);

	void deleteAll();

	boolean exists(Integer id);

	List<Notificacion> findAll();

	List<Notificacion> findByFechaBajaIsNull();

	Notificacion findOne(Integer id);

	Notificacion save(Notificacion entity);

	void crearNotificacion(String descripcion, String tipoNotificacion, String seccion);
}
