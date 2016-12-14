package es.mira.progesin.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.INotificacionRepository;

@Service
public class NotificacionService implements INotificacionService {
	@Autowired
	INotificacionRepository notificacionRepository;
	
	@Autowired
	IMensajeService mensajeService;
	
	@Autowired
	IRegistroActividadService registroActividadService;

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		notificacionRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAll() {
		notificacionRepository.deleteAll();
	}

	@Override
	public boolean exists(Long id) {
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
	public Notificacion findOne(Long id) {
		return notificacionRepository.findOne(id);
	}

	@Transactional(readOnly = false)
	private Notificacion save(Notificacion entity) {
		return notificacionRepository.save(entity);
	}

	
	private Notificacion crearNotificacion(String descripcion, String seccion) {
		try {
			Notificacion notificacion = new Notificacion();
			notificacion.setUsernameNotificacion(SecurityContextHolder.getContext().getAuthentication().getName());
			notificacion.setNombreSeccion(seccion);
			notificacion.setFechaAlta(new Date());
			notificacion.setDescripcion(descripcion);
			registroActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.ALTA.name(), seccion);
			return notificacionRepository.save(notificacion);
		} catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}
		return null;

	}

	@Override
	public void crearNotificacionUsuario(String descripcion, String seccion, String usuario) {
		try {
			Notificacion notificacion=crearNotificacion(descripcion, seccion);
			mensajeService.grabarMensajeUsuario(notificacion, usuario);
		} catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}
		
	}

	@Override
	public void crearNotificacionRol(String descripcion, String seccion, RoleEnum rol) {
		try {
			Notificacion notificacion=crearNotificacion(descripcion, seccion);
			mensajeService.grabarMensajeRol(notificacion, rol);
		} catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}
	}

	@Override
	public void crearNotificacionEquipo(String descripcion, String seccion, Long idEquipo) {
		try {
			Notificacion notificacion=crearNotificacion(descripcion, seccion);
			mensajeService.grabarMensajeEquipo(notificacion, idEquipo);
		} catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}
	}

	@Override
	public void crearNotificacionJefeEquipo(String descripcion, String seccion, Long idEquipo) {
		try {
			Notificacion notificacion=crearNotificacion(descripcion, seccion);
			mensajeService.grabarMensajeJefeEquipo(notificacion, idEquipo);
		} catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}
	}


	

}
