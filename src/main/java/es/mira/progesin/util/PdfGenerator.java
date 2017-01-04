package es.mira.progesin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;

import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;

/**
 * Clase para la generación de documentos PDF de la aplicación
 * @author EZENTIS
 *
 */
@Component("pdfGenerator")
public class PdfGenerator {

	public static final String NOMBRE_FICHERO_SOLICITUD = "Solicitud_Documentacion.pdf";

	public static final String LOGO_MININISTERIO_INTERIOR_IPSS = "src/main/resources/static/images/header_sol_doc_pag_1.png";

	public static final String LOGO_MININISTERIO_INTERIOR = "src/main/resources/static/images/ministerior_interior_logo.png";

	public static final String LOGO_IPSS = "src/main/resources/static/images/logo_ipss.png";

	public static final String LOGO_CALIDAD = "src/main/resources/static/images/footer_solicitud_1.png";

	private static final String CONTENT_TYPE_PDF = "application/pdf";

	/**
	 * 
	 * @param solDocPrevia Datos de la solicitud a imprimir
	 * @param listadoDocumentosPrevios Listado de los documentos que contendrá la solicitud
	 * @return StreamedContent
	 * @throws Exception
	 */
	public StreamedContent imprimirSolicitudDocumentacionPrevia(SolicitudDocumentacionPrevia solDocPrevia,
			List<DocumentacionPrevia> listadoDocumentosPrevios) throws IOException {
		File file = File.createTempFile(NOMBRE_FICHERO_SOLICITUD, ".pdf");

		// Initialize PDF writer
		PdfWriter writer = new PdfWriter(file.getAbsolutePath());

		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);

		// Initialize document
		Document document = new Document(pdf, PageSize.A4);
		document.setMargins(100, 36, 70, 36);

		crearCabeceraDocumento(pdf, document);

		Paragraph p1 = new Paragraph("Nº INSPECCIÓN: " + solDocPrevia.getInspeccion().getNumero());
		p1.setMarginTop(30);
		Paragraph p2 = new Paragraph(
				"FECHA: Madrid, " + Utilities.getFechaFormateada(solDocPrevia.getFechaAlta(), "dd/MM/yyyy"));
		Paragraph p3 = new Paragraph("ASUNTO: " + solDocPrevia.getAsunto());
		Paragraph p4 = new Paragraph("DESTINATARIO: " + solDocPrevia.getDestinatario());
		p4.setBold();
		p4.setMarginBottom(20);

		document.add(p1);
		document.add(p2);
		document.add(p3);
		document.add(p4);

		// TODO: añadir el resto de textos

		document.add(crearTablaTipoDocumentacion(listadoDocumentosPrevios));

		Paragraph p = new Paragraph("Para cualquier aclaración, podrá dirigirse a:");
		p.setPaddingBottom(20);
		p.setPaddingTop(20);
		document.add(p);

		document.add(crearTablaDatosApoyo(solDocPrevia));

		p = new Paragraph(
				"El Jefe del Equipo Auditor encargado de dirigir y coordinar la Inspección, próximamente, se pondrá en contacto con el Jefe de esa Unidad a los fines expuestos.");
		p.setPaddingTop(20);
		document.add(p);

		// Close document
		document.close();

		InputStream inputStream = new FileInputStream(file);
		return new DefaultStreamedContent(inputStream, CONTENT_TYPE_PDF, NOMBRE_FICHERO_SOLICITUD);
	}

	/**
	 * Crea la cabecera de la solicitud
	 * @param pdf
	 * @param document
	 * @throws IOException
	 */
	private void crearCabeceraDocumento(PdfDocument pdf, Document document) throws IOException {
		Image logoMinisterioInterior = new Image(ImageDataFactory.create(LOGO_MININISTERIO_INTERIOR));
		logoMinisterioInterior.scaleAbsolute((float) (logoMinisterioInterior.getImageWidth() * 0.6),
				(float) (logoMinisterioInterior.getImageHeight() * 0.6));

		Image ipssLogo = new Image(ImageDataFactory.create(LOGO_IPSS));
		ipssLogo.scaleAbsolute((float) (ipssLogo.getImageWidth() * 0.6), (float) (ipssLogo.getImageHeight() * 0.6));

		Image headerRepetido = new Image(ImageDataFactory.create(LOGO_MININISTERIO_INTERIOR_IPSS));
		headerRepetido.scaleAbsolute((float) (headerRepetido.getImageWidth() * 0.6),
				(float) (headerRepetido.getImageHeight() * 0.6));

		// Footer
		Image footerCalidad = new Image(ImageDataFactory.create(LOGO_CALIDAD));
		footerCalidad.scaleAbsolute((float) (footerCalidad.getImageWidth() * 0.6),
				(float) (footerCalidad.getImageHeight() * 0.6));

		HeaderFooterPdf handler = new HeaderFooterPdf(document, logoMinisterioInterior, ipssLogo, headerRepetido,
				footerCalidad);
		pdf.addEventHandler(PdfDocumentEvent.END_PAGE, handler);
	}

	/**
	 * Obtiene la tabla con los documentos solicitados
	 * @see imprimirSolicitudDocumentacionPrevia
	 * @param tipoDocumentacion
	 * @return Table Tabla con los documentos
	 */
	private Table crearTablaTipoDocumentacion(List<DocumentacionPrevia> tipoDocumentacion) {
		float[] columnWidths = { 5, 3, 2 };
		Table tabla = new Table(columnWidths);
		tabla.setWidthPercent(100);

		tabla.addHeaderCell(new Cell().add("DOCUMENTO").setBackgroundColor(Color.LIGHT_GRAY));
		tabla.addHeaderCell("NOMBRE");
		tabla.addHeaderCell("TIPO DE ARCHIVO");

		tabla.getHeader().setBackgroundColor(Color.LIGHT_GRAY);
		tabla.getHeader().setPaddingTop(20);
		tabla.getHeader().setHorizontalAlignment(HorizontalAlignment.CENTER);

		Cell celda;
		for (DocumentacionPrevia doc : tipoDocumentacion) {
			celda = new Cell();
			celda.add(doc.getDescripcion());
			tabla.addCell(celda);

			celda = new Cell();
			celda.add(doc.getNombre());
			tabla.addCell(celda);

			celda = new Cell();
			celda.add(String.join(", ", doc.getExtensiones()));
			tabla.addCell(celda);
		}

		return tabla;
	}

	/**
	 * Obtiene la tabla con los datos de la persona de apoyo
	 * @see imprimirSolicitudDocumentacionPrevia
	 * @param solDocPrevia
	 * @return Table Tabla con los datos de la persona de apoyo
	 */
	private Table crearTablaDatosApoyo(SolicitudDocumentacionPrevia solDocPrevia) {
		Table tabla = new Table(4);
		tabla.setWidthPercent(100);

		Cell celda = new Cell();
		celda.add(solDocPrevia.getApoyoPuesto());
		tabla.addCell(celda);

		celda = new Cell();
		celda.add(solDocPrevia.getApoyoNombre());
		tabla.addCell(celda);

		celda = new Cell();
		celda.add(solDocPrevia.getApoyoTelefono());
		tabla.addCell(celda);

		celda = new Cell();
		celda.add(solDocPrevia.getApoyoCorreo());
		tabla.addCell(celda);

		return tabla;
	}

}
