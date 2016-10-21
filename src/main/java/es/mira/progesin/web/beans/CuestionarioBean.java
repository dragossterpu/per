package es.mira.progesin.web.beans;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.DatosJasper;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.PreEnvioCuest;
import es.mira.progesin.persistence.entities.RegActividad;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.services.IModeloCuestionarioService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegActividadService;
import es.mira.progesin.util.DescargasHelper;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("cuestionarioBean")
@SessionScoped

public class CuestionarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	RegActividad regActividad = new RegActividad();

	private final String NOMBRESECCION = "Generación de plantilla jasper";

	@Autowired
	IRegActividadService regActividadService;

	@Autowired
	INotificacionService notificacionService;

	@Autowired
	IModeloCuestionarioService modeloCuestionarioService;

	List<PreEnvioCuest> listadoPreEnvioCuestionarios;

	private StreamedContent file;

	private DatosJasper model;

	// Url de la plantilla jasper
	private static final String RUTA_JASPER = "jasper/gcZonaPluriprovincial.jasper";

	public String creaCuestionario() {

		return "/cuestionarios/preenvio";

	}

	public String execute() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(true);
		final DatosJasper datosjasper = new DatosJasper();
		datosjasper.setNombre("GC_ZONA_PLURI_PROVINCIAL");
		datosjasper.setUrl(RUTA_JASPER);
		try {
			DescargasHelper.preparaDescargaJasper(datosjasper, session);
			String descripcion = "Creación de nuevo cuestionario previo. Nombre del cuestionario"
					+ datosjasper.getNombre() + " Usuario creación : "
					+ SecurityContextHolder.getContext().getAuthentication().getName();
			// Guardamos la actividad en bbdd
			saveReg(descripcion, EstadoRegActividadEnum.ALTA.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());

			// Guardamos la notificacion en bbdd
			saveNotificacion(descripcion, EstadoRegActividadEnum.ALTA.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());
		}
		catch (Exception e) {
			// Guardamos loe posibles errores en bbdd
			altaRegActivError(e);
		}
		return null;

	}

	public String limpiar() {
		// this.model = null;
		return "/cuestionarios/preenvio";
	}

	@PostConstruct
	public void init() {

		// insertar();
		listadoPreEnvioCuestionarios = modeloCuestionarioService.findAllPre();

	}

	public void insertar() {
		try {

			File directory = new File("C:\\Users\\Admin\\Desktop\\Documentación IPSS\\preenvioCuestionarios");

			File[] listaFicheros = directory.listFiles();
			for (int i = 0; i < listaFicheros.length; i++) {
				File fichero = listaFicheros[i];

				// Obtiene el contenido del fichero en []bytes
				byte[] data = Files.readAllBytes(fichero.toPath());
				PreEnvioCuest cuestionario = new PreEnvioCuest();
				cuestionario.setCodigo("codigo");
				cuestionario.setDescripcion(
						fichero.getName().substring(0, fichero.getName().lastIndexOf('.')).toUpperCase());
				cuestionario.setNombreFichero(fichero.getName());
				System.out.println(fichero.getName().substring(fichero.getName().lastIndexOf('.') + 1));
				cuestionario.setExtension(
						fichero.getName().substring(fichero.getName().lastIndexOf('.') + 1).toLowerCase());
				// Blob fichero = Hibernate.getLobCreator(sessionFactory.openSession()).createBlob(data);
				cuestionario.setFichero(data);
				modeloCuestionarioService.savePre(cuestionario);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que recoge los valores introducidos en el formulario y da de alta un equipo normal en la BBDD
	 * @return
	 */
	public String enviarPreCuestionario(PreEnvioCuest cuestionario) {

		return "/cuestionarios/preenvio";
	}

	public void descargarFichero(PreEnvioCuest cuestionario) {
		try {
			InputStream stream = new ByteArrayInputStream(cuestionario.getFichero());
			String contentType = "application/msword";
			if ("pdf".equals(cuestionario.getExtension())) {
				contentType = "application/pdf";
			}
			else if (cuestionario.getExtension().startsWith("xls")) {
				contentType = "application/x-msexcel";
			}
			file = new DefaultStreamedContent(stream, contentType, cuestionario.getNombreFichero());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ************* Alta mensajes de notificacion, regActividad y alertas Progesin ********************
	/**
	 * @param descripcion
	 * @param tipoReg
	 * @param username
	 */
	private void saveReg(String descripcion, String tipoReg, String username) {
		RegActividad regActividad = new RegActividad();
		regActividad.setTipoRegActividad(tipoReg);
		regActividad.setUsernameRegActividad(username);
		regActividad.setFechaAlta(new Date());
		regActividad.setNombreSeccion(NOMBRESECCION);
		regActividad.setDescripcion(descripcion);
		regActividadService.save(regActividad);
	}

	/**
	 * @param descripcion
	 * @param tipoReg
	 * @param username
	 */
	private void saveNotificacion(String descripcion, String tipoNotificacion, String username) {
		Notificacion notificacion = new Notificacion();
		notificacion.setTipoNotificacion(tipoNotificacion);
		notificacion.setUsernameNotificacion(username);
		notificacion.setNombreSeccion(NOMBRESECCION);
		notificacion.setFechaAlta(new Date());
		notificacion.setDescripcion(descripcion);
		notificacionService.save(notificacion);
	}

	/**
	 * @param e
	 */
	private void altaRegActivError(Exception e) {
		regActividad.setTipoRegActividad(EstadoRegActividadEnum.ERROR.name());
		String message = Utilities.messageError(e);
		regActividad.setFechaAlta(new Date());
		regActividad.setNombreSeccion(NOMBRESECCION);
		regActividad.setUsernameRegActividad(SecurityContextHolder.getContext().getAuthentication().getName());
		regActividad.setDescripcion(message);
		regActividadService.save(regActividad);
	}
	// ************* Alta mensajes de notificacion, regActividad y alertas Progesin END********************
}
