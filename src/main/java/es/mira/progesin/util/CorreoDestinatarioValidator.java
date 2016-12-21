package es.mira.progesin.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.web.beans.SolicitudDocPreviaBean;

/**
 * @author EZENTIS
 */
@Component("correoDestinatarioValidator")
public class CorreoDestinatarioValidator implements Validator {

	@Autowired
	ISolicitudDocumentacionService solicitudDocumentacionService;

	@Autowired
	SolicitudDocPreviaBean solicitudDocPreviaBean;

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		String nuevoCorreoDestinatario = value.toString();

		String actualCorreoDestinatario = solicitudDocPreviaBean.getSolicitudDocumentacionPrevia()
				.getCorreoDestinatario();

		if (!nuevoCorreoDestinatario.equals(actualCorreoDestinatario) && solicitudDocumentacionService
				.findByFechaFinalizacionIsNullAndCorreoDestinatario(nuevoCorreoDestinatario) != null) {
			FacesMessage facesMsg = new FacesMessage(
					"Este correo ya existe para otra solicitud en curso. Debe finalizarla o eliminarla antes de proseguir.");
			facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(facesMsg);
		}

	}

}
