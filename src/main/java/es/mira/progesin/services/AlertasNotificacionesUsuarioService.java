package es.mira.progesin.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.AlertasNotificacionesUsuario;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
import es.mira.progesin.persistence.repositories.IAlertasNotificacionesUsuarioRepository;
import es.mira.progesin.persistence.repositories.IMiembrosRepository;

/********************
 * 
 * @author Ezentis
 * 
 *
 ********************/

@Service
public class AlertasNotificacionesUsuarioService implements IAlertasNotificacionesUsuarioService {

	@Autowired
	private IAlertasNotificacionesUsuarioRepository mensajeRepo;

	@Autowired
	private IUserService userService;

	@Autowired
	private IAlertaService alertaService;

	@Autowired
	private INotificacionService notificacionService;

	@Autowired
	private IMiembrosRepository miembrosRepository;

	/***************************
	 * 
	 * Delete
	 * 
	 * Elimina un registro de base de datos. El registro se identifica por su tipo, el id y el usuario vinculado
	 * 
	 * @param String Usuario
	 * @param Long Id
	 * @param TipoMensajeEnum tipo
	 * 
	 ***************************/
	@Override
	public void delete(String user, Long id, TipoMensajeEnum tipo) {
		AlertasNotificacionesUsuario men = mensajeRepo.findByUsuarioAndTipoAndIdMensaje(user, tipo, id);
		mensajeRepo.delete(men);

	}

	/***************************
	 * 
	 * Save
	 * 
	 * Guarda un registro en base de datos.
	 * 
	 * @param AlertasNotificacionesUsuario
	 * @return Mensaje
	 * 
	 ***************************/

	@Override
	public AlertasNotificacionesUsuario save(AlertasNotificacionesUsuario entity) {
		return mensajeRepo.save(entity);
	}

	/***************************
	 * 
	 * findAlertasByUser
	 * 
	 * Devuelve un listado de alertas vinculadas al usuario
	 * 
	 * @param String
	 * @return List<Alerta>
	 * 
	 ***************************/

	@Override
	public List<Alerta> findAlertasByUser(String user) {
		List<AlertasNotificacionesUsuario> mensajesAlerta = mensajeRepo.findByUsuarioAndTipo(user,
				TipoMensajeEnum.ALERTA);
		List<Alerta> respuesta = new ArrayList<>();

		for (AlertasNotificacionesUsuario alertasNotificacionesUsuario : mensajesAlerta) {
			Alerta alerta = alertaService.findOne(alertasNotificacionesUsuario.getIdMensaje());
			respuesta.add(alerta);
		}
		return respuesta;
	}

	/***************************
	 * 
	 * findNotificacionesByUser
	 * 
	 * Devuelve un listado de notificaciones vinculadas al usuario
	 * 
	 * @param String
	 * @return List<Notificacion>
	 * 
	 ***************************/

	@Override
	public List<Notificacion> findNotificacionesByUser(String user) {
		List<AlertasNotificacionesUsuario> mensajesAlerta = mensajeRepo.findByUsuarioAndTipo(user,
				TipoMensajeEnum.NOTIFICACION);
		List<Notificacion> respuesta = new ArrayList<>();

		for (AlertasNotificacionesUsuario alertasNotificacionesUsuario : mensajesAlerta) {
			Notificacion alerta = notificacionService.findOne(alertasNotificacionesUsuario.getIdMensaje());
			respuesta.add(alerta);
		}
		return respuesta;
	}

	/***************************
	 * 
	 * grabarMensajeUsuario
	 * 
	 * Graba un mensaje (Alerta o Notificacion) vinculado a un usuario
	 * 
	 * @param Object
	 * @param String
	 * @return Mensaje
	 * 
	 ***************************/
	@Override
	public AlertasNotificacionesUsuario grabarMensajeUsuario(Object entidad, String user) {
		AlertasNotificacionesUsuario men = new AlertasNotificacionesUsuario();
		String nombre = entidad.getClass().getName().substring(entidad.getClass().getName().lastIndexOf('.') + 1);
		switch (nombre) {
		case "Alerta":
			men = rellenarMensaje((Alerta) entidad);
			break;
		case "Notificacion":
			men = rellenarMensaje((Notificacion) entidad);
			break;
		default:
		}
		men.setUsuario(user);
		men.setFechaAlta(new Date());
		mensajeRepo.save(men);
		return null;
	}

	/***************************
	 * 
	 * grabarMensajeJefeEquipo
	 * 
	 * Graba un mensaje (Alerta o Notificacion) vinculado al jefe de un equipo
	 * 
	 * @param Object
	 * @param Long
	 * @return Mensaje
	 * 
	 ***************************/
	@Override
	public AlertasNotificacionesUsuario grabarMensajeJefeEquipo(Object entidad, Inspeccion inspeccion) {
		return grabarMensajeUsuario(entidad, inspeccion.getEquipo().getJefeEquipo());
	}

	/***************************
	 * 
	 * grabarMensajeRol
	 * 
	 * Graba un mensaje (Alerta o Notificacion) vinculado a todos los usuarios pertenecientes a un mismo rol
	 * 
	 * @param Object
	 * @param RoleEnum
	 * 
	 ***************************/
	@Override
	public void grabarMensajeRol(Object entidad, RoleEnum rol) {
		List<User> usuariosRol = userService.findByfechaBajaIsNullAndRole(rol);
		for (User user : usuariosRol) {
			grabarMensajeUsuario(entidad, user.getUsername());
		}

	}

	/***************************
	 * 
	 * grabarMensajeRol
	 * 
	 * Graba un mensaje (Alerta o Notificacion) vinculado a todos los usuarios pertenecientes a una lista de roles
	 * 
	 * @param entidad Object
	 * @param roles List<RoleEnum>
	 * 
	 ***************************/

	@Override
	public void grabarMensajeRol(Object entidad, List<RoleEnum> roles) {
		for (RoleEnum rol : roles) {
			grabarMensajeRol(entidad, rol);
		}

	}

	/***************************
	 * 
	 * grabarMensajeEquipo
	 * 
	 * Graba un mensaje (Alerta o Notificacion) vinculado a todos los usuarios pertenecientes al equipo asignado a una
	 * inspección
	 * 
	 * @param Object
	 * @param Inspeccion
	 * 
	 ***************************/
	@Override
	public void grabarMensajeEquipo(Object entidad, Inspeccion inspeccion) {
		List<Miembro> miembrosEquipo = miembrosRepository.findByEquipo(inspeccion.getEquipo());

		for (Miembro miembro : miembrosEquipo) {
			grabarMensajeUsuario(entidad, miembro.getUsername());
		}

	}

	/***************************
	 * 
	 * rellenarMensaje
	 * 
	 * Recoge datos de un entidad Alerta para generar una entidad AlertasNotificacionesUsuario
	 * 
	 * @param Alerta
	 * @return AlertasNotificacionesUsuario
	 * 
	 ***************************/

	private AlertasNotificacionesUsuario rellenarMensaje(Alerta entidad) {
		AlertasNotificacionesUsuario men = new AlertasNotificacionesUsuario();
		men.setIdMensaje(entidad.getIdAlerta());
		men.setNombreSeccion(entidad.getNombreSeccion());
		men.setTipo(TipoMensajeEnum.ALERTA);
		return men;
	}

	/***************************
	 * 
	 * rellenarMensaje
	 * 
	 * Recoge datos de un entidad Notificacion para generar una entidad MensAlertasNotificacionesUsuarioaje
	 * 
	 * @param Notificacion
	 * @return AlertasNotificacionesUsuario
	 * 
	 ***************************/

	private AlertasNotificacionesUsuario rellenarMensaje(Notificacion entidad) {
		AlertasNotificacionesUsuario men = new AlertasNotificacionesUsuario();
		men.setIdMensaje(entidad.getIdNotificacion());
		men.setNombreSeccion(entidad.getNombreSeccion());
		men.setTipo(TipoMensajeEnum.NOTIFICACION);
		return men;
	}

	/***************************
	 * 
	 * Recupera la lista de notificaciones en función de una lista contenida en una lista de
	 * AlertasNotificacionesUsuario pasada como parámetro
	 * 
	 * @param lista List<AlertasNotificacionesUsuario>
	 * @return List<Notificacion>
	 * 
	 ***************************/

	@Override
	public List<Notificacion> findNotificaciones(List<AlertasNotificacionesUsuario> lista) {
		List<Notificacion> listaNotificaciones = new ArrayList<>();
		for (AlertasNotificacionesUsuario men : lista) {
			listaNotificaciones.add(notificacionService.findOne(men.getIdMensaje()));
		}
		return listaNotificaciones;
	}

	/***************************
	 * 
	 * Recupera la lista de alertas en función de una lista contenida en una lista de AlertasNotificacionesUsuario
	 * pasada como parámetro
	 * 
	 * @param lista List<AlertasNotificacionesUsuario>
	 * @return List<Alerta>
	 * 
	 ***************************/

	@Override
	public List<Alerta> findAlertas(List<AlertasNotificacionesUsuario> lista) {
		List<Alerta> listaAlertas = new ArrayList<>();
		for (AlertasNotificacionesUsuario men : lista) {
			listaAlertas.add(alertaService.findOne(men.getIdMensaje()));
		}
		return listaAlertas;
	}

	/***************************
	 * 
	 * Recupera un objeto Page conteniendo las AlertasNotificacionesUsuario asignadas a y del tipo pasados como
	 * parámetros
	 * 
	 * @param String usuario
	 * @param TipoMensajeEnum tipo
	 * @param Pageable request
	 * @return Page<AlertasNotificacionesUsuario>
	 * 
	 ***************************/

	@Override
	public Page<AlertasNotificacionesUsuario> findByUsuarioAndTipo(String usuario, TipoMensajeEnum tipo,
			Pageable request) {
		return mensajeRepo.findByUsuarioAndTipo(usuario, tipo, request);
	}

	/***************************
	 * 
	 * grabarMensajeEquipo
	 * 
	 * Graba un mensaje (Alerta o Notificacion) vinculado a todos los usuarios pertenecientes a un equipo
	 * 
	 * @param Object
	 * @param Equipo
	 * 
	 ***************************/

	@Override
	public void grabarMensajeEquipo(Object entidad, Equipo equipo) {
		List<Miembro> miembrosEquipo = miembrosRepository.findByEquipo(equipo);
		for (Miembro miembro : miembrosEquipo) {
			grabarMensajeUsuario(entidad, miembro.getUsername());
		}
	}

}
