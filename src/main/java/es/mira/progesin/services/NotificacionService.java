package es.mira.progesin.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.INotificacionRepository;

@Service
public class NotificacionService implements INotificacionService {
	@Autowired
	INotificacionRepository notificacionRepository;
	
	@Autowired
	IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;
	
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
			alertasNotificacionesUsuarioService.grabarMensajeUsuario(notificacion, usuario);
		} catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}
		
	}

	@Override
	public void crearNotificacionRol(String descripcion, String seccion, RoleEnum rol) {
		try {
			Notificacion notificacion=crearNotificacion(descripcion, seccion);
			alertasNotificacionesUsuarioService.grabarMensajeRol(notificacion, rol);
		} catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}
	}
	
	@Override
	public void crearNotificacionRol(String seccion, String descripcion, List<RoleEnum> roles) {
		try {
			Notificacion notificacion=crearNotificacion(descripcion, seccion);
			alertasNotificacionesUsuarioService.grabarMensajeRol(notificacion, roles);
		} catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}
		
	}

	@Override
	public void crearNotificacionEquipo(String descripcion, String seccion, Inspeccion inspeccion) {
		try {
			Notificacion notificacion=crearNotificacion(descripcion, seccion);
			alertasNotificacionesUsuarioService.grabarMensajeEquipo(notificacion, inspeccion);
		} catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}
	}

	@Override
	public void crearNotificacionJefeEquipo(String descripcion, String seccion, Inspeccion inspeccion) {
		try {
			Notificacion notificacion=crearNotificacion(descripcion, seccion);
			alertasNotificacionesUsuarioService.grabarMensajeJefeEquipo(notificacion, inspeccion);
		} catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}
	}

	


	

}
