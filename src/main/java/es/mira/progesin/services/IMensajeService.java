package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.Mensaje;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;

public interface IMensajeService {

	void delete(String user, Long id, TipoMensajeEnum tipo);

	Mensaje save(Mensaje entity);
	
	List<Alerta> findAlertasByUser(String user);
	
	List<Notificacion> findNotificacionesByUser(String user);
	
	Mensaje grabarMensajeUsuario(Object entidad, String user);
	
	Mensaje grabarMensajeJefeEquipo(Object entidad, Long equipo);
	
	void grabarMensajeRol(Object entidad, RoleEnum rol);
	
	void grabarMensajeEquipo(Object entidad, Long equipo);
	
}
