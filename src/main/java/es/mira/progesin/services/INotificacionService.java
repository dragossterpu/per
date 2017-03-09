package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.enums.RoleEnum;

/*********************************************
 * 
 * Interfaz del servicio de Notificaciones
 * 
 * @author Ezentis
 * @see NotificacionService
 * 
 *******************************************/
public interface INotificacionService {

	void delete(Long id);

	void deleteAll();

	boolean exists(Long id);

	List<Notificacion> findAll();

	List<Notificacion> findByFechaBajaIsNull();

	Notificacion findOne(Long id);

	void crearNotificacionUsuario(String descripcion, String seccion, String usuario);

	void crearNotificacionRol(String descripcion, String seccion, RoleEnum rol);

	void crearNotificacionRol(String seccion, String descripcion, List<RoleEnum> roles);

	void crearNotificacionEquipo(String descripcion, String seccion, Inspeccion inspeccion);

	void crearNotificacionEquipo(String descripcion, String seccion, Equipo equipo);

	void crearNotificacionJefeEquipo(String descripcion, String seccion, Inspeccion inspeccion);
}
