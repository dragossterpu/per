package es.mira.progesin.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class ReportsHelper {

	private static final Log LOG = LogFactory.getLog(ReportsHelper.class);

	/**
	 * Generar el reporte indicado
	 * 
	 * @param pathReporte
	 * @param reportParameters
	 * @param data
	 * @param tipoFormatoReporte
	 * @param context
	 * @throws Exception
	 */
	public void generarReporte(final String pathReporte, final Map<String, Object> reportParameters,
			final JRBeanCollectionDataSource data, final String nombreFichero) throws Exception {
		generarReporte(pathReporte, reportParameters, data, null);
	}

	/**
	 * Generator de reporte
	 * 
	 * @param pathReporte
	 * @param model
	 * @param data
	 * @param tipoFormatoReporte
	 * @param nombre
	 * @param context
	 * @throws Exception
	 */
	public void generarReporte(final String pathReporte, final Map<String, Object> reportParameters,
			final JRBeanCollectionDataSource data, final String nombreFichero, final HttpSession session) {
		final InputStream inputStream = session.getServletContext().getResourceAsStream("/WEB-INF/" + pathReporte);
		generarReporte(inputStream, reportParameters, data, session,
				StringUtils.isEmpty(nombreFichero) ? calcularNombreFichero(pathReporte) : nombreFichero);
	}

	/**
	 * 
	 * @param inputStream
	 * @param model
	 * @param data
	 * @param tipoFormatoReporte
	 * @param session
	 * @param nombreFicheroDescarga
	 * @throws Exception
	 */

	protected void generarReporte(final InputStream inputStream, final Map<String, Object> reportParameters,
			final JRBeanCollectionDataSource data, final HttpSession session, final String nombreFicheroDescarga) {

		try {

			if (inputStream == null) {
				throw new FileNotFoundException("Reporte no encontrado.");
			}
			reportParameters.put(JRParameter.REPORT_LOCALE, LocaleContextHolder.getLocale());
			final JasperReport jasperReport;
			final JasperPrint jasperPrint;

			try {
				String url = session.getServletContext().getRealPath("/WEB-INF/jasper/gcZonaPluriprovincial.jrxml");
				// Compile jrxml
				jasperReport = JasperCompileManager.compileReport(url);
				// Cargar con los datos obtenidos del formulario
				jasperPrint = JasperFillManager.fillReport(jasperReport, reportParameters, data);
				// Formato html
				JasperExportManager.exportReportToHtmlFile(jasperPrint,
						"C:/Users/Admin/Desktop/jasper/GC_ZONA_PLURI_PROVINCIAL/gcZonaPluriprovincial.html");
				LOG.info("Generando reporte en HTML ");
				// Formato pdf
				JasperExportManager.exportReportToPdfFile(jasperPrint,
						"C:/Users/Admin/Desktop/jasper/GC_ZONA_PLURI_PROVINCIAL/gcZonaPluriprovincial.pdf");
				LOG.info("Generando reporte en PDF ");
				// Formato docs
				Exporter exporter = new JRDocxExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				File exportReportFile = new File(
						"C:/Users/Admin/Desktop/jasper/GC_ZONA_PLURI_PROVINCIAL/gcZonaPluriprovincial.docx");
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportReportFile));
				exporter.exportReport();
				LOG.info("Generando reporte en DOC");
			}
			catch (JRException e) {
				LOG.error("Error generando reporte: ", e);
			}

		}
		catch (final Exception e) {
			LOG.error("Error generando reporte: ", e);
			throw new RuntimeException("Error generando reporte");
		}
	}

	private String calcularNombreFichero(final String pathReporte) {
		return pathReporte.replace('/', '_').replace(".jasper", "");
	}

}
