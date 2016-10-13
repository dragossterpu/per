package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Notificacion;

public interface INotificacionRepository extends CrudRepository<Notificacion, Integer> {

	@Override
	List<Notificacion> findAll();
}
