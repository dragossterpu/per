package es.mira.progesin.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IGuiaPersonalizadaService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.WordGenerator;
import lombok.Getter;
import lombok.Setter;

/*********************************************
 * Bean de guías personalizadas
 * 
 * @author Ezentis
 * 
 *******************************************/

@Setter
@Getter
@Controller("guiaPersonalizadaBean")
@Scope("session")

public class GuiaPersonalizadaBean {

	private GuiaPersonalizada guiaPersonalizada;

	private String vieneDe;

	private GuiaPersonalizadaBusqueda busqueda;

	private GuiaPasos pasoSeleccionada;

	private List<Boolean> list;

	private List<GuiaPasos> listaPasos;

	private List<GuiaPasos> listaPasosSeleccionados;

	private List<GuiaPersonalizada> listaGuiasPersonalizadas;

	private transient StreamedContent file;

	boolean alta = false;

	@Autowired
	private transient WordGenerator wordGenerator;

	@Autowired
	private IRegistroActividadService regActividadService;

	@Autowired
	private IGuiaPersonalizadaService guiaPersonalizadaService;

	/**************************************************************
	 * 
	 * Busca las guías según los filtros introducidos en el formulario de búsqueda
	 * 
	 **************************************************************/
	public void buscarGuia() {

		List<GuiaPersonalizada> listaGuias = guiaPersonalizadaService.buscarGuiaPorCriteria(busqueda);
		busqueda.setListaGuias(listaGuias);
	}

	/*********************************************************
	 * 
	 * Visualiza la guía personalizada pasada como parámetro redirigiendo a la vista "visualizaGuíaPersonalizada"
	 * 
	 * @param GuiaPersonalizada
	 * @return String
	 * 
	 *********************************************************/

	public String visualizaGuia(GuiaPersonalizada guiaPersonalizada) {
		this.guiaPersonalizada = guiaPersonalizada;
		listaPasos = guiaPersonalizadaService.listaPasos(guiaPersonalizada);
		return "/guias/visualizaGuiaPersonalizada?faces-redirect=true";
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
	 * Borra de base de datos una guía personalizada pasada como parámetro
	 * 
	 * @param GuiaPersonalizada
	 * 
	 *********************************************************/

	public void eliminar(GuiaPersonalizada guiaPersonalizada) {
		guiaPersonalizadaService.eliminar(guiaPersonalizada);
	}

	/*********************************************************
	 * 
	 * Inicializa el bean
	 * 
	 *********************************************************/

	@PostConstruct
	public void init() {
		busqueda = new GuiaPersonalizadaBusqueda();
		list = new ArrayList<>();
		for (int i = 0; i <= 4; i++) {
			list.add(Boolean.TRUE);
		}
	}

	/*********************************************************
	 * 
	 * Crea un documento Word a partir de una guía personalizada pasada como parámetro
	 * 
	 * @param GuiaPersonalizada
	 * 
	 *********************************************************/

	public void crearDocumentoWordGuia(GuiaPersonalizada guia) {
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
	 * Limpia el menú de búsqueda si se accede a la vista desde el menú lateral
	 * 
	 *********************************************************/

	public void getFormularioBusqueda() {
		if ("menu".equalsIgnoreCase(this.vieneDe)) {
			limpiarBusqueda();
			this.vieneDe = null;
		}

	}

}
