package es.mira.progesin.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mira.progesin.persistence.entities.DatosJasper;
import es.mira.progesin.persistence.entities.SolicitudDocumentacion;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class DescargasHelper {

	private static final Log LOG = LogFactory.getLog(DescargasHelper.class);

	/**
	 * Generacion de la plantilla de preenvio cuestionario
	 * @param form
	 * @param context
	 * @throws Exception
	 */
	public static void preparaDescargaJasper(DatosJasper datosJasper, HttpSession session,
			SolicitudDocumentacion documento) throws Exception {

		// DatosJasper Clase con los parametros variables de las plantillas jasper de pre envio cuestionario
		final List<DatosJasper> listaForm = new ArrayList<DatosJasper>();
		listaForm.add(datosJasper);
		final JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(listaForm);
		final Map<String, Object> reportParameters = new HashMap<String, Object>();
		final String nombreFichero = "GC_ZONA_PLURI_PROVINCIAL";
		reportParameters.put("asunto", datosJasper.getAsunto());
		reportParameters.put("numeroReferencia", datosJasper.getNumeroReferencia());
		reportParameters.put("destinatario", datosJasper.getDestinatario());
		reportParameters.put("tipoInspeccion", datosJasper.getTipoInspeccion());
		// reportParameters.put("fechaAntes", datosJasper.getFechaAntes());
		reportParameters.put("fechaCumplimentar", datosJasper.getFechaCumplimentar());
		reportParameters.put("correoApoyo", datosJasper.getCorreoApoyo());
		reportParameters.put("jefeServicios", datosJasper.getPuestoInspector());
		reportParameters.put("identificadorTrimestre", datosJasper.getIdentificadorTrimestre());
		reportParameters.put("nombre", datosJasper.getNombre());
		reportParameters.put("nombreJefeServicios", datosJasper.getNombreInspector());
		Date date = new Date();
		DateFormat fecha = new SimpleDateFormat("dd//MM/yyyy");
		reportParameters.put("fechaEmision", "Madrid, " + fecha.format(date));

		LOG.info("Recuperando datos para generr reporte ");
		try {
			new ReportsHelper().generarReporte(datosJasper.getUrl(), reportParameters, data, nombreFichero, session,
					documento);
		}
		catch (Exception e) {
			LOG.error("Error generando reporte " + e);
		}

	}

}
