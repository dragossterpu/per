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

	// public void crearTabla(List<String> valoresColumnas) {
	// crearColumnasDinamicamente(valoresColumnas);
	// crearFilaVacia();
	// }

	// public void crearMatriz(List<String> valores) {
	// List<String> nombresFilas = new ArrayList<>();
	// List<String> valoresColumnas = new ArrayList<>();
	// for (String valor : valores) {
	// if (valor.startsWith("nombreFila")) {
	// nombresFilas.add(valor.substring(valor.indexOf('#') + 1));
	// }
	// else {
	// valoresColumnas.add(valor);
	// }
	// }
	// crearColumnasDinamicamente(valoresColumnas);
	// for (String nombreFila : nombresFilas) {
	// crearFilaMatriz(nombreFila);
	// }
	// }

	public void crearTabla(List<ConfiguracionRespuestasCuestionario> valoresColumnas) {
		crearColumnasDinamicamente(valoresColumnas);
		crearFilaVacia();
	}

	public void crearMatriz(List<ConfiguracionRespuestasCuestionario> valoresColumnas) {
		// List<String> nombresFilas = new ArrayList<>();
		// List<String> valoresColumnas = new ArrayList<>();
		// for (String valor : valores) {
		// if (valor.startsWith("nombreFila")) {
		// nombresFilas.add(valor.substring(valor.indexOf('#') + 1));
		// }
		// else {
		// valoresColumnas.add(valor);
		// }
		// }
		// crearColumnasDinamicamente(valoresColumnas);
		// for (String nombreFila : nombresFilas) {
		// crearFilaMatriz(nombreFila);
		// }

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

	// public void crearColumnasDinamicamente(List<String> valoresColumnas) {
	// // valoresColumnas = campo1#nombreColumna1, campo2#nombreColumna2 ...
	// columns = new ArrayList<ColumnModel>();
	// String header;
	// String property;
	// for (String columna : valoresColumnas) {
	// property = columna.substring(0, columna.indexOf('#'));
	// header = columna.substring(columna.indexOf('#') + 1);
	// columns.add(new ColumnModel(header, property));
	// }
	//
	// // Inicializado la lista con un campo vacío para que muestre el datatable con una fila vacía de inputText
	// listaDatosTabla = new ArrayList<>();
	// }

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
