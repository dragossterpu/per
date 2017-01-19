package es.mira.progesin.services;

import java.io.FileNotFoundException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IAlertaRepository;
import es.mira.progesin.persistence.repositories.IUserRepository;
import es.mira.progesin.util.ICorreoElectronico;

@Service
public class AlertaService implements IAlertaService {
	@Autowired
	IAlertaRepository alertaRepository;
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;
	
	@Autowired
	ICorreoElectronico correo;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IRegistroActividadService registroActividadService;
	
	@Autowired 
	IEquipoService equipoService;

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		alertaRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAll() {
		alertaRepository.deleteAll();
	}

	@Override
	public boolean exists(Long id) {
		return alertaRepository.exists(id);
	}

	@Override
	public List<Alerta> findByFechaBajaIsNull() {
		return alertaRepository.findByFechaBajaIsNull();
	}

	public Iterable<Alerta> findAll(Iterable<Long> ids) {
		return alertaRepository.findAll(ids);
	}

	@Override
	public Alerta findOne(Long id) {
		return alertaRepository.findOne(id);
	}

	
	@Transactional(readOnly = false)
	private Alerta save(Alerta entity) {
		return alertaRepository.save(entity);
	}


	private Alerta crearAlerta(String seccion, String descripcion) {
		try {
			Alerta alerta=new Alerta();
			alerta.setDescripcion(descripcion);
			alerta.setNombreSeccion(seccion);
			return save(alerta);
		} catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}
		return null;
	}

	@Override
	public void crearAlertaUsuario(String seccion, String descripcion, String usuario) {
		try {
			Alerta alerta=crearAlerta(seccion, descripcion);
			User usu=userService.findOne(usuario);	
			alertasNotificacionesUsuarioService.grabarMensajeUsuario(alerta, usuario);
			correo.envioCorreo(usu.getCorreo(), "Nueva alerta PROGESIN", "Se ha generado una nueva alerta en la aplicacion PROGESIN:\n " + descripcion);
			
		} catch (MailException | FileNotFoundException | MessagingException e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}
		
	}

	@Override
	public void crearAlertaRol(String seccion, String descripcion, RoleEnum rol) {
		try {
			List<User> usuariosRol= userService.findByfechaBajaIsNullAndRole(rol);
			for(User usuario:usuariosRol){
				crearAlertaUsuario(seccion, descripcion,usuario.getUsername());
			}
		} catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}
		
	}

	@Override
	public void crearAlertaRol(String seccion, String descripcion, List<RoleEnum> roles) {
		for(RoleEnum rol:roles){
			crearAlertaRol(seccion, descripcion, rol);
		}
		
	}
	@Override
	public void crearAlertaEquipo(String seccion, String descripcion, Inspeccion inspeccion) {
		try {
			List<Miembro> miembrosEquipo= inspeccion.getEquipo().getMiembros();
			for(Miembro miembro:miembrosEquipo){
				crearAlertaUsuario(seccion, descripcion,miembro.getUsername());
			}

		} catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}
		
	}

	@Override
	public void crearAlertaJefeEquipo(String seccion, String descripcion, Inspeccion inspeccion) {
		try {
			crearAlertaUsuario(seccion, descripcion,inspeccion.getEquipo().getNombreJefe());

		} catch (Exception e) {
			registroActividadService.altaRegActividadError(seccion, e);
		}
		
	}

	


	
	

}
