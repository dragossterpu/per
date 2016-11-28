package es.mira.progesin.util;

import java.io.IOException;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.services.IInspeccionesService;

@Component("facesUtilities")
public class FacesUtilities {

	@Autowired
	private IInspeccionesService inspeccionesService;

	/**
	 * 
	 * @param pagina
	 */
	public void redirect(String pagina) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();

		String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, pagina));

		try {
			extContext.redirect(url);
		}
		catch (IOException ioe) {
			throw new FacesException(ioe);
		}
	}

	/**
	 * 
	 * @param severity
	 * @param summary
	 * @param detail
	 */
	public static void setMensajeConfirmacionDialog(Severity severity, String summary, String detail) {
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(severity, summary, detail);
		FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
		context.execute("PF('dialogMessage').show()");
	}

	public static void setMensajeInformativo(Severity severity, String summary, String detail, String idMensaje) {
		FacesMessage message = new FacesMessage(severity, summary, detail);
		FacesContext.getCurrentInstance().addMessage(idMensaje, message);
	}

	/**
	 * Devuelve una lista con las inspecciones cuyo nombre de unidad o número contienen alguno de los caracteres pasado
	 * como parámetro. Se usa en los formularios de creación y modificación para el autocompletado.
	 * 
	 * @param inspeccion texto con parte del nombre de unidad o el número de la inspección que teclea el usuario en los
	 * formularios de creación y modificación
	 * @return Devuelve la lista de inspecciones que contienen algún caracter coincidente con el texto introducido
	 */
	public List<Inspeccion> autocompletarInspeccion(String infoInspeccion) {
		return inspeccionesService.buscarNoFinalizadaPorNombreUnidadONumero("%" + infoInspeccion + "%");
	}
}
