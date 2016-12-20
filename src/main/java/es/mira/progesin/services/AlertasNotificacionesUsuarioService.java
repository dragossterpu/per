package es.mira.progesin.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.AlertasNotificacionesUsuario;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
import es.mira.progesin.persistence.repositories.IAlertasNotificacionesUsuarioRepository;

/********************
 * 
 * @author Ezentis
 * 
 *
 ********************/

@Service
public class AlertasNotificacionesUsuarioService implements IAlertasNotificacionesUsuarioService {
	
	
	@Autowired
	IAlertasNotificacionesUsuarioRepository mensajeRepo;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IAlertaService alertaService;
	
	@Autowired
	INotificacionService notificacionService;
	
	@Autowired
	IRegistroActividadService registroActividadService;
	
	@Autowired
	IEquipoService equipoService;
	
	@Autowired
	IInspeccionesService inspeccionService;


	/***************************
	 * 
	 * Delete
	 * 
	 * Elimina un registro de base de datos.
	 * El registro se identifica por su tipo, el id y el usuario vinculado
	 * 
	 * @param 	String 		Usuario
	 * @param 	Long 		Id 
	 * @param	TipoMensajeEnum		tipo	
	 * 
	 ***************************/
	@Override
	public void delete(String user, Long id, TipoMensajeEnum tipo) {
		AlertasNotificacionesUsuario men=mensajeRepo.findByUsuarioAndTipoAndIdMensaje(user,tipo,id);
		mensajeRepo.delete(men);
	
	}
	
	/***************************
	 * 
	 * Save
	 * 
	 * Guarda un registro en base de datos.
	 * 
	 * @param 	AlertasNotificacionesUsuario 	
	 * @return	Mensaje	
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
	 * @param 	String 	
	 * @return	List<Alerta>	
	 * 
	 ***************************/

	@Override
	public List<Alerta> findAlertasByUser(String user) {
		List<AlertasNotificacionesUsuario> mensajesAlerta= mensajeRepo.findByUsuarioAndTipo(user, TipoMensajeEnum.ALERTA);
		List<Alerta> respuesta=new ArrayList<Alerta>();
		
		for (AlertasNotificacionesUsuario alertasNotificacionesUsuario:mensajesAlerta){
			Alerta alerta=alertaService.findOne(alertasNotificacionesUsuario.getIdMensaje());
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
	 * @param 	String 	
	 * @return	List<Notificacion>	
	 * 
	 ***************************/

	@Override
	public List<Notificacion> findNotificacionesByUser(String user) {
		List<AlertasNotificacionesUsuario> mensajesAlerta= mensajeRepo.findByUsuarioAndTipo(user, TipoMensajeEnum.NOTIFICACION);
		List<Notificacion> respuesta=new ArrayList<Notificacion>();
		
		for (AlertasNotificacionesUsuario alertasNotificacionesUsuario:mensajesAlerta){
			Notificacion alerta=notificacionService.findOne(alertasNotificacionesUsuario.getIdMensaje());
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
	 * @param	Object
	 * @param 	String 	
	 * @return	Mensaje	
	 * 
	 ***************************/
	@Override
	public AlertasNotificacionesUsuario grabarMensajeUsuario(Object entidad, String user) {
		AlertasNotificacionesUsuario men= new AlertasNotificacionesUsuario();
		String nombre=entidad.getClass().getName().substring(entidad.getClass().getName().lastIndexOf('.')+1);
		switch (nombre){
		case "Alerta":
			men=rellenarMensaje((Alerta) entidad);
			break; 
		case "Notificacion": 
			men=rellenarMensaje((Notificacion) entidad);
			break; 
		default:	
		}
		men.setUsuario(user);
		mensajeRepo.save(men);
		return null;
	}
	
	
	/***************************
	 * 
	 * grabarMensajeJefeEquipo
	 * 
	 * Graba un mensaje (Alerta o Notificacion) vinculado al jefe de un equipo
	 * 
	 * @param	Object
	 * @param 	Long 	
	 * @return	Mensaje	
	 * 
	 ***************************/
	@Override
	public AlertasNotificacionesUsuario grabarMensajeJefeEquipo(Object entidad, Inspeccion inspeccion) {
		return grabarMensajeUsuario(entidad,inspeccion.getEquipo().getNombreJefe());
	}

	
	/***************************
	 * 
	 * grabarMensajeRol
	 * 
	 * Graba un mensaje (Alerta o Notificacion) vinculado a todos los usuarios pertenecientes a un mismo rol
	 * 
	 * @param	Object
	 * @param 	RoleEnum 	
	 * 
	 ***************************/
	@Override
	public void grabarMensajeRol(Object entidad, RoleEnum rol) {
		List<User> usuariosRol= userService.findByfechaBajaIsNullAndRole(rol);
		for(User user:usuariosRol){
			grabarMensajeUsuario(entidad,user.getUsername());	
		}
		
	}
	
	/***************************
	 * 
	 * grabarMensajeRol
	 * 
	 * Graba un mensaje (Alerta o Notificacion) vinculado a todos los usuarios pertenecientes a una lista de roles
	 * 
	 * @param	Object
	 * @param 	List<RoleEnum> 	
	 * 
	 ***************************/
	
	@Override
	public void grabarMensajeRol(Object entidad, List<RoleEnum> roles) {
		for(RoleEnum rol:roles){
			grabarMensajeRol(entidad,rol);
		}
		
	}

	/***************************
	 * 
	 * grabarMensajeEquipo
	 * 
	 * Graba un mensaje (Alerta o Notificacion) vinculado a todos los usuarios pertenecientes a equipo
	 * 
	 * @param	Object
	 * @param 	Long 		
	 * 
	 ***************************/
	@Override
	public void grabarMensajeEquipo(Object entidad, Inspeccion inspeccion) {
		List<Miembro> miembrosEquipo= inspeccion.getEquipo().getMiembros();
		
		for(Miembro miembro:miembrosEquipo){
			grabarMensajeUsuario(entidad,miembro.getUsername());
		}
		
	}
	
	/***************************
	 * 
	 * rellenarMensaje
	 * 
	 * Recoge datos de un entidad Alerta para generar una entidad AlertasNotificacionesUsuario
	 * 
	 * @param	Alerta	
	 * @return	AlertasNotificacionesUsuario	
	 * 
	 ***************************/
	
	private AlertasNotificacionesUsuario rellenarMensaje(Alerta entidad){
		AlertasNotificacionesUsuario men= new AlertasNotificacionesUsuario();
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
	 * @param	Notificacion	
	 * @return	AlertasNotificacionesUsuario	
	 * 
	 ***************************/
	
	private AlertasNotificacionesUsuario rellenarMensaje(Notificacion entidad){
		AlertasNotificacionesUsuario men= new AlertasNotificacionesUsuario();
		men.setIdMensaje(entidad.getIdNotificacion());
		men.setNombreSeccion(entidad.getNombreSeccion());
		men.setTipo(TipoMensajeEnum.NOTIFICACION);
		return men;
	}

	@Override
	public List<Notificacion> findNotificaciones(List<AlertasNotificacionesUsuario> lista) {
		List<Notificacion> listaNotificaciones=new ArrayList<Notificacion>();
		for(AlertasNotificacionesUsuario men:lista){
			listaNotificaciones.add(notificacionService.findOne(men.getIdMensaje()));
		}
		return listaNotificaciones;
	}

	public List<Alerta> findAlertas(List<AlertasNotificacionesUsuario> lista) {
		List<Alerta> listaAlertas=new ArrayList<Alerta>();
		for(AlertasNotificacionesUsuario men:lista){
			listaAlertas.add(alertaService.findOne(men.getIdMensaje()));
		}
		return listaAlertas;
	}
	
	@Override
	public Page<AlertasNotificacionesUsuario> findByUsuarioAndTipo(String usuario, TipoMensajeEnum tipo,
			PageRequest request) {
		return mensajeRepo.findByUsuarioAndTipo(usuario, tipo, request);
	}

	

	

	

}
