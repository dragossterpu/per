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
	IRegistroActividadService regActividadService;

	@Autowired
	IGuiaPersonalizadaService guiaPersonalizadaService;

	/**
	 * Busca las guías según los filtros introducidos en el formulario de búsqueda
	 */
	public void buscarGuia() {

		List<GuiaPersonalizada> listaGuias = guiaPersonalizadaService.buscarGuiaPorCriteria(busqueda);
		busqueda.setListaGuias(listaGuias);
	}

	public String visualizaGuia(GuiaPersonalizada guiaPersonalizada) {
		this.guiaPersonalizada = guiaPersonalizada;
		listaPasos = guiaPersonalizadaService.listaPasos(guiaPersonalizada);
		return "/guias/visualizaGuiaPersonalizada?faces-redirect=true";
	}

	public void limpiarBusqueda() {
		busqueda.resetValues();
	}

	public void visualizaModelos() {
		listaGuiasPersonalizadas = guiaPersonalizadaService.findAll();
	}

	@PostConstruct
	public void init() {
		busqueda = new GuiaPersonalizadaBusqueda();
		list = new ArrayList<>();
		for (int i = 0; i <= 4; i++) {
			list.add(Boolean.TRUE);
		}
	}

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

	public void getFormularioBusqueda() {
		if ("menu".equalsIgnoreCase(this.vieneDe)) {
			limpiarBusqueda();
			this.vieneDe = null;
		}

	}

}
