package es.mira.progesin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import es.mira.progesin.model.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.Documento;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.enums.ContentTypeEnum;
import es.mira.progesin.persistence.repositories.IConfiguracionRespuestasCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;

/**
 * Clase para la generación de documentos PDF de la aplicación
 * @author EZENTIS
 *
 */
@Component("pdfGenerator")
public class PdfGenerator {

	public static final String NOMBRE_FICHERO_SOLICITUD = "Solicitud_Documentacion.pdf";

	public static final String LOGO_MININISTERIO_INTERIOR_IPSS = "static/images/header_sol_doc_pag_1.png";

	public static final String LOGO_MININISTERIO_INTERIOR = "static/images/ministerior_interior_logo.png";

	public static final String LOGO_IPSS = "static/images/logo_ipss.png";

	public static final String LOGO_CALIDAD = "static/images/footer_solicitud_1.png";

	private static final String NOMBRE_FICHERO_CUESTIONARIO_ENVIADO_OR = "Cuestionario_OR.pdf";

	private static final String NOMBRE_FICHERO_CUESTIONARIO_ENVIADO_DEST = "Cuestionario.pdf";

	@Autowired
	IRespuestaCuestionarioRepository respuestaCuestionarioRepository;

	@Autowired
	IConfiguracionRespuestasCuestionarioRepository configuracionRespuestaRepository;

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

		crearCabeceraFooter(pdf, document, true);

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
		return new DefaultStreamedContent(inputStream, ContentTypeEnum.PDF.getContentType(), NOMBRE_FICHERO_SOLICITUD);
	}

	/**
	 * Crea la cabecera y el footer del documento
	 * @param pdf
	 * @param document
	 * @param insertarFooter Indica si hay que poner un footer al documento o sólo se inserta una cabecera
	 * @throws IOException
	 */
	private void crearCabeceraFooter(PdfDocument pdf, Document document, boolean insertarFooter) throws IOException {
		File file = new ClassPathResource(LOGO_MININISTERIO_INTERIOR).getFile();
		Image logoMinisterioInterior = new Image(ImageDataFactory.create(file.getPath()));
		logoMinisterioInterior.scaleAbsolute((float) (logoMinisterioInterior.getImageWidth() * 0.6),
				(float) (logoMinisterioInterior.getImageHeight() * 0.6));

		file = new ClassPathResource(LOGO_IPSS).getFile();
		Image ipssLogo = new Image(ImageDataFactory.create(file.getPath()));
		ipssLogo.scaleAbsolute((float) (ipssLogo.getImageWidth() * 0.6), (float) (ipssLogo.getImageHeight() * 0.6));

		file = new ClassPathResource(LOGO_MININISTERIO_INTERIOR_IPSS).getFile();
		Image headerRepetido = new Image(ImageDataFactory.create(file.getPath()));
		headerRepetido.scaleAbsolute((float) (headerRepetido.getImageWidth() * 0.6),
				(float) (headerRepetido.getImageHeight() * 0.6));

		// Footer
		Image footer = null;
		if (insertarFooter) {
			file = new ClassPathResource(LOGO_CALIDAD).getFile();
			footer = new Image(ImageDataFactory.create(file.getPath()));
			footer.scaleAbsolute((float) (footer.getImageWidth() * 0.6), (float) (footer.getImageHeight() * 0.6));
		}

		HeaderFooterPdf handler = new HeaderFooterPdf(document, logoMinisterioInterior, ipssLogo, headerRepetido,
				footer);
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

		tabla.addHeaderCell("DOCUMENTO");
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

	/**
	 * Genera un documento PDF con las preguntas y respuestas de un cuestionario enviado
	 * 
	 * @param cuestionarioEnviado
	 * @return
	 * @throws Exception
	 */
	public StreamedContent crearCuestionarioEnviado(CuestionarioEnvio cuestionarioEnviado) throws Exception {
		File fileOr = File.createTempFile(NOMBRE_FICHERO_CUESTIONARIO_ENVIADO_OR, ".pdf");

		// Initialize PDF writer
		PdfWriter writer = new PdfWriter(fileOr.getAbsolutePath());

		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);

		// Initialize document
		Document document = new Document(pdf, PageSize.A4);
		document.setMargins(100, 36, 70, 36);

		crearCabeceraFooter(pdf, document, false);

		Paragraph p = new Paragraph(
				cuestionarioEnviado.getCuestionarioPersonalizado().getNombreCuestionario().toUpperCase());
		p.setBold();
		p.setTextAlignment(TextAlignment.CENTER);
		p.setPadding(20);
		document.add(p);

		p = new Paragraph();
		Text text = new Text("INSPECCIÓN:  ").setBold();
		p.add(text);
		p.add(cuestionarioEnviado.getInspeccion().getTipoInspeccion().getCodigo() + " "
				+ cuestionarioEnviado.getInspeccion().getNumero());
		p.setPaddingBottom(10);
		document.add(p);

		List<RespuestaCuestionario> listaRespuestas = respuestaCuestionarioRepository
				.findDistinctByRespuestaIdCuestionarioEnviado(cuestionarioEnviado);

		// Ordena la lista de respuestas por el orden del area para tenerlas ya ordenadas en el mapa
		Collections.sort(listaRespuestas,
				(o1, o2) -> Long.compare(o1.getRespuestaId().getPregunta().getArea().getOrden(),
						o2.getRespuestaId().getPregunta().getArea().getOrden()));

		// Construyo un mapa con las respuestas asociadas a cada área
		Map<AreasCuestionario, List<RespuestaCuestionario>> mapaAreaRespuesta = new LinkedHashMap<>();
		AreasCuestionario area;
		List<RespuestaCuestionario> listaRtasArea;
		for (RespuestaCuestionario rta : listaRespuestas) {
			area = rta.getRespuestaId().getPregunta().getArea();
			if (mapaAreaRespuesta.get(area) == null) {
				listaRtasArea = new ArrayList<>();
			}
			else {
				listaRtasArea = mapaAreaRespuesta.get(area);
			}
			listaRtasArea.add(rta);

			mapaAreaRespuesta.put(area, listaRtasArea);
		}

		Iterator<AreasCuestionario> areasSet = mapaAreaRespuesta.keySet().iterator();
		while (areasSet.hasNext()) {
			area = areasSet.next();
			List<RespuestaCuestionario> listaRespuestasArea = mapaAreaRespuesta.get(area);
			Paragraph pa = new Paragraph(area.getArea());
			pa.setBackgroundColor(Color.LIGHT_GRAY);
			pa.setTextAlignment(TextAlignment.CENTER);
			pa.setBold();
			document.add(pa);

			// Ordeno las preguntas por su orden
			Collections.sort(listaRespuestasArea, (o1, o2) -> Long.compare(o1.getRespuestaId().getPregunta().getOrden(),
					o2.getRespuestaId().getPregunta().getOrden()));

			crearRespuestasPorArea(listaRespuestasArea, document);
		}

		// Close document
		document.close();

		File fileDest = File.createTempFile(NOMBRE_FICHERO_CUESTIONARIO_ENVIADO_DEST, ".pdf");
		insertarNumeroPagina(fileOr.getAbsolutePath(), fileDest.getAbsolutePath(), document);

		InputStream inputStream = new FileInputStream(fileDest);
		return new DefaultStreamedContent(inputStream, ContentTypeEnum.PDF.getContentType(),
				NOMBRE_FICHERO_CUESTIONARIO_ENVIADO_DEST);
	}

	private void crearRespuestasPorArea(List<RespuestaCuestionario> listaRespuestas, Document document)
			throws Exception {
		for (RespuestaCuestionario respuesta : listaRespuestas) {
			PreguntasCuestionario pregunta = respuesta.getRespuestaId().getPregunta();
			Paragraph p = new Paragraph(pregunta.getPregunta());
			p.setBold();
			document.add(p);

			if (pregunta.getTipoRespuesta().startsWith("TABLA") || pregunta.getTipoRespuesta().startsWith("MATRIZ")) {
				document.add(crearRespuestaTipoTablaMatriz(respuesta));
			}
			else {
				p = new Paragraph(respuesta.getRespuestaTexto());
				document.add(p);
				if (pregunta.getTipoRespuesta() != null && pregunta.getTipoRespuesta().startsWith("ADJUNTO")
						&& respuesta.getDocumentos().isEmpty() == Boolean.FALSE) {
					document.add(crearTablaDocumentos(respuesta));
				}
			}
		}
	}

	private Table crearRespuestaTipoTablaMatriz(RespuestaCuestionario respuesta) throws Exception {
		List<DatosTablaGenerica> listaDatosTabla = respuesta.getRespuestaTablaMatriz();
		String tipoRespuesta = respuesta.getRespuestaId().getPregunta().getTipoRespuesta();
		List<ConfiguracionRespuestasCuestionario> valoresColumnas = configuracionRespuestaRepository
				.findColumnasBySeccion(tipoRespuesta);

		Table tabla = new Table(valoresColumnas.size());
		tabla.setWidthPercent(100);
		tabla.setPaddingBottom(30);
		tabla.setPaddingTop(30);

		if (tipoRespuesta.startsWith("MATRIZ")) {
			tabla = new Table(valoresColumnas.size() + 1);
			// Añado la primera columna de la cabecera vacía
			tabla.addHeaderCell("");
		}

		tabla.setWidthPercent(100);

		for (ConfiguracionRespuestasCuestionario columna : valoresColumnas) {
			Cell cell = new Cell();
			cell.add(columna.getConfig().getValor());
			cell.setTextAlignment(TextAlignment.CENTER);
			tabla.addHeaderCell(cell);
		}
		tabla.getHeader().setBackgroundColor(Color.LIGHT_GRAY);
		tabla.getHeader().setPaddingTop(20);

		for (DatosTablaGenerica datosTabla : listaDatosTabla) {
			if (tipoRespuesta.startsWith("MATRIZ")) {
				Cell cell = new Cell();
				cell.add(datosTabla.getNombreFila());
				cell.setBackgroundColor(Color.LIGHT_GRAY);
				cell.setTextAlignment(TextAlignment.LEFT);
				tabla.addCell(cell);
			}
			for (ConfiguracionRespuestasCuestionario columna : valoresColumnas) {
				Field field = DatosTablaGenerica.class.getDeclaredField(columna.getConfig().getClave());
				field.setAccessible(true);
				tabla.addCell((String) field.get(datosTabla));
			}
		}

		return tabla;
	}

	private Table crearTablaDocumentos(RespuestaCuestionario respuesta) throws Exception {
		Table tabla = new Table(1);
		tabla.setWidthPercent(100);
		tabla.setPaddingBottom(10);
		tabla.setPaddingTop(10);

		tabla.addHeaderCell("DOCUMENTOS ADJUNTADOS");
		tabla.getHeader().setBackgroundColor(Color.LIGHT_GRAY);
		tabla.getHeader().setTextAlignment(TextAlignment.CENTER);

		List<Documento> listaDocumentos = respuesta.getDocumentos();
		for (Documento documento : listaDocumentos) {
			tabla.addCell(documento.getNombre());
		}

		return tabla;
	}

	public void insertarNumeroPagina(String src, String dest, Document doc) throws IOException, DocumentException {
		PdfReader reader = new PdfReader(src);
		int nTotalPaginas = reader.getNumberOfPages();
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
		PdfContentByte pagecontent;
		for (int i = 0; i < nTotalPaginas;) {
			pagecontent = stamper.getOverContent(++i);
			ColumnText.showTextAligned(pagecontent, Element.ALIGN_CENTER,
					new Phrase(String.format("Página %s de %s", i, nTotalPaginas)),
					(pagecontent.getPdfDocument().getPageSize().getRight() - doc.getRightMargin()
							- (pagecontent.getPdfDocument().getPageSize().getLeft() + doc.getLeftMargin())) / 2
							+ doc.getLeftMargin(),
					pagecontent.getPdfDocument().getPageSize().getBottom() + 20, 0);
		}
		stamper.close();
		reader.close();
	}
}
