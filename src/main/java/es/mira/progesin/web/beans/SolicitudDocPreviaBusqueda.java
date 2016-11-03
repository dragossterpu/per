package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.SolicitudDocPreviaEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SolicitudDocPreviaBusqueda implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date fechaDesde;

	private Date fechaHasta;

	private SolicitudDocPreviaEnum estado;

	private User usuarioCreacion;

	private List<SolicitudDocumentacionPrevia> listaSolicitudesDocPrevia;

	public void resetValues() {
		this.fechaDesde = null;
		this.fechaHasta = null;
		this.estado = null;
		this.usuarioCreacion = null;
		this.listaSolicitudesDocPrevia = null;
	}

}
