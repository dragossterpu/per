package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.persistence.repositories.IRegActividadRepository;
import es.mira.progesin.services.IRegistroActividadService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Controller("regActividadBean")
@Scope("session")
public class RegActividadBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<RegistroActividad> listaRegActividad;

	private final String NOMBRESECCION = "Registros de actividad";

	private List<Boolean> list;

	private RegistroActividad regActividad;
	private RegistroActividad error;

	private Integer numColListRegActividad = 5;

	private RegActividadBusqueda regActividadBusqueda;

	private String vieneDe;

	@Autowired
	IRegActividadRepository regActividadRepository;

	@Autowired
	IRegistroActividadService regActividadService;

	@Autowired
	ApplicationBean applicationBean;

	public void buscarRegActividad() {
		List<RegistroActividad> listaRegActividad = regActividadService
				.buscarRegActividadCriteria(regActividadBusqueda);
		regActividadBusqueda.setListaRegActividad(listaRegActividad);
	}

	public void onToggle(ToggleEvent e) {
		list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	public void getFormularioRegActividad() {
		if ("menu".equalsIgnoreCase(this.vieneDe)) {
			regActividadBusqueda.resetValues();
			this.vieneDe = null;
		}

	}

	@PostConstruct
	public void init() {
		regActividadBusqueda = new RegActividadBusqueda();
		list = new ArrayList<>();
		for (int i = 0; i <= numColListRegActividad; i++) {
			list.add(Boolean.TRUE);
		}
	}

	public List<String> autocompletarSeccion(String infoSeccion) {
		return regActividadService.buscarPorNombreSeccion("%" + infoSeccion + "%");
	}

	public List<String> autocompletarUsuario(String infoUsuario) {
		return regActividadService.buscarPorUsuarioRegistro("%" + infoUsuario + "%");
	}

	
	public void setSelected(RegistroActividad selected) {
        this.regActividad = selected;
    }
	
	public void onRowSelect(SelectEvent event){
		error=new RegistroActividad();
		error= (RegistroActividad) event.getObject();
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("textoError");
		context.execute("PF('dlg').show();");

		
	}
}
