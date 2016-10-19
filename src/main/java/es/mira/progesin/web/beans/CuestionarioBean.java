package es.mira.progesin.web.beans;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Component("cuestionarioBean")
@RequestScoped
@Scope("session")
public class CuestionarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public String creaCuestionario() {

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		ServletContext context = (ServletContext) externalContext.getContext();

		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("asunto", "Comunicando InspeccÃ³n General PeriÃ³dica y solicitando documentaciÃ³n");
			parameters.put("numeroReferencia", "SA/IGP/01/2016");
			parameters.put("correoApoyo", "apoyo_ipss@interior.es");
			parameters.put("fechaAntes", "09/05/2016");
			parameters.put("destinatario", "SR. GENERAL, JEFE DE LA ZONA DE LA GUARDIA CIVIL DE LA CC AA DE");
			parameters.put("tipoDestinatario", "Policia");
			parameters.put("tipoInspeccion", "general periÃ³dica");
			parameters.put("identificadorTrimestre", "tercer cuatrimestre del aÃ±o 2016");
			// parameters.put("fecha", "18 de abril de 2016");
			// parameters.put("fechaLimit", "11/01/2017");
			parameters.put("jefeServicios", "EL JEFE DE LOS SERVICIOS DE INSPECCIÃ“N");
			parameters.put("nombreJefeServicios", "JosÃ© LuÃ­s DomÃ­nguez Alonso");
			JasperReport jasperReport = getCompiled("solicitudDevolucion1");

			String[] pageFile = new String[3];
			pageFile[0] = "preCuestionario_subreport1";
			pageFile[1] = "preCuestionario_subreport2";
			pageFile[2] = "preCuestionario_subreport3";

			JasperReport[] jasperSubReport = new JasperReport[3];
			for (int i = 0; pageFile.length > i; i++) {
				jasperSubReport[i] = getCompiled(pageFile[i]);
			}
			parameters.put("preCuestionario_subreport1", jasperSubReport[0]);
			parameters.put("preCuestionario_subreport2", jasperSubReport[1]);
			parameters.put("preCuestionario_subreport3", jasperSubReport[2]);
			// JasperReport reporte = (JasperReport) JRLoader
			// .loadObjectFromFile("C:/Users/Admin/Desktop/jasper/preCuestionario.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
			String fileJasper = "C:/Users/Admin/Desktop/jasper/solicitudDevolucion.jasper";
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			response.getOutputStream().write(bytes, 0, bytes.length);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			// JasperPrint print = JasperFillManager.fillReport(fileJasper, null, new JREmptyDataSource());
			// JasperViewer jviewer = new JasperViewer(print, false);
			// jviewer.setVisible(true);

			// Exporta el informe a PDF
			JasperExportManager.exportReportToPdfFile(jasperPrint,
					"C:/Users/Admin/Desktop/jasper/solicitudDevolucion1.pdf");
			// Para visualizar el pdf directamente desde java
			// JasperViewer.viewReport(print, false);
			Exporter exporter = new JRDocxExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

			File exportReportFile = new File("C:/Users/Admin/Desktop/jasper/solicitudDevolucion.docx");

			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportReportFile));

			exporter.exportReport();
		}
		catch (

		Exception e) {
			e.printStackTrace();
		}

		return "/registro/registro";
	}

	@PostConstruct
	public void init() {

	}

	private JasperReport getCompiled(String fileName) throws JRException {
		InputStream is = FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("C:\\Users\\Admin\\Desktop\\jasper\\" + fileName + ".jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(is);
		return jasperReport;
	}
}
