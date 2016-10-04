package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.services.ICuerpoEstadoService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean para la administración de los cuerpos de estado
 * @author sperezp
 *
 */
@Setter
@Getter
@Component("cuerposEstadoBean")
@Scope(FacesViewScope.NAME)
public class CuerposEstadoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<CuerpoEstado> listaCuerposEstado;

	private CuerpoEstado cuerpo;

	private String cuerpoNuevo;

	@Autowired
	ICuerpoEstadoService cuerposEstadoService;

	/**
	 * Eliminación lógica (se pone fecha de baja) de un cuerpo del estado
	 * @param cuerpo cuerpo del estado a eliminar
	 */
	public void eliminarCuerpo(CuerpoEstado cuerpo) {
		cuerpo.setFechaBaja(new Date());
		cuerpo.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
		cuerposEstadoService.save(cuerpo);
		listaCuerposEstado.remove(cuerpo);
	}

	/**
	 * Da de alta un nuevo cuerpo del estado
	 */
	public void altaCuerpo() {
		CuerpoEstado cuerpoEstado = new CuerpoEstado();
		cuerpoEstado.setDescripcion(cuerpoNuevo);
		if (cuerposEstadoService.save(cuerpoEstado) != null) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
					"El cuerpo ha sido creado con éxito");
		}
		else {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Error",
					"Se ha producido un error al dar de alta el cuerpo de estado, inténtelo de nuevo más tarde");
		}
	}

	/**
	 * Modificación de la descripción de un cuerpo
	 * @param event
	 */
	public void onRowEdit(RowEditEvent event) {
		CuerpoEstado cuerpoEstado = (CuerpoEstado) event.getObject();
		cuerposEstadoService.save(cuerpoEstado);
		FacesMessage msg = new FacesMessage("Cuerpo de estado modificado", cuerpoEstado.getDescripcion());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * Cancela la edición de un cuerpo del estado
	 * @param event
	 */
	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Modificación cancelada",
				((CuerpoEstado) event.getObject()).getDescripcion());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * Médodo usado para inicializar la lista de cuerpos de estado que se mostrarán en la página
	 */
	@PostConstruct
	public void init() {
		listaCuerposEstado = cuerposEstadoService.findByFechaBajaIsNull();
	}
}
