package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnviado;
import es.mira.progesin.persistence.repositories.IInspeccionesRepository;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean asociado a la pantalla de envío de cuestionarios
 * @author EZENTIS
 *
 */
@Setter
@Getter
@Component("envioCuestionarioBean")
public class EnvioCuestionarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private CuestionarioEnviado cuestionarioEnvio;

	@Autowired
	private IInspeccionesRepository inspeccionRepository;

	@Autowired
	private ISolicitudDocumentacionService solDocService;

	/**
	 * Método que devuelve una lista con las inspecciones cuyo número contienen alguno de los caracteres pasado como
	 * parámetro. Se usa en el formulario de envío para el autocompletado.
	 * 
	 * @param numeroInspeccion Número de inspección que teclea el usuario en el formulario de envío
	 * @return Devuelve la lista de inspecciones que contienen algún caracter coincidente con el número introducido
	 */
	public List<Inspeccion> autocompletarInspeccion(String numeroInspeccion) {
		return inspeccionRepository.findByNumeroLike("%" + numeroInspeccion + "%");
	}

	/**
	 * 
	 */
	public void enviarCuestionario() {
		SolicitudDocumentacionPrevia solDocPrevia = solDocService
				.findSolicitudDocumentacionFinalizadaPorInspeccion(this.cuestionarioEnvio.getInspeccion());
		// TODO crear usuario provisional rol PROV_CUESTIONARIO
		System.out.println("enviar");
	}

}
