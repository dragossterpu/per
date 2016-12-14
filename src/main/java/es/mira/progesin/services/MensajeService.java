package es.mira.progesin.services;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Mensaje;
import es.mira.progesin.persistence.entities.Miembros;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
import es.mira.progesin.persistence.repositories.IMensajeRepository;

/********************
 * 
 * @author Ezentis
 * 
 *
 ********************/

@Service
public class MensajeService implements IMensajeService {
	
	
	@Autowired
	IMensajeRepository mensajeRepo;
	
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
		Mensaje men=mensajeRepo.findByUsuarioAndTipoAndIdMensaje(user,tipo,id);
		mensajeRepo.delete(men);
	
	}
	
	/***************************
	 * 
	 * Save
	 * 
	 * Guarda un registro en base de datos.
	 * 
	 * @param 	Mensaje 	
	 * @return	Mensaje	
	 * 
	 ***************************/
	
	@Override
	public Mensaje save(Mensaje entity) {
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
		List<Mensaje> mensajesAlerta= mensajeRepo.findByUsuarioAndTipo(user, TipoMensajeEnum.ALERTA);
		List<Alerta> respuesta=new ArrayList();
		
		for (Mensaje mensaje:mensajesAlerta){
			Alerta alerta=alertaService.findOne(mensaje.getIdMensaje());
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
		List<Mensaje> mensajesAlerta= mensajeRepo.findByUsuarioAndTipo(user, TipoMensajeEnum.NOTIFICACION);
		List<Notificacion> respuesta=new ArrayList();
		
		for (Mensaje mensaje:mensajesAlerta){
			Notificacion alerta=notificacionService.findOne(mensaje.getIdMensaje());
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
	public Mensaje grabarMensajeUsuario(Object entidad, String user) {
		Mensaje men= new Mensaje();
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
	public Mensaje grabarMensajeJefeEquipo(Object entidad, Long equipo) {
		return grabarMensajeUsuario(entidad,equipoService.buscarEquipo(equipo).getJefeEquipo());
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
		List<User> usuariosRol= userService.findByRole(rol);
		for(User user:usuariosRol){
			grabarMensajeUsuario(entidad,user.getUsername());	
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
	public void grabarMensajeEquipo(Object entidad, Long equipo) {
		List<Miembros> miembrosEquipo= equipoService.findByIdEquipo(equipo);
		
		for(Miembros miembro:miembrosEquipo){
			grabarMensajeUsuario(entidad,miembro.getUsername());
		}
		
	}
	
	/***************************
	 * 
	 * rellenarMensaje
	 * 
	 * Recoge datos de un entidad Alerta para generar una entidad Mensaje
	 * 
	 * @param	Alerta	
	 * @return	Mensaje	
	 * 
	 ***************************/
	
	private Mensaje rellenarMensaje(Alerta entidad){
		Mensaje men= new Mensaje();
		men.setIdMensaje(entidad.getIdAlerta());
		men.setNombreSeccion(entidad.getNombreSeccion());
		men.setTipo(TipoMensajeEnum.ALERTA);
		return men;
	}
	
	
	/***************************
	 * 
	 * rellenarMensaje
	 * 
	 * Recoge datos de un entidad Notificacion para generar una entidad Mensaje
	 * 
	 * @param	Notificacion	
	 * @return	Mensaje	
	 * 
	 ***************************/
	
	private Mensaje rellenarMensaje(Notificacion entidad){
		Mensaje men= new Mensaje();
		men.setIdMensaje(entidad.getIdNotificacion());
		men.setNombreSeccion(entidad.getNombreSeccion());
		men.setTipo(TipoMensajeEnum.NOTIFICACION);
		return men;
	}

	

}
