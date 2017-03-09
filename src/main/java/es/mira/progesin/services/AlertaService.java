package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IAlertaRepository;
import es.mira.progesin.util.ICorreoElectronico;

/**********************************************************************************
 * 
 * Implementación del servicio de alertas
 * 
 * @author Ezentis
 * 
 **********************************************************************************/

@Service
public class AlertaService implements IAlertaService {
	@Autowired
	private IAlertaRepository alertaRepository;

	@Autowired
	private IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;

	@Autowired
	private ICorreoElectronico correo;

	@Autowired
	private IUserService userService;

	@Autowired
	private IRegistroActividadService registroActividadService;

	/**********************************************************************************
	 * 
	 * Elimina de la base de datos una alerta cuyo id se recibe como parámetro
	 * 
	 * @param Long
	 * 
	 **********************************************************************************/

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		alertaRepository.delete(id);
	}

	/**********************************************************************************
	 * 
	 * Elimina de la base de datos todas las alertas
	 * 
	 * 
	 **********************************************************************************/

	@Override
	@Transactional(readOnly = false)
	public void deleteAll() {
		alertaRepository.deleteAll();
	}

	/**********************************************************************************
	 * 
	 * Comprueba si existe en de base de datos una alerta cuyo id se pasa como parámetro
	 * 
	 * @param Long
	 * 
	 * 
	 **********************************************************************************/

	@Override
	public boolean exists(Long id) {
		return alertaRepository.exists(id);
	}

	/**********************************************************************************
	 * 
	 * Busca en base de datos todas las alertas que no hayan sido dadas de baja
	 * 
	 **********************************************************************************/

	@Override
	public List<Alerta> findByFechaBajaIsNull() {
		return alertaRepository.findByFechaBajaIsNull();
	}

	/**********************************************************************************
	 * 
	 * Busca en base de datos las alertas cuyos id se recibe como parámetro
	 * 
	 * @param Iterable<Long>
	 * 
	 **********************************************************************************/

	public Iterable<Alerta> findAll(Iterable<Long> ids) {
		return alertaRepository.findAll(ids);
	}

	/**********************************************************************************
	 * 
	 * Busca en base de datos las alerta cuyo id se recibe como parámetro
	 * 
	 * @param Long
	 * 
	 **********************************************************************************/

	@Override
	public Alerta findOne(Long id) {
		return alertaRepository.findOne(id);
	}

	/**********************************************************************************
	 * 
	 * Guarda en base de datos la alerta recibida como parámetro.
	 * 
	 * @param Alerta
	 * @return Alerta
	 * 
	 **********************************************************************************/

	@Transactional(readOnly = false)
	private Alerta save(Alerta entity) {
		return alertaRepository.save(entity);
	}

	/**********************************************************************************
	 * 
	 * Crea una alerta a partir de la sección y la descripción que se reciben como parámetros
	 * 
	 * @param String seccion
	 * @param String descripcion
	 * @return Alerta
	 * 
	 **********************************************************************************/

	private Alerta crearAlerta(String seccion, String descripcion) {
		try {
			Alerta alerta = new Alerta();
			alerta.setDescripcion(descripcion);
			alerta.setNombreSeccion(seccion);
			return save(alerta);
		}
		catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}
		return null;
	}

	/**********************************************************************************
	 * 
	 * Crea una alerta y se asigna a u usuario. Se crea a partir de la sección, la descripción y el usuario que se
	 * reciben como parámetros
	 * 
	 * @param String seccion
	 * @param String descripcion
	 * @param String usuario
	 * 
	 **********************************************************************************/

	@Override
	public void crearAlertaUsuario(String seccion, String descripcion, String usuario) {
		try {
			Alerta alerta = crearAlerta(seccion, descripcion);
			User usu = userService.findOne(usuario);
			alertasNotificacionesUsuarioService.grabarMensajeUsuario(alerta, usuario);
			correo.envioCorreo(usu.getCorreo(), "Nueva alerta PROGESIN",
					"Se ha generado una nueva alerta en la aplicacion PROGESIN:\n " + descripcion);

		}
		catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}

	}

	/**********************************************************************************
	 * 
	 * Crea una alerta y se asigna a un rol. Se crea a partir de la sección, la descripción y el rol que se reciben como
	 * parámetros
	 * 
	 * @param String seccion
	 * @param String descripcion
	 * @param RoleEnum rol
	 * 
	 **********************************************************************************/

	@Override
	public void crearAlertaRol(String seccion, String descripcion, RoleEnum rol) {
		try {
			List<User> usuariosRol = userService.findByfechaBajaIsNullAndRole(rol);
			for (User usuario : usuariosRol) {
				crearAlertaUsuario(seccion, descripcion, usuario.getUsername());
			}
		}
		catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}

	}

	/**********************************************************************************
	 * 
	 * Crea una alerta y se asigna a varios roles. Se crea a partir de la sección, la descripción y la lista de roles
	 * que se reciben como parámetros
	 * 
	 * @param String seccion
	 * @param String descripcion
	 * @param List<RoleEnum> roles
	 * 
	 **********************************************************************************/

	@Override
	public void crearAlertaRol(String seccion, String descripcion, List<RoleEnum> roles) {
		for (RoleEnum rol : roles) {
			crearAlertaRol(seccion, descripcion, rol);
		}

	}

	/**********************************************************************************
	 * 
	 * Crea una alerta y se asigna a un equipo de inspección. Se crea a partir de la sección, la descripción y la
	 * inspección que se reciben como parámetros
	 * 
	 * @param String seccion
	 * @param String descripcion
	 * @param Inspeccion inspeccion
	 * 
	 **********************************************************************************/

	@Override
	public void crearAlertaEquipo(String seccion, String descripcion, Inspeccion inspeccion) {
		try {
			List<Miembro> miembrosEquipo = inspeccion.getEquipo().getMiembros();
			for (Miembro miembro : miembrosEquipo) {
				crearAlertaUsuario(seccion, descripcion, miembro.getUsername());
			}

		}
		catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}

	}

	/**********************************************************************************
	 * 
	 * Crea una alerta y se asigna al jefe de un equipo asignado a una inspección. Se crea a partir de la sección, la
	 * descripción y la inspección que se reciben como parámetros
	 * 
	 * @param String seccion
	 * @param String descripcion
	 * @param Inspeccion inspeccion
	 * 
	 **********************************************************************************/

	@Override
	public void crearAlertaJefeEquipo(String seccion, String descripcion, Inspeccion inspeccion) {
		try {
			crearAlertaUsuario(seccion, descripcion, inspeccion.getEquipo().getJefeEquipo());

		}
		catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}

	}

}
