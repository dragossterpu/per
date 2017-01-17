package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.util.PdfGenerator;
import es.mira.progesin.web.beans.ApplicationBean;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author EZENTIS Esta clase contiene todos los métodos necesarios para el tratamiento de los cuestionarios enviados a
 * partir de un cuestionario personalizado
 *
 */
@Setter
@Getter
@Controller("cuestionarioEnviadoBean")
@Scope("session")
public class CuestionarioEnviadoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String NOMBRESECCION = "Gestión de cuestionario enviado";

	private static final String ERROR = "Error";

	@Autowired
	private transient IRespuestaCuestionarioRepository respuestaRepository;

	@Autowired
	private CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda;

	@Autowired
	private VisualizarCuestionario visualizarCuestionario;

	private List<CuestionarioEnvio> listaCuestionarioEnvio;

	private String vieneDe;

	private String motivosNoConforme;

	private Date backupFechaLimiteCuestionario;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private transient ICuestionarioEnvioService cuestionarioEnvioService;

	@Autowired
	private EnvioCuestionarioBean envioCuestionarioBean;

	@Autowired
	private transient ICorreoElectronico correoElectronico;

	@Autowired
	transient IRegistroActividadService regActividadService;

	@Autowired
	transient INotificacionService notificacionService;

	@Autowired
	transient ApplicationBean applicationBean;

	@Autowired
	private transient PdfGenerator pdfGenerator;

	private StreamedContent pdfFile;

	public void buscarCuestionario() {
		listaCuestionarioEnvio = cuestionarioEnvioService
				.buscarCuestionarioEnviadoCriteria(cuestionarioEnviadoBusqueda);
	}

	/**
	 * Devuelve al formulario de búsqueda de modelos de cuestionario a su estado inicial y borra los resultados de
	 * búsquedas anteriores si se navega desde el menú u otra sección.
	 * 
	 * @author EZENTIS
	 */
	public void getFormBusquedaCuestionarios() {

		if ("menu".equalsIgnoreCase(this.vieneDe)) {
			limpiar();
			this.vieneDe = null;
		}

	}

	/**
	 * Resetea los valores de búsqueda introducidos en el formulario y el resultado de la búsqueda
	 */
	public void limpiar() {

		cuestionarioEnviadoBusqueda.limpiar();
		listaCuestionarioEnvio = null;

	}

	/**
	 * Elimina un cuestionario
	 * @param cuestionario Cuestionario a eliminar
	 */
	public void eliminarCuestionario(CuestionarioEnvio cuestionario) {
		cuestionario.setUsernameAnulacion(SecurityContextHolder.getContext().getAuthentication().getName());
		cuestionario.setFechaAnulacion(new Date());
		cuestionario.setFechaFinalizacion(cuestionario.getFechaAnulacion());
		cuestionarioEnvioService.transaccSaveElimUsuariosProv(cuestionario);
	}

	@PostConstruct
	public void init() {
		cuestionarioEnviadoBusqueda.limpiar();
		listaCuestionarioEnvio = new ArrayList<>();
	}

	/**
	 * Guarda fecha validación de las respuestas de las preguntas
	 * @see guardarRespuestas
	 *
	 */
	public void validarRespuestas() {
		try {
			Date fechaValidacion = new Date();
			CuestionarioEnvio cuestionario = visualizarCuestionario.getCuestionarioEnviado();
			List<RespuestaCuestionario> listaRespuestasTotales = visualizarCuestionario.getListaRespuestas();
			List<RespuestaCuestionario> listaRespuestasValidadas = new ArrayList<>();
			Map<PreguntasCuestionario, Boolean> mapaValidacionRespuestas = visualizarCuestionario
					.getMapaValidacionRespuestas();
			for (RespuestaCuestionario respuesta : listaRespuestasTotales) {
				if (mapaValidacionRespuestas.get(respuesta.getRespuestaId().getPregunta())) {

					respuesta.setFechaValidacion(fechaValidacion);
					listaRespuestasValidadas.add(respuesta);
				}
			}
			if (listaRespuestasValidadas.isEmpty() == Boolean.FALSE) {
				respuestaRepository.save(listaRespuestasValidadas);
				respuestaRepository.flush();
			}

			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Validación",
					"Se ha validado con éxito las respuestas");
			if (listaRespuestasValidadas.size() == listaRespuestasTotales.size()) {
				cuestionario.setFechaFinalizacion(new Date());
				String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
				cuestionario.setUsernameFinalizacion(usuarioActual);
				cuestionarioEnvioService.transaccSaveElimUsuariosProv(cuestionario);
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Finalización",
						"Cuestionario finalizado con éxito, todas sus respuestas han sido validadas");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR,
					"Se ha producido un error al validar las respuestas. ", e.getMessage());
			// TODO registro actividad
		}

	}

	/**
	 * Carga el formulario para declarar no conforme un cuestionario enviado ya cumplimentado.
	 * 
	 * @author EZENTIS
	 * @return vista noConformeCuestionario
	 */
	public String getFormNoConformeCuestionario() {
		motivosNoConforme = null;
		return "/cuestionarios/noConformeCuestionario";
	}

	/**
	 * Permite al jefe de equipo declarar no conforme un cuestionario enviado ya cumplimentada, después de revisar las
	 * respuestas y la documentación adjuntada por la unidad que se va a inspeccionar. Para ello se elimina la fecha de
	 * cumplimentación y reenvia el cuestionario al destinatario de la unidad con el motivo de dicha no conformidad.
	 * Adicionalmente reactiva los usuarios provisinales que se usaron para llevarlo a cabo.
	 * 
	 * @author EZENTIS
	 * @return vista validarCuestionario
	 */
	public String noConformeCuestionario() {
		try {
			CuestionarioEnvio cuestionario = visualizarCuestionario.getCuestionarioEnviado();
			cuestionario.setFechaCumplimentacion(null);
			cuestionario.setFechaNoConforme(new Date());
			String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
			cuestionario.setUsernameNoConforme(usuarioActual);
			if (cuestionarioEnvioService.transaccSaveActivaUsuariosProv(cuestionario)) {

				StringBuilder asunto = new StringBuilder("Cuestionario para la inspección ")
						.append(cuestionario.getInspeccion().getNumero());
				StringBuilder textoAutomatico = new StringBuilder(
						"\r\n \r\nSe ha declarado no conforme el cuestionario que usted envió por los motivos que se exponen a continuación:")
								.append("\r\n \r\n").append(motivosNoConforme)
								.append("\r\n \r\nPara solventarlo debe volver a conectarse a la aplicación PROGESIN. El enlace de acceso a la aplicación es ")
								.append(applicationBean.getMapaParametros().get("URLPROGESIN")
										.get(cuestionario.getInspeccion().getAmbito().name()))
								.append(", el usuario de acceso principal es su correo electrónico. El nombre del resto de usuarios y la contraseña para todos ellos constan en la primera comunicación que se le envió.")
								.append("\r\n \r\nEn caso de haber perdido dicha información póngase en contacto con el administrador de la aplicación a través del correo xxxxx@xxxx para solicitar una nueva contraseña.")
								.append("\r\n \r\nUna vez enviado el cuestionario cumplimentado todos los usuarios quedarán inactivos de nuevo. \r\n \r\n")
								.append("Muchas gracias y un saludo.");
				StringBuilder cuerpo = new StringBuilder("Motivo: ").append(cuestionario.getMotivoCuestionario())
						.append(textoAutomatico);
				correoElectronico.setDatos(cuestionario.getCorreoEnvio(), asunto.toString(), cuerpo.toString());
				correoElectronico.envioCorreo();

				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "No Conforme",
						"Declarado no conforme con éxito el cuestionario. El destinatario de la unidad será notificado y reactivado su acceso al sistema");

				String descripcion = asunto + ". Usuario no conforme : " + cuestionario.getUsernameNoConforme();

				regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						NOMBRESECCION);

				notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al declarar no conforme el cuestionario, inténtelo de nuevo más tarde");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
		return "/cuestionarios/noConformeCuestionario";
	}

	/**
	 * Pasa los datos de envio del cuestionario que queremos modificar al formulario de envio para que cambien los
	 * valores que quieran. En este caso sólo la fecha límite de cumplimentación y envio por parte de la unidad.
	 * 
	 * @author EZENTIS
	 * @param cuestionario recuperado del formulario
	 * @return vista enviarCuestionario
	 */
	public String getFormModificarCuestionario(CuestionarioEnvio cuestionario) {
		envioCuestionarioBean.setCuestionarioEnvio(cuestionario);
		backupFechaLimiteCuestionario = cuestionario.getFechaLimiteCuestionario();
		return "/cuestionarios/enviarCuestionario";
	}

	/**
	 * Modifica los datos de envio de un cuestionario
	 * 
	 * @author EZENTIS
	 */
	public String modificarCuestionario() {
		try {
			CuestionarioEnvio cuestionario = envioCuestionarioBean.getCuestionarioEnvio();
			cuestionarioEnvioService.save(cuestionario);
			String mensajeCorreoEnviado = "";
			// Avisar al destinatario si la fecha limite para la solicitud ha cambiado
			if (cuestionario.getFechaEnvio() != null
					&& !backupFechaLimiteCuestionario.equals(cuestionario.getFechaLimiteCuestionario())) {
				StringBuilder asunto = new StringBuilder("Cuestionario para la inspección ")
						.append(cuestionario.getInspeccion().getNumero());
				StringBuilder textoAutomatico = new StringBuilder(
						"\r\n \r\nEl plazo del que disponía para enviar el cuestionario conectándose a la aplicación PROGESIN ha sido modificado.")
								.append("\r\n \r\nFecha límite de envío anterior: ")
								.append(sdf.format(backupFechaLimiteCuestionario))
								.append("\r\nFecha límite de envío nueva: ")
								.append(sdf.format(cuestionario.getFechaLimiteCuestionario()))
								.append("\r\n \r\nMuchas gracias y un saludo.");
				String cuerpo = "Motivo: " + cuestionario.getMotivoCuestionario() + textoAutomatico;
				correoElectronico.setDatos(cuestionario.getCorreoEnvio(), asunto.toString(), cuerpo);
				correoElectronico.envioCorreo();
				mensajeCorreoEnviado = ". Se ha comunicado al destinatario de la unidad el cambio de fecha.";
			}
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
					"El cuestionario ha sido modificado con éxito" + mensajeCorreoEnviado);

			String descripcion = "Cuestionario. Usuario modificación : "
					+ SecurityContextHolder.getContext().getAuthentication().getName();

			regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
					NOMBRESECCION);

			notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al modificar la solicitud, inténtelo de nuevo más tarde");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
		return "/cuestionarios/modificarCuestionario";
	}

	public void crearPdfCuestionarioEnviado(CuestionarioEnvio cuestionarioEnviado) {
		try {
			setPdfFile(pdfGenerator.crearCuestionarioEnviado(cuestionarioEnviado));
		}
		catch (Exception e) {
			e.printStackTrace();
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error en la generación del PDF");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
	}

}
