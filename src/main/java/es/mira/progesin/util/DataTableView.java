package es.mira.progesin.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.mira.progesin.model.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataTableView implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ColumnModel> columns;

	private List<DatosTablaGenerica> listaDatosTabla;

	public DataTableView() {
		columns = new ArrayList<>();
		listaDatosTabla = new ArrayList<>();
	}

	public void crearTabla(List<ConfiguracionRespuestasCuestionario> valoresColumnas) {
		crearColumnasDinamicamente(valoresColumnas);
		crearFilaVacia();
	}

	public void crearMatriz(List<ConfiguracionRespuestasCuestionario> valoresColumnas) {
		// Inicializado la lista con un campo vacío para que muestre el datatable con una fila vacía de inputText
		for (ConfiguracionRespuestasCuestionario config : valoresColumnas) {
			if ("nombreFila".equals(config.getConfig().getClave())) {
				crearFilaMatriz(config.getConfig().getValor());
			}
			else {
				columns.add(new ColumnModel(config.getConfig().getValor(), config.getConfig().getClave()));
			}
		}
	}

	public void crearFilaMatriz(String nombreFila) {
		DatosTablaGenerica dtg = new DatosTablaGenerica();
		dtg.setNombreFila(nombreFila);
		listaDatosTabla.add(dtg);
	}

	public void crearColumnasDinamicamente(List<ConfiguracionRespuestasCuestionario> valoresColumnas) {
		// valoresColumnas = campo1#nombreColumna1, campo2#nombreColumna2 ...
		String header;
		String property;
		for (ConfiguracionRespuestasCuestionario config : valoresColumnas) {
			property = config.getConfig().getClave();
			header = config.getConfig().getValor();
			columns.add(new ColumnModel(header, property));
		}
	}

	public void crearFilaVacia() {
		DatosTablaGenerica dtg = new DatosTablaGenerica();
		listaDatosTabla.add(dtg);
	}

	/**
	 * Elimina el último registro de la tabla, siempre y cuando la tabla tenga más de una fila
	 */
	public void eliminarFila() {
		if (listaDatosTabla.size() > 1) {
			listaDatosTabla.remove(listaDatosTabla.size() - 1);
		}
	}

	public class ColumnModel implements Serializable {

		private static final long serialVersionUID = 1L;

		private String header;

		private String property;

		public ColumnModel(String header, String property) {
			this.header = header;
			this.property = property;
		}

		public String getHeader() {
			return header;
		}

		public String getProperty() {
			return property;
		}
	}
}
