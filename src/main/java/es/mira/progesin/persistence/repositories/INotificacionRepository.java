package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Notificacion;

/***********************************
 * 
 * Repositorio de operaciones de BDD para la entity Notificacion.
 * 
 * Es un repositorio de tipo CRUD
 * 
 * @author Ezentis
 * 
 ************************************/

public interface INotificacionRepository extends CrudRepository<Notificacion, Long> {

	/***********************************
	 * 
	 * Devuelve una lista de notificaciones que no han sido dadas de baja
	 * 
	 * @return List<Alerta>
	 * 
	 ************************************/

	List<Notificacion> findByFechaBajaIsNull();

	/***********************************
	 * 
	 * Devuelve una lista de todas las notificaciones
	 * 
	 * @return List<Alerta>
	 * 
	 ************************************/

	@Override
	List<Notificacion> findAll();

}
