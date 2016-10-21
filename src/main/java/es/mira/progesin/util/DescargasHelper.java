package es.mira.progesin.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mira.progesin.persistence.entities.DatosJasper;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class DescargasHelper {

	private static final Log LOG = LogFactory.getLog(DescargasHelper.class);

	/**
	 * Generacion de la plantilla de preenvio cuestionario
	 * @param form
	 * @param context
	 * @throws Exception
	 */
	public static void preparaDescargaJasper(DatosJasper datosJasper, HttpSession session) throws Exception {

		// DatosJasper Clase con los parametros variables de las plantillas jasper de pre envio cuestionario
		final List<DatosJasper> listaForm = new ArrayList<DatosJasper>();
		listaForm.add(datosJasper);
		final JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(listaForm);
		final Map<String, Object> reportParameters = new HashMap<String, Object>();
		final String nombreFichero = "GC_ZONA_PLURI_PROVINCIAL";
		reportParameters.put("asunto", "Comunicando Inspeccon General Periodica y solicitando documentacion");
		reportParameters.put("numeroReferencia", "SA/IGP/01/2016");
		reportParameters.put("destinatario", "SR. GENERAL, JEFE DE LA ZONA DE LA GUARDIA CIVIL DE LA CC AA DE");
		reportParameters.put("tipoInspeccion", "general periodica");
		reportParameters.put("identificadorTrimestre", "tercer cuatrimestre del aÃ±o 2016");
		LOG.info("Recuperando datos para generr reporte ");
		try {
			new ReportsHelper().generarReporte(datosJasper.getUrl(), reportParameters, data, nombreFichero, session);
		}
		catch (Exception e) {
			LOG.error("Error generando reporte " + e);
		}

	}

}
