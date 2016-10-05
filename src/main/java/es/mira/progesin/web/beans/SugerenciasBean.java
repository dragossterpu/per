package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.Sugerencia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.services.ISugerenciaService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.SendSimpleMail;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("sugerenciasBean")
@RequestScoped
// @Scope(FacesViewScope.NAME)
public class SugerenciasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	static Logger LOG = Logger.getLogger(SugerenciasBean.class);

	private User user;

	private String modulo;

	private String descripcion;

	private String contestacion;

	private Sugerencia sugerencia;

	private List<Sugerencia> sugerenciasListado;

	@Autowired
	ISugerenciaService sugerenciaService;

	@Autowired
	IUserService userService;

	public String guardarSugerencia() {
		if (modulo.equals("")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Es obligatorio elegir un modulo", ""));
			return null;
		}
		else if (descripcion == null || descripcion.length() < 10) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "La descrición debe ser mayor de 10 caracteres", ""));
			return null;
		}
		else {
			Date fecha = new Date();
			Sugerencia sugerencia = new Sugerencia();
			user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			sugerencia.setModulo(modulo);
			sugerencia.setDescripcion(descripcion);
			sugerencia.setFechaAlta(fecha);
			sugerencia.setFechaBaja(null);
			sugerencia.setUsuario(user.getUsername());
			sugerenciaService.save(sugerencia);
			System.out.println("modulo" + modulo);
			System.out.println("descripcion" + descripcion);
			return "/index";
		}
	}

	/**
	 * Método que nos lleva al listado de sugerencias Se llama desde el menu lateral
	 * @return
	 */
	public String sugerenciasListado() {

		sugerenciasListado = (List<Sugerencia>) sugerenciaService.findAll();
		return "/principal/sugerenciasListado";
	}

	public void eliminarSugerencia(Integer idSugerencia) {
		Sugerencia sugerencia = sugerenciaService.findOne(idSugerencia);
		user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Date fecha = new Date();
		sugerencia.setFechaBaja(fecha);
		sugerencia.setUsuarioBaja(user.getUsername());
		sugerenciaService.save(sugerencia);
		this.sugerenciasListado = null;
		sugerenciasListado = (List<Sugerencia>) sugerenciaService.findAll();
	}

	public String contestarSugerencia(Integer idSugerencia) throws MessagingException {
		sugerencia = sugerenciaService.findOne(idSugerencia);
		user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return "/principal/contestarSugerencia";
	}

	public String contestar(Integer idSugerencia, String contestacion) throws MessagingException {
		sugerencia = sugerenciaService.findOne(idSugerencia);
		user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String asunto = "Respuesta a su sugerencia";
		String usuarioContestacion = sugerencia.getUsuario();
		user = userService.findOne(usuarioContestacion);
		String correoEnvio = user.getCorreo();
		String nombre = user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2();
		// SendMailwithAttachment.sendMailWithAttachment( );
		SendSimpleMail.sendMail(asunto, correoEnvio, nombre, contestacion);
		return "/principal/sugerenciasListado";
	}

	public void resetFail() {
		this.modulo = null;
		this.descripcion = null;
	}

	@PostConstruct
	public void init() throws MessagingException {

		sugerenciasListado = (List<Sugerencia>) sugerenciaService.findAll();

		// contestarSugerencia( 1);

	}

}
