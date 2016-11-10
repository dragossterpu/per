package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;

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

	public void resetValues() {
		this.fechaDesde = null;
		this.fechaHasta = null;
		this.estado = null;
		this.usuarioCreacion = null;
	}

}
