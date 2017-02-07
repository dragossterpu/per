package es.mira.progesin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabs;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.enums.ContentTypeEnum;
import es.mira.progesin.persistence.repositories.IConfiguracionRespuestasCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;

/**
 * Clase para la generación de documentos DOCX
 * 
 * @author EZENTIS
 *
 */
@Component("wordGenerator")
public class WordGenerator {

	public static final String LOGO_MININISTERIO_INTERIOR = "static/images/ministerior_interior_logo.png";

	public static final String LOGO_IPSS = "static/images/logo_ipss.png";

	public static final String BACKGROUND_COLOR_AREA_HEXADECIMAL = "cfd6d4";

	public static final String FONT_FAMILY = "Arial";

	// Azul claro en hexadecimal
	public static final String COLOR_CELDA_TABLA = "bbd2f7";

	public static final String TIPO_RESPUESTA_TABLA = "TABLA";

	@Autowired
	private IPreguntaCuestionarioRepository preguntasRepository;

	@Autowired
	IConfiguracionRespuestasCuestionarioRepository configuracionRespuestaRepository;

	/**
	 * Genera un documento DOCX a partir de un cuestionario personalizado, mostrando el texto de las preguntas y, en
	 * aquellos casos donde el tipo de respuesta es de tipo TABLA o MATRIZ, dibujando las tablas/matrices de las
	 * respuestas. En el caso de los tipo de respuesta RADIO, las opciones posibles se añaden al texto de la pregunta
	 * 
	 * @param cuestionarioPersonalizado
	 * @return StreamedContent Stream para descargar el fichero en la ventana del navegador
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public StreamedContent crearDocumentoCuestionarioPersonalizado(CuestionarioPersonalizado cuestionarioPersonalizado)
			throws InvalidFormatException, IOException {
		// Blank Document
		XWPFDocument doc = new XWPFDocument();

		crearCabeceraCuestionario(doc, LOGO_MININISTERIO_INTERIOR, LOGO_IPSS);

		XWPFParagraph parrafo = doc.createParagraph();
		// XWPFRun is used to add a region of text to the paragraph.

		// Nombre des cuestionario
		XWPFRun texto = parrafo.createRun();
		texto.setText(cuestionarioPersonalizado.getNombreCuestionario());
		texto.setBold(true);
		texto.setCapitalized(true);
		texto.setFontSize(16);
		texto.setFontFamily(FONT_FAMILY);
		parrafo.setSpacingAfterLines(200);
		parrafo.setAlignment(ParagraphAlignment.CENTER);
		parrafo.addRun(texto);

		// Áreas del cuestionario
		List<PreguntasCuestionario> listaPreguntas = preguntasRepository
				.findPreguntasElegidasCuestionarioPersonalizado(cuestionarioPersonalizado.getId());

		// Construyo un mapa con las preguntas asociadas a cada área
		Map<AreasCuestionario, List<PreguntasCuestionario>> mapaAreaPreguntas = new HashMap<>();
		List<PreguntasCuestionario> listaPreguntasArea;
		for (PreguntasCuestionario preg : listaPreguntas) {
			AreasCuestionario area = preg.getArea();
			if (mapaAreaPreguntas.get(area) == null) {
				listaPreguntasArea = new ArrayList<>();
			}
			else {
				listaPreguntasArea = mapaAreaPreguntas.get(area);
			}
			listaPreguntasArea.add(preg);

			mapaAreaPreguntas.put(area, listaPreguntasArea);
		}

		List<AreasCuestionario> listaAreas = new ArrayList<>(mapaAreaPreguntas.keySet());
		// Ordena la lista de áreas
		Collections.sort(listaAreas, (o1, o2) -> Long.compare(o1.getOrden(), o2.getOrden()));
		for (AreasCuestionario area : listaAreas) {
			listaPreguntasArea = mapaAreaPreguntas.get(area);

			parrafo = doc.createParagraph();
			// Nombre del área
			texto = parrafo.createRun();
			texto.setText(area.getArea());
			texto.setBold(true);
			texto.setCapitalized(true);
			texto.setFontSize(14);
			texto.setFontFamily(FONT_FAMILY);
			texto.setUnderline(UnderlinePatterns.SINGLE);
			parrafo.setAlignment(ParagraphAlignment.CENTER);
			parrafo.addRun(texto);
			parrafo.setSpacingAfterLines(200);

			// Ordena la lista de preguntas por el orden del area para tenerlas ya ordenadas en el mapa
			Collections.sort(listaPreguntasArea, (o1, o2) -> Long.compare(o1.getOrden(), o2.getOrden()));

			crearPreguntasPorArea(listaPreguntasArea, doc);
		}

		File f = File.createTempFile("CuestionarioPersonalizado", ".docx");

		FileOutputStream fo = new FileOutputStream(f);
		doc.write(fo);

		InputStream inputStream = new FileInputStream(f);
		return new DefaultStreamedContent(inputStream, ContentTypeEnum.DOCX.getContentType(),
				"CuestionarioPersonalizado.docx");
	}

	/**
	 * Crea la cabecera del documento
	 * 
	 * @see #crearDocumentoCuestionarioPersonalizado(CuestionarioPersonalizado)
	 * @param doc XWPFDocument Documento donde se insertará la cabecera
	 * @param imagen1 Imagen situada en la parte superior izquierda de la cabecera
	 * @param imagen2 Imagen situada en la parte superior derecha de la cabecera
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	private void crearCabeceraCuestionario(XWPFDocument doc, String imagen1, String imagen2)
			throws InvalidFormatException, IOException {
		// create header start
		CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
		XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc, sectPr);

		XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

		XWPFParagraph paragraph = header.getParagraphArray(0);
		paragraph.setAlignment(ParagraphAlignment.LEFT);

		XWPFRun run = paragraph.createRun();

		ClassPathResource logo = new ClassPathResource(LOGO_MININISTERIO_INTERIOR);
		XWPFPicture picture = run.addPicture(logo.getInputStream(), XWPFDocument.PICTURE_TYPE_PNG, logo.getPath(),
				Units.toEMU(177 * 0.6), Units.toEMU(90 * 0.6));

		String blipID = "";
		for (XWPFPictureData picturedata : header.getAllPackagePictures()) {
			blipID = header.getRelationId(picturedata);
		}
		// now they have a blipID also
		picture.getCTPicture().getBlipFill().getBlip().setEmbed(blipID);

		// header imagen 2 (logo ipss)
		BigInteger pos2 = BigInteger.valueOf(9000);
		setTabStop(paragraph, STTabJc.Enum.forString("right"), pos2);

		run.addTab();
		logo = new ClassPathResource(LOGO_IPSS);
		XWPFPicture picture2 = run.addPicture(logo.getInputStream(), XWPFDocument.PICTURE_TYPE_PNG, logo.getPath(),
				Units.toEMU(264 * 0.6), Units.toEMU(85 * 0.6));
		blipID = "";
		for (XWPFPictureData picturedata : header.getAllPackagePictures()) {
			blipID = header.getRelationId(picturedata);
		}
		picture2.getCTPicture().getBlipFill().getBlip().setEmbed(blipID);
	}

	/**
	 * Añade tabulación en un párrafo
	 * @see #crearCabeceraCuestionario(XWPFDocument, String, String)
	 * @param oParagraph
	 * @param oSTTabJc
	 * @param oPos
	 */
	private void setTabStop(XWPFParagraph oParagraph, STTabJc.Enum oSTTabJc, BigInteger oPos) {
		CTP oCTP = oParagraph.getCTP();
		CTPPr oPPr = oCTP.getPPr();
		if (oPPr == null) {
			oPPr = oCTP.addNewPPr();
		}

		CTTabs oTabs = oPPr.getTabs();
		if (oTabs == null) {
			oTabs = oPPr.addNewTabs();
		}

		CTTabStop oTabStop = oTabs.addNewTab();
		oTabStop.setVal(oSTTabJc);
		oTabStop.setPos(oPos);
	}

	/**
	 * Inserta las preguntas relacionadas con un área en el documento
	 * 
	 * @see #crearDocumentoCuestionarioPersonalizado(CuestionarioPersonalizado)
	 * @param listaPreguntasArea Lista de preguntas asociadas a un área
	 * @param doc XWPFDocument donde se insertarán las preguntas
	 */
	private void crearPreguntasPorArea(List<PreguntasCuestionario> listaPreguntasArea, XWPFDocument doc) {
		XWPFParagraph parrafo;
		XWPFRun texto;
		for (PreguntasCuestionario pregunta : listaPreguntasArea) {
			parrafo = doc.createParagraph();
			// Texto pregunta
			texto = parrafo.createRun();
			texto.setBold(true);
			texto.setFontFamily(FONT_FAMILY);
			if (pregunta.getTipoRespuesta().startsWith("RADIO")) {
				texto.setText(pregunta.getPregunta() + " (valores posibles: " + getValoresRadioButton(pregunta) + ")");
			}
			else {
				texto.setText(pregunta.getPregunta());
			}
			parrafo.setSpacingAfterLines(200);
			parrafo.setSpacingBeforeLines(200);
			parrafo.setAlignment(ParagraphAlignment.BOTH);
			parrafo.addRun(texto);

			if (pregunta.getTipoRespuesta().startsWith(TIPO_RESPUESTA_TABLA)
					|| pregunta.getTipoRespuesta().startsWith("MATRIZ")) {
				crearRespuestaTablaMatriz(doc, pregunta);
			}
		}

	}

	/**
	 * Crear la respuesta tabla o matriz asociada a una pregunta
	 * 
	 * @see #crearPreguntasPorArea(List, XWPFDocument)
	 * @param doc
	 * @param pregunta
	 */
	private void crearRespuestaTablaMatriz(XWPFDocument doc, PreguntasCuestionario pregunta) {
		List<ConfiguracionRespuestasCuestionario> valoresColumnas = configuracionRespuestaRepository
				.findColumnasBySeccion(pregunta.getTipoRespuesta());

		XWPFTable table;
		if (pregunta.getTipoRespuesta().startsWith(TIPO_RESPUESTA_TABLA)) {
			table = doc.createTable(1, valoresColumnas.size());
		}
		else {
			table = doc.createTable(1, valoresColumnas.size() + 1);
		}

		// Fijar el ancho de la tabla
		CTTblWidth width = table.getCTTbl().addNewTblPr().addNewTblW();
		width.setType(STTblWidth.DXA);
		width.setW(BigInteger.valueOf(9072));

		// Añado las cabeceras en la fila 1
		XWPFTableRow filaCabecera = table.getRow(0);
		XWPFParagraph parrafoCelda;
		for (int col = 0; col < valoresColumnas.size(); col++) {

			if (pregunta.getTipoRespuesta().startsWith(TIPO_RESPUESTA_TABLA)) {
				parrafoCelda = filaCabecera.getCell(col).addParagraph();
			}
			else {
				parrafoCelda = filaCabecera.getCell(col + 1).addParagraph();
				filaCabecera.getCell(valoresColumnas.size()).setColor(COLOR_CELDA_TABLA);
			}
			setTextoYFormatoParrafoCelda(parrafoCelda, valoresColumnas.get(col).getConfig().getValor());
			filaCabecera.getCell(col).setColor(COLOR_CELDA_TABLA);
		}

		if (pregunta.getTipoRespuesta().startsWith("MATRIZ")) {
			// Añado una fila por cada línea de la matriz
			List<ConfiguracionRespuestasCuestionario> valoresFilas = configuracionRespuestaRepository
					.findFilasBySeccion(pregunta.getTipoRespuesta());
			for (ConfiguracionRespuestasCuestionario config : valoresFilas) {
				XWPFTableRow row = table.createRow();
				parrafoCelda = row.getCell(0).addParagraph();
				setTextoYFormatoParrafoCelda(parrafoCelda, config.getConfig().getValor());
				row.getCell(0).setColor(COLOR_CELDA_TABLA);
			}
		}
		else {
			// Creo una fila vacía
			table.createRow();
		}
	}

	/**
	 * Crea el texto que se añadirá a un párrafo y se le da formato
	 * @see #crearRespuestaTablaMatriz(XWPFDocument, PreguntasCuestionario)
	 * @param parrafoCelda
	 * @param valor
	 */
	private void setTextoYFormatoParrafoCelda(XWPFParagraph parrafoCelda, String valor) {
		XWPFRun texto = parrafoCelda.createRun();
		texto.setText(valor);
		texto.setBold(true);
		texto.setFontFamily(FONT_FAMILY);
		parrafoCelda.addRun(texto);
		parrafoCelda.setAlignment(ParagraphAlignment.CENTER);
		parrafoCelda.setVerticalAlignment(TextAlignment.CENTER);
	}

	/**
	 * Obtiene un String con los posibles valores de un RADIO unidos por ", "
	 * 
	 * @see #crearPreguntasPorArea(List, XWPFDocument)
	 * @param pregunta
	 * @return String con los valores de un RADIO unidos por ", "
	 */
	private String getValoresRadioButton(PreguntasCuestionario pregunta) {
		String valoresString = "";
		List<String> valores = configuracionRespuestaRepository.findValoresPorSeccion(pregunta.getTipoRespuesta());
		if (valores != null && valores.isEmpty() == Boolean.FALSE) {
			valoresString = String.join(", ", valores);
		}
		return valoresString;
	}
}
