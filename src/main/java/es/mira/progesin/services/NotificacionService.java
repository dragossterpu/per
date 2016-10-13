package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	public Iterable<Notificacion> findAll(Iterable<Integer> ids) {
		return notificacionRepository.findAll(ids);
	}

	@Override
	public Notificacion findOne(Integer id) {
		return notificacionRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Iterable<Notificacion> save(Iterable<Notificacion> entities) {
		return notificacionRepository.save(entities);
	}

	@Override
	@Transactional(readOnly = false)
	public Notificacion save(Notificacion entity) {
		return notificacionRepository.save(entity);
	}

}
