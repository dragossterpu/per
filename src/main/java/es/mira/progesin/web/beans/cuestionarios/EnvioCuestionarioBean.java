package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.model.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionarioId;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IConfiguracionRespuestasCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.util.Utilities;
import es.mira.progesin.web.beans.ApplicationBean;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean asociado a la pantalla de envío de cuestionarios
 * @author EZENTIS
 *
 */
@Setter
@Getter
@Controller("envioCuestionarioBean")
@Scope("session")
public class EnvioCuestionarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String ETIQUETA_ERROR = "mensajeerror";

	private static final String SECCION_ENVIAR_CUESTIONARIO = "ENVIO CUESTIONARIO";

	private CuestionarioEnvio cuestionarioEnvio;

	@Autowired
	private transient IInspeccionesService inspeccionService;

	@Autowired
	private transient ISolicitudDocumentacionService solDocService;

	@Autowired
	private transient IRegistroActividadService regActividadService;

	@Autowired
	private transient IUserService userService;

	@Autowired
	private transient PasswordEncoder passwordEncoder;

	@Autowired
	private ICuestionarioEnvioService cuestionarioEnvioService;

	@Autowired
	private ApplicationBean applicationBean;

	@Autowired
	private transient ICorreoElectronico correoElectronico;

	@Autowired
	private transient INotificacionService notificacionService;

	@Autowired
	private transient IPreguntaCuestionarioRepository preguntasRepository;

	@Autowired
	private transient IConfiguracionRespuestasCuestionarioRepository configRespuestas;

	/**
	 * Devuelve una lista con las inspecciones cuyo número contiene alguno de los caracteres pasados como parámetro. Se
	 * usa en el formulario de envío para el autocompletado.
	 * 
	 * @param numeroInspeccion Número de inspección que teclea el usuario en el formulario de envío o nombre de la
	 * unidad de la inspección
	 * @return Devuelve la lista de inspecciones que contienen algún caracter coincidente con el número introducido
	 */
	public List<Inspeccion> autocompletarInspeccion(String nombreUnidad) {
		return inspeccionService.buscarNoFinalizadaPorNombreUnidadONumeroSinCuestionarioNoFinalizado(nombreUnidad);
	}

	/**
	 * Completa los datos del formulario (correo, nombre, cargo, fecha límite) si el tipo de inspección asociada es de
	 * tipo General Periódica y tiene una solicitud de documentación previa finalizada.
	 */
	public void completarDatosSolicitudPrevia() {
		try {
			if ("I.G.P.".equals(cuestionarioEnvio.getInspeccion().getTipoInspeccion().getCodigo())) {
				List<SolicitudDocumentacionPrevia> listaSolicitudes = solDocService
						.findFinalizadasPorInspeccion(this.cuestionarioEnvio.getInspeccion());
				if (listaSolicitudes != null && listaSolicitudes.isEmpty() == Boolean.FALSE) {
					// Como está ordenado en orden descendente por fecha finalización, recupero la más reciente
					SolicitudDocumentacionPrevia solDocPrevia = listaSolicitudes.get(0);
					this.cuestionarioEnvio.setCorreoEnvio(solDocPrevia.getCorreoCorporativoInterlocutor());
					this.cuestionarioEnvio.setNombreUsuarioEnvio(solDocPrevia.getNombreCompletoInterlocutor());
					this.cuestionarioEnvio.setCargo(solDocPrevia.getCargoInterlocutor());
					this.cuestionarioEnvio.setFechaLimiteCuestionario(solDocPrevia.getFechaLimiteCumplimentar());
				}
				else {
					mostrarMensajeNoDocumentacionPrevia();
				}
			}
		}
		catch (Exception e) {
			regActividadService.altaRegActividadError(SECCION_ENVIAR_CUESTIONARIO, e);
		}
	}

	/**
	 * Guarda los datos introducidos en el formulario de envío en BBDD. Además crea 10 usuarios provisionales para el
	 * destinatario del correo. Se envía un correo electrónico informando de la URL de acceso a la aplicación y la
	 * contraseña de los 10 usuarios provisionales.
	 */
	public void enviarCuestionario() {
		try {
			// Comprobar que el usuario no tenga más de un cuestionario sin finalizar
			CuestionarioEnvio cuestionario = cuestionarioEnvioService
					.findNoFinalizadoPorCorreoEnvio(cuestionarioEnvio.getCorreoEnvio());
			// Comprobar que no existe un cuestionario enviado sin finalizar para esa inspección
			CuestionarioEnvio cuestionarioInspeccion = cuestionarioEnvioService
					.findNoFinalizadoPorInspeccion(cuestionarioEnvio.getInspeccion());

			if (cuestionario == null && cuestionarioInspeccion == null) {
				String password = Utilities.getPassword();
				System.out.println(password);
				List<User> listaUsuariosProvisionales = userService
						.crearUsuariosProvisionalesCuestionario(cuestionarioEnvio.getCorreoEnvio(), password);
				cuestionarioEnvioService.enviarCuestionarioService(listaUsuariosProvisionales, cuestionarioEnvio);
				enviarCorreoCuestionario(password, listaUsuariosProvisionales);
				// Notificaciones y registro de actividad
				crearNotificacionesYResgistro(cuestionarioEnvio.getInspeccion());
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "",
						"El cuestionario se ha enviado con éxito");
				// Crear respuestas tipo tabla (ñapa para que cuando inician sesíón varios usuarios a la vez por primera
				// vez, al grabar borrador no se repitan los datos de las tablas/matriz por cada usuario)
				crearResgistrosRespuestaTipoTablaMatriz(cuestionarioEnvio);
			}
			else {
				String textoError;
				if (cuestionario != null) {
					textoError = "El usuario con correo " + cuestionarioEnvio.getCorreoEnvio()
							+ " ya tiene otro cuestionario abierto. Finalícelo antes de enviar otro cuestionario.";

				}
				else {
					textoError = "Existe un cuestionario enviado para la inspección "
							+ cuestionarioEnvio.getInspeccion().getNumero()
							+ " sin finalizar. Debe finalizarlo primero antes de poder enviar otro cuestionario para la misma inspección";
				}
				FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, textoError, "", ETIQUETA_ERROR);
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
					"Se ha produdico un error en el envio del cuestionario", e.getMessage(), ETIQUETA_ERROR);
			regActividadService.altaRegActividadError(SECCION_ENVIAR_CUESTIONARIO, e);
		}
	}

	/**
	 * Muestra en el formulario un mensaje de error indiciando que no existe documentación previa finalizada para la
	 * inspección introducida
	 */
	private void mostrarMensajeNoDocumentacionPrevia() {
		String mensaje = "No se puede enviar el cuestionario ya que no existe documentación previa finalizada para la inspección. "
				+ "Debe finalizar la solicitud de documentación previa antes de poder enviar el cuestionario.";
		FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "", mensaje, ETIQUETA_ERROR);
	}

	/**
	 * Construye y envía el correo
	 * @param password Password de entrada a la aplicación para los usuarios provisionales
	 * @param usuarios Lista de usuarios provisionales
	 */
	private void enviarCorreoCuestionario(String password, List<User> usuarios) {
		String urlAcceso = applicationBean.getMapaParametros().get("URLPROGESIN")
				.get(cuestionarioEnvio.getInspeccion().getAmbito().name());

		StringBuilder textoAutomatico = new StringBuilder();
		textoAutomatico
				.append("\r\n \r\nPara responder el cuestionario debe conectarse a la aplicación PROGESIN. El enlace de acceso a la aplicación es '")
				.append(urlAcceso).append("'.\r\nLos usuarios de acceso a la aplicación son: \r\n\r\n");

		StringBuilder usuariosProvisionales = new StringBuilder();
		for (User usuario : usuarios) {
			usuariosProvisionales.append(usuario.getUsername()).append("\r\n");
		}
		textoAutomatico.append(usuariosProvisionales);
		textoAutomatico.append("\r\nLa contraseña de acceso para todos los usuarios es: ").append(password)
				.append(".\r\nSólo el usuario principal (").append(cuestionarioEnvio.getCorreoEnvio())
				.append(") podrá enviar el cuestionario, el resto de ")
				.append("usuarios solamente podrá guardar el cuestionario como borrador.")
				.append("\r\n \r\nUna vez enviado el cuestionario todos los usuarios quedarán inactivos. \r\n \r\n")
				.append("Muchas gracias y un saludo.");

		String cuerpo = cuestionarioEnvio.getMotivoCuestionario().concat("\r\n").concat(textoAutomatico.toString());
		String asunto = "Cuestionario para la inspección " + cuestionarioEnvio.getInspeccion().getNumero();
		try {
			correoElectronico.envioCorreo(cuestionarioEnvio.getCorreoEnvio(), asunto, cuerpo);
		}
		catch (Exception e) {
			FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
					"Se ha producido un error en el envio del correo electrónico", e.getMessage(), ETIQUETA_ERROR);
			regActividadService.altaRegActividadError("ENVIO CUESTIONARIO", e);
		}
	}

	/**
	 * Crea una notificación para el Jefe de Inspecciones y para el equipo de la inspección y registro de actividad
	 * @param inspeccion
	 */
	private void crearNotificacionesYResgistro(Inspeccion inspeccion) {
		String textoNotificaciones = "Enviado cuestionario para la inspección ".concat(inspeccion.getNumero());
		notificacionService.crearNotificacionRol(textoNotificaciones, SECCION_ENVIAR_CUESTIONARIO,
				RoleEnum.JEFE_INSPECCIONES);
		notificacionService.crearNotificacionEquipo(textoNotificaciones, SECCION_ENVIAR_CUESTIONARIO,
				cuestionarioEnvio.getInspeccion());
		regActividadService.altaRegActividad(textoNotificaciones, EstadoRegActividadEnum.ALTA.name(),
				SECCION_ENVIAR_CUESTIONARIO);
	}

	private void crearResgistrosRespuestaTipoTablaMatriz(CuestionarioEnvio cuestionarioEnvio) {
		try {
			List<PreguntasCuestionario> listaPreguntasTablaMatriz = preguntasRepository
					.findPreguntasElegidasTablaMatrizCuestionarioPersonalizado(
							cuestionarioEnvio.getCuestionarioPersonalizado().getId());
			List<RespuestaCuestionario> listaRespuestas = new ArrayList<>();
			List<DatosTablaGenerica> listaDatosTablaSave = new ArrayList<>();

			for (PreguntasCuestionario pregunta : listaPreguntasTablaMatriz) {
				List<DatosTablaGenerica> listaDatosTabla = new ArrayList<>();
				RespuestaCuestionario rtaCuestionario = new RespuestaCuestionario();
				RespuestaCuestionarioId idRespuesta = new RespuestaCuestionarioId();
				idRespuesta.setCuestionarioEnviado(cuestionarioEnvio);
				idRespuesta.setPregunta(pregunta);
				rtaCuestionario.setRespuestaId(idRespuesta);
				if (pregunta.getTipoRespuesta().startsWith("TABLA")) {
					DatosTablaGenerica dtg = new DatosTablaGenerica();
					dtg.setRespuesta(rtaCuestionario);
					listaDatosTabla.add(dtg);
				}
				else {
					crearRespuestaMatriz(pregunta, listaDatosTabla, rtaCuestionario);
				}

				rtaCuestionario.setRespuestaTablaMatriz(listaDatosTabla);
				listaRespuestas.add(rtaCuestionario);
				listaDatosTablaSave.addAll(listaDatosTabla);
			}
			cuestionarioEnvioService.transaccSaveConRespuestas(cuestionarioEnvio, listaRespuestas, listaDatosTablaSave);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void crearRespuestaMatriz(PreguntasCuestionario pregunta, List<DatosTablaGenerica> listaDatosTabla,
			RespuestaCuestionario rtaCuestionario) {
		List<ConfiguracionRespuestasCuestionario> listaFilas = configRespuestas
				.findFilasBySeccion(pregunta.getTipoRespuesta());
		for (ConfiguracionRespuestasCuestionario c : listaFilas) {
			DatosTablaGenerica dtg = new DatosTablaGenerica();
			dtg.setNombreFila(c.getConfig().getValor());
			dtg.setRespuesta(rtaCuestionario);
			listaDatosTabla.add(dtg);
		}
	}
}
