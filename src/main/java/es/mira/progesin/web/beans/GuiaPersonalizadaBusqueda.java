package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GuiaPersonalizadaBusqueda implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date fechaDesde;

	private Date fechaHasta;

	private String usuarioCreacion;

	private String nombre;

	private TipoInspeccion tipoInspeccion;

	private List<GuiaPersonalizada> listaGuias;

	public void resetValues() {
		this.fechaDesde = null;
		this.fechaHasta = null;
		this.usuarioCreacion = null;
		this.nombre = null;
		this.tipoInspeccion = null;
		this.listaGuias = null;
	}
}
