package es.mira.progesin.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.repositories.INotificacionRepository;

@Service
public class NotificacionService implements INotificacionService {
	@Autowired
	INotificacionRepository notificacionRepository;

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		notificacionRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAll() {
		notificacionRepository.deleteAll();
	}

	@Override
	public boolean exists(Integer id) {
		return notificacionRepository.exists(id);
	}

	@Override
	public List<Notificacion> findAll() {
		return notificacionRepository.findAll();
	}

	@Override
	public List<Notificacion> findByFechaBajaIsNull() {
		return notificacionRepository.findByFechaBajaIsNull();
	}

	@Override
	public Notificacion findOne(Integer id) {
		return notificacionRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Notificacion save(Notificacion entity) {
		return notificacionRepository.save(entity);
	}

	@Override
	public void crearNotificacion(String descripcion, String tipoNotificacion, String seccion) {
		Notificacion notificacion = new Notificacion();
		notificacion.setTipoNotificacion(tipoNotificacion);
		notificacion.setUsernameNotificacion(SecurityContextHolder.getContext().getAuthentication().getName());
		notificacion.setNombreSeccion(seccion);
		notificacion.setFechaAlta(new Date());
		notificacion.setDescripcion(descripcion);
		notificacionRepository.save(notificacion);

	}

}
