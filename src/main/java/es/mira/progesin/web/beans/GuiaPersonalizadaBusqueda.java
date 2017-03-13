package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Clase de objetos para el almacenaje de los parámetros de búsqueda de guías personalizadas
 * 
 * @author Ezentis
 *
 */
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

	/**
	 * Limpia los valores del objeto
	 * 
	 */
	public void resetValues() {
		this.fechaDesde = null;
		this.fechaHasta = null;
		this.usuarioCreacion = null;
		this.nombre = null;
		this.tipoInspeccion = null;
		this.listaGuias = null;
	}
}
