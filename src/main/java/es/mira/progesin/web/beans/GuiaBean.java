package es.mira.progesin.web.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IGuiaPersonalizadaService;
import es.mira.progesin.services.IGuiaService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.WordGenerator;
import lombok.Getter;
import lombok.Setter;

/*********************************************
 * Bean de guías
 * 
 * @author Ezentis
 * 
 *******************************************/

@Setter
@Getter
@Controller("guiaBean")
@Scope("session")
public class GuiaBean {

	private Guia guia;

	private String vieneDe;

	private GuiaBusqueda busqueda;

	private GuiaPasos pasoSeleccionado;

	private List<Boolean> list;

	private List<GuiaPasos> listaPasos;

	private List<GuiaPasos> listaPasosGrabar;

	private List<GuiaPasos> listaPasosSeleccionados;

	private StreamedContent file;

	boolean alta = false;

	@Autowired
	private WordGenerator wordGenerator;

	@Autowired
	private IGuiaService guiaService;

	@Autowired
	private IRegistroActividadService regActividadService;

	@Autowired
	private IGuiaPersonalizadaService guiaPersonalizadaService;

	/*********************************************************
	 * 
	 * Busca las guías según los filtros introducidos en el formulario de búsqueda
	 * 
	 *********************************************************/
	public void buscarGuia() {

		List<Guia> listaGuias = guiaService.buscarGuiaPorCriteria(busqueda);
		busqueda.setListaGuias(listaGuias);
	}

	/*********************************************************
	 * 
	 * Visualiza la guía pasada como parámetro redirigiendo a la vista "visualizaGuía"
	 * 
	 * @param guia Guia
	 * @return String
	 * 
	 *********************************************************/

	public String visualizaGuia(Guia guia) {
		this.guia = guia;
		listaPasos = guiaService.listaPasos(guia);
		return "/guias/visualizaGuia?faces-redirect=true";
	}

	/*********************************************************
	 * 
	 * Limpia los valores del objeto de búsqueda
	 * 
	 *********************************************************/

	public void limpiarBusqueda() {
		busqueda.resetValues();
	}

	/*********************************************************
	 * 
	 * Inicia el proceso de creación de una nueva guía redirigiendo a la vista "editarGuia"
	 * 
	 * @return String
	 * 
	 *********************************************************/

	public String nuevaGuia() {
		alta = true;
		this.guia = new Guia();
		listaPasos = new ArrayList<>();
		listaPasosGrabar = new ArrayList<>();
		return "/guias/editarGuia?faces-redirect=true";
	}

	/*********************************************************
	 * 
	 * Inicia el proceso de edición de una guía pasada como parámetro redirigiendo a la vista "editarGuia"
	 * 
	 * @param guia Guia
	 * @return String
	 * 
	 *********************************************************/

	public String editaGuia(Guia guia) {
		alta = false;
		this.guia = guia;
		listaPasos = guiaService.listaPasos(guia);
		listaPasosGrabar = guiaService.listaPasos(guia);
		return "/guias/editarGuia?faces-redirect=true";
	}

	/*********************************************************
	 * 
	 * Añade un nuevo paso a la guía
	 * 
	 * @param pregunta String
	 * 
	 *********************************************************/

	public void aniadePaso(String pregunta) {
		GuiaPasos nuevaPregunta = new GuiaPasos();
		nuevaPregunta.setPaso(pregunta);
		nuevaPregunta.setIdGuia(guia);
		listaPasos.add(nuevaPregunta);
		listaPasosGrabar.add(nuevaPregunta);
	}

	/*********************************************************
	 * 
	 * Elimina un paso de la guía.
	 * 
	 * En el caso de que el paso haya sido empleado en alguna guía personalizada la eliminación será lógica y se
	 * introducirá una fecha de baja. Si no ha sido usada en ninguna guía se eliminara físicamente de la base de datos
	 * 
	 * 
	 *********************************************************/

	public void borraPaso() {

		if (pasoSeleccionado.getId() != null) {
			// La pregunta existe en base de datos
			if (guiaService.existePaso(pasoSeleccionado)) {
				// baja lógica
				int index = listaPasos.indexOf(pasoSeleccionado);
				pasoSeleccionado.setFechaBaja(new Date());
				pasoSeleccionado.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
				listaPasosGrabar.set(index, pasoSeleccionado);
				listaPasos.remove(pasoSeleccionado);
			}
			else {// Baja física
				listaPasos.remove(pasoSeleccionado);
				listaPasosGrabar.remove(pasoSeleccionado);
			}
		}
		else {
			List<GuiaPasos> aux = new ArrayList<>();

			for (GuiaPasos paso : listaPasos) {
				if (paso != pasoSeleccionado) {
					aux.add(paso);
				}
			}
			listaPasos = aux;
			listaPasosGrabar = aux;
		}

	}

	/*********************************************************
	 * 
	 * Asigna a la variable "pasoSeleccionado" el valor seleccionado por el usuario en la vista
	 * 
	 * @param event SelectEvent
	 * 
	 *********************************************************/

	public void onSelectPaso(SelectEvent event) {
		pasoSeleccionado = (GuiaPasos) event.getObject();
	}

	/*********************************************************
	 * 
	 * Inicializa el bean
	 * 
	 *********************************************************/

	@PostConstruct
	public void init() {
		busqueda = new GuiaBusqueda();
		list = new ArrayList<>();
		for (int i = 0; i <= 4; i++) {
			list.add(Boolean.TRUE);
		}
	}

	/*********************************************************
	 * 
	 * Crea un documento Word a partir de una guía pasada como parámetro
	 * 
	 * @param guia Guia
	 * 
	 *********************************************************/

	public void crearDocumentoWordGuia(Guia guia) {
		try {
			setFile(wordGenerator.crearDocumentoGuia(guia));
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
					"Se ha producido un error en la generación del documento Word");
			regActividadService.altaRegActividadError(TipoRegistroEnum.ERROR.name(), e);
		}
	}

	/*********************************************************
	 * 
	 * Comprueba que el formulario está relleno. En caso afirmativo se grabará la guía en base de datos. En caso
	 * contrario se muestra un mensaje de error
	 * 
	 *********************************************************/

	public void guardaGuia() {
		boolean correcto = true;
		String mensajeError = "";
		if (guia.getNombre() == null || guia.getNombre().isEmpty()) {
			mensajeError = mensajeError.concat("\nEl nombre no puede estar en blanco");
			correcto = false;
		}
		if (listaPasos == null || listaPasos.isEmpty()) {
			mensajeError = mensajeError.concat("\nSe debe incluir al menos un paso en la guía");
			correcto = false;
		}
		if (correcto) {
			grabaGuia();
		}
		else {
			FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Error", mensajeError, null);

		}
	}

	/*********************************************************
	 * 
	 * Almacena en base de datos la guía
	 * 
	 *********************************************************/

	private void grabaGuia() {

		try {
			guia.setPasos(ordenaPasos(listaPasosGrabar));
			if (guiaService.guardaGuia(guia) != null) {
				if (alta) {
					FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
							"La guía se ha creado con éxito ");
					regActividadService.altaRegActividad(
							"La guía '".concat(guia.getNombre().concat("' ha sido creada")),
							TipoRegistroEnum.ALTA.name(), SeccionesEnum.GUIAS.getDescripcion());
				}
				else {
					FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificacion",
							"La guía ha sido modificada con éxito ");
					regActividadService.altaRegActividad(
							"La guía '".concat(guia.getNombre().concat("' ha sido modificada")),
							TipoRegistroEnum.MODIFICACION.name(), SeccionesEnum.GUIAS.getDescripcion());
				}

			}

		}
		catch (Exception e) {
			regActividadService.altaRegActividadError(TipoRegistroEnum.ERROR.name(), e);
		}
	}

	/*********************************************************
	 * 
	 * Ordena los pasos de una lista pasada como parámetro almacenando el orden en los objetos GuiaPaso contenidos en la
	 * misma
	 *
	 * @param lista List<GuiaPasos>
	 * @return List<GuiaPasos>
	 * 
	 *********************************************************/

	private List<GuiaPasos> ordenaPasos(List<GuiaPasos> lista) {
		List<GuiaPasos> listaNueva = new ArrayList<>();
		for (int i = 0; i < lista.size(); i++) {
			GuiaPasos paso = lista.get(i);
			paso.setOrden(i);
			listaNueva.add(paso);
		}
		return listaNueva;
	}

	/*********************************************************
	 * 
	 * Limpia el menú de búsqueda si se accede a la vista desde el menú lateral
	 * 
	 *********************************************************/

	public void getFormularioBusqueda() {
		if ("menu".equalsIgnoreCase(this.vieneDe)) {
			limpiarBusqueda();
			this.vieneDe = null;
		}

	}

	/*********************************************************
	 * 
	 * Inicia el proceso de creación de una guía personalizada y redirige a la vista de personalización
	 *
	 * @param guia Guia
	 * @return String
	 * 
	 *********************************************************/

	public String creaPersonalizada(Guia guia) {
		alta = false;
		this.guia = guia;
		listaPasosSeleccionados = new ArrayList<>();
		listaPasos = guiaService.listaPasos(guia);

		return "/guias/personalizarGuia?faces-redirect=true";
	}

	/*********************************************************
	 * 
	 * Almacena en BDD la guía personalizada
	 *
	 * @param nombre String
	 * 
	 *********************************************************/

	public void guardarPersonalizada(String nombre) {

		try {
			RequestContext.getCurrentInstance().execute("PF('guiaDialogo').hide()");
			if (!listaPasosSeleccionados.isEmpty()) {
				GuiaPersonalizada personalizada = new GuiaPersonalizada();
				personalizada.setNombreGuiaPersonalizada(nombre);
				personalizada.setGuia(guia);
				personalizada.setPasosElegidos(listaPasosSeleccionados);
				guiaPersonalizadaService.save(personalizada);
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Guía",
						"Se ha guardado su guía personalizada con éxito");
			}
			else {

				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No se puede crear la guía personalizada. Al menos debe seleccionarse un paso", "");
				FacesContext.getCurrentInstance().addMessage("message", message);
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
					"Se ha producido un error al guardar la guía personalizada");
			regActividadService.altaRegActividadError(SeccionesEnum.GUIAS.getDescripcion(), e);
		}
	}

}
