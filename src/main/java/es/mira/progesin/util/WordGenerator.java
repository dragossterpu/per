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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
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

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.enums.ContentTypeEnum;
import es.mira.progesin.persistence.repositories.IConfiguracionRespuestasCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;
import es.mira.progesin.services.IGuiaPersonalizadaService;
import es.mira.progesin.services.IGuiaService;

/**
 * Clase para la generación de documentos DOCX.
 * 
 * @author EZENTIS
 *
 */
@Component("wordGenerator")
public class WordGenerator {
    
    /**
     * Constante que contiene la familia de fuentes que se usarán en los documentos.
     */
    public static final String FONTFAMILY = "Arial";
    
    /**
     * Color de fondo para las celdas. Azul claro en hexadecimal
     */
    public static final String COLORCELDATABLA = "bbd2f7";
    
    /**
     * Repositorio de preguntas cuestionario.
     */
    @Autowired
    private IPreguntaCuestionarioRepository preguntasRepository;
    
    /**
     * Servicio de guías.
     */
    @Autowired
    private IGuiaService guiaService;
    
    /**
     * Servicio de guías personalizadas.
     */
    @Autowired
    private IGuiaPersonalizadaService guiaPersonalizadaService;
    
    /**
     * Repositorio de tipos de pregunta.
     */
    @Autowired
    private IConfiguracionRespuestasCuestionarioRepository configuracionRespuestaRepository;
    
    /**
     * Genera un documento DOCX a partir de un cuestionario personalizado, mostrando el texto de las preguntas y, en
     * aquellos casos donde el tipo de respuesta es de tipo TABLA o MATRIZ, dibujando las tablas/matrices de las
     * respuestas. En el caso de los tipo de respuesta RADIO, las opciones posibles se añaden al texto de la pregunta
     * 
     * @param cuestionarioPersonalizado Cuestionario que se desea exportar
     * @return StreamedContent Stream para descargar el fichero en la ventana del navegador
     * @throws InvalidFormatException Excepción de formato inválido
     * @throws IOException Excepción de entrada/salida
     */
    public StreamedContent crearDocumentoCuestionarioPersonalizado(CuestionarioPersonalizado cuestionarioPersonalizado)
            throws InvalidFormatException, IOException {
        XWPFDocument doc = new XWPFDocument();
        crearCabecera(doc);
        crearTitulo(doc, cuestionarioPersonalizado.getNombreCuestionario());
        List<PreguntasCuestionario> listaPreguntas = preguntasRepository
                .findPreguntasElegidasCuestionarioPersonalizado(cuestionarioPersonalizado.getId());
        
        creaCuerpoCuestionario(doc, listaPreguntas);
        return exportarFichero(doc, cuestionarioPersonalizado.getNombreCuestionario());
        
    }
    
    /**
     * Genera un documento DOCX a partir de una guia.
     * 
     * @param guia Guía a partir de la cual se xdesea generar el documento word
     * @return StreamedContent Stream para descargar el fichero en la ventana del navegador
     * @throws InvalidFormatException Excepción de formato inválido
     * @throws IOException Excepción de entrada/salida
     */
    
    public StreamedContent crearDocumentoGuia(Guia guia) throws InvalidFormatException, IOException {
        XWPFDocument doc = new XWPFDocument();
        crearCabecera(doc);
        crearTitulo(doc, guia.getNombre());
        List<GuiaPasos> listaPasos = guiaService.listaPasos(guia);
        creaCuerpoGuia(doc, listaPasos);
        return exportarFichero(doc, guia.getNombre());
    }
    
    /**
     * Genera un documento DOCX a partir de una guia personalizada mostrando el número de inspección que tiene asignado
     * en el caso de existir y los pasos elegidos.
     * 
     * @param guia Guía a partir de la cual se xdesea generar el documento word
     * @return StreamedContent Stream para descargar el fichero en la ventana del navegador
     * @throws InvalidFormatException Excepción de formato inválido
     * @throws IOException Excepción de entrada/salida
     */
    
    public StreamedContent crearDocumentoGuia(GuiaPersonalizada guia) throws InvalidFormatException, IOException {
        XWPFDocument doc = new XWPFDocument();
        crearCabecera(doc);
        crearTitulo(doc, guia.getNombreGuiaPersonalizada());
        if (guia.getInspeccion() != null) {
            creaNumeroInspeccion(doc, guia.getInspeccion());
        }
        List<GuiaPasos> listaPasos = guiaPersonalizadaService.listaPasos(guia);
        creaCuerpoGuia(doc, listaPasos);
        return exportarFichero(doc, guia.getNombreGuiaPersonalizada());
    }
    
    /**
     * Crea la cabecera del documento.
     * 
     * @see #crearDocumentoCuestionarioPersonalizado(CuestionarioPersonalizado)
     * @param doc XWPFDocument Documento donde se insertará la cabecera
     * @throws InvalidFormatException Excepción de formato inválido
     * @throws IOException Excepción de entrada/salida
     */
    private void crearCabecera(XWPFDocument doc) throws InvalidFormatException, IOException {
        CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc, sectPr);
        
        XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);
        
        XWPFParagraph paragraph = header.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        
        XWPFRun run = paragraph.createRun();
        
        ClassPathResource logo = new ClassPathResource(Constantes.LOGOMININISTERIOINTERIOR);
        XWPFPicture picture = run.addPicture(logo.getInputStream(), XWPFDocument.PICTURE_TYPE_PNG, logo.getPath(),
                Units.toEMU(177 * Constantes.ESCALA), Units.toEMU(90 * Constantes.ESCALA));
        
        String blipID = "";
        for (XWPFPictureData picturedata : header.getAllPackagePictures()) {
            blipID = header.getRelationId(picturedata);
        }
        
        picture.getCTPicture().getBlipFill().getBlip().setEmbed(blipID);
        
        BigInteger pos2 = BigInteger.valueOf(9000);
        setTabStop(paragraph, STTabJc.Enum.forString("right"), pos2);
        
        run.addTab();
        logo = new ClassPathResource(Constantes.LOGOIPSS);
        XWPFPicture picture2 = run.addPicture(logo.getInputStream(), XWPFDocument.PICTURE_TYPE_PNG, logo.getPath(),
                Units.toEMU(264 * Constantes.ESCALA), Units.toEMU(85 * Constantes.ESCALA));
        blipID = "";
        for (XWPFPictureData picturedata : header.getAllPackagePictures()) {
            blipID = header.getRelationId(picturedata);
        }
        picture2.getCTPicture().getBlipFill().getBlip().setEmbed(blipID);
    }
    
    /**
     * Crea el título del documento a partir de una cadena recibida como parámetro. Da formato a la cadena recibida y la
     * anexa como título al documento recibido como parámetro.
     * 
     * @param doc Documento al que se desea anexar el título
     * @param titulo Cadena que contiene el título
     * 
     */
    
    private void crearTitulo(XWPFDocument doc, String titulo) {
        XWPFParagraph parrafo = doc.createParagraph();
        XWPFRun texto = parrafo.createRun();
        texto.setText(titulo);
        texto.setBold(true);
        texto.setCapitalized(true);
        texto.setFontSize(16);
        texto.setFontFamily(FONTFAMILY);
        parrafo.setSpacingAfterLines(200);
        parrafo.setAlignment(ParagraphAlignment.CENTER);
        parrafo.addRun(texto);
    }
    
    /**
     * 
     * Crea un párrafo formateado que contiene el identificador de la inspección a la que está asignado el documento.
     * 
     * @param doc Documento al que se desea anexar los números de inspección
     * @param inspecciones Lista de inspecciones Inspección a la que está asignado
     * 
     */
    private void creaNumeroInspeccion(XWPFDocument doc, List<Inspeccion> inspecciones) {
        
        XWPFParagraph parrafo = doc.createParagraph();
        XWPFRun texto = parrafo.createRun();
        if (inspecciones.size() > 1) {
            texto.setText("Inspecciones asociadas ");
        } else {
            texto.setText("Inspección asociada ");
        }
        texto.setBold(true);
        texto.setFontSize(12);
        texto.setFontFamily(FONTFAMILY);
        parrafo.addRun(texto);
        for (Inspeccion ins : inspecciones) {
            texto.addCarriageReturn();
            texto.setText(ins.getNumero());
            parrafo.addRun(texto);
        }
        
        parrafo.setSpacingAfterLines(200);
        parrafo.setAlignment(ParagraphAlignment.LEFT);
        parrafo.addRun(texto);
        
    }
    
    /**
     * Genera el cuerpo de un cuestionario recibiendo como parámetro la lista de preguntas del cuestionario.
     * 
     * @param doc Documento al que se desea anexar el cuerpo con las preguntas
     * @param listaPreguntas Listado de las preguntas que se deben pintar en el documento
     * 
     */
    
    private void creaCuerpoCuestionario(XWPFDocument doc, List<PreguntasCuestionario> listaPreguntas) {
        XWPFParagraph parrafo;
        XWPFRun texto;
        // Construyo un mapa con las preguntas asociadas a cada área
        Map<AreasCuestionario, List<PreguntasCuestionario>> mapaAreaPreguntas = new HashMap<>();
        List<PreguntasCuestionario> listaPreguntasArea;
        for (PreguntasCuestionario preg : listaPreguntas) {
            AreasCuestionario area = preg.getArea();
            if (mapaAreaPreguntas.get(area) == null) {
                listaPreguntasArea = new ArrayList<>();
            } else {
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
            texto.setFontFamily(FONTFAMILY);
            texto.setUnderline(UnderlinePatterns.SINGLE);
            parrafo.setAlignment(ParagraphAlignment.CENTER);
            parrafo.addRun(texto);
            parrafo.setSpacingAfterLines(200);
            
            // Ordena la lista de preguntas por el orden del area para tenerlas ya ordenadas en el mapa
            Collections.sort(listaPreguntasArea, (o1, o2) -> Long.compare(o1.getOrden(), o2.getOrden()));
            
            crearPreguntasPorArea(listaPreguntasArea, doc);
        }
    }
    
    /**
     * Genera el cuerpo del documento a partir de una lista de pasos que recibe como parámetro.
     * 
     * @param doc Documento al que se desea anexar el cuerpo con las preguntas
     * @param listaPasos Listado de los pasos que se deben pintar en el documento
     */
    
    private void creaCuerpoGuia(XWPFDocument doc, List<GuiaPasos> listaPasos) {
        XWPFParagraph parrafo = doc.createParagraph();
        XWPFRun texto = parrafo.createRun();
        parrafo.setSpacingBeforeLines(100);
        parrafo.setSpacingAfterLines(100);
        texto.setText("Pasos");
        texto.setBold(true);
        texto.setFontSize(16);
        texto.setFontFamily(FONTFAMILY);
        parrafo.addRun(texto);
        
        for (GuiaPasos paso : listaPasos) {
            parrafo = doc.createParagraph();
            // Texto pregunta
            texto = parrafo.createRun();
            texto.setBold(true);
            texto.setFontFamily(FONTFAMILY);
            /* Comprobamos si hay saltos de línea */
            String textoPaso = paso.getPaso();
            if (textoPaso.contains("\n")) {
                String[] nuevasLineas = textoPaso.split("\n");
                
                // Por cada línea hay que generar un nuevo XWPFRun insertándole el texto de la linea y un retorno de
                // carro
                textoConSalto(parrafo, texto, nuevasLineas);
            } else {
                texto.setText(paso.getPaso());
            }
            /* Fin comprobaciones */
            parrafo.setSpacingAfterLines(200);
            parrafo.setSpacingBeforeLines(200);
            parrafo.setAlignment(ParagraphAlignment.BOTH);
            parrafo.addRun(texto);
        }
    }
    
    /**
     * Esta función suple la incapacidad de POI para pintar textos que contengan saltos de línea (carácter '\n').
     * 
     * @param parrafo Párrafo al que se añaden las nuevas líneas
     * @param texto Objeto de tipo XWPFRun donde se añade el texto
     * @param nuevasLineas Array que contiene las líneas acabando con el carácter ('\n')
     */
    private void textoConSalto(XWPFParagraph parrafo, XWPFRun texto, String[] nuevasLineas) {
        for (int i = 0; i < nuevasLineas.length; i++) {
            
            String textoLinea = nuevasLineas[i];
            if (i == nuevasLineas.length - 1) {
                texto.setText(textoLinea, 0);
                texto.addCarriageReturn();
            } else {
                parrafo.insertNewRun(i);
                XWPFRun newRun = parrafo.getRuns().get(i);
                
                CTRPr rPr;
                if (newRun.getCTR().isSetRPr()) {
                    rPr = newRun.getCTR().getRPr();
                } else {
                    rPr = newRun.getCTR().addNewRPr();
                }
                rPr.set(texto.getCTR().getRPr());
                newRun.setText(textoLinea);
                newRun.addCarriageReturn();
            }
        }
    }
    
    /**
     * 
     * Generación de un objeto StreamedContent que habilita la posibilidad de que el documento sea descargado desde el
     * navegador.
     * 
     * @param doc Documento a partir del cual se desea generar el fichero
     * @param titulo Título con el que se desea generar el fichero
     * @return StreamedContent Flujo generado a partir del fichero y que permitirá la descarga desde el navegador
     * @throws IOException Excepción de entrada/salida
     */
    
    private StreamedContent exportarFichero(XWPFDocument doc, String titulo) throws IOException {
        File f = File.createTempFile("temporal", ".docx");
        
        FileOutputStream fo = new FileOutputStream(f);
        doc.write(fo);
        
        InputStream inputStream = new FileInputStream(f);
        return new DefaultStreamedContent(inputStream, ContentTypeEnum.DOCX.getContentType(), titulo.concat(".docx"));
        
    }
    
    /**
     * Añade tabulación en un párrafo.
     * 
     * @see #crearCabeceraCuestionario(XWPFDocument, String, String)
     * @param oParagraph Párrafo al que se quiere añadir tabulación
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
     * Inserta las preguntas relacionadas con un área en el documento.
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
            texto.setFontFamily(FONTFAMILY);
            if (pregunta.getTipoRespuesta().startsWith("RADIO")) {
                texto.setText(pregunta.getPregunta() + " (valores posibles: " + getValoresRadioButton(pregunta) + ")");
            } else {
                /* Comprobamos si hay saltos de línea */
                String textoPregunta = pregunta.getPregunta();
                if (textoPregunta.contains("\n")) {
                    String[] nuevasLineas = textoPregunta.split("\n");
                    
                    textoConSalto(parrafo, texto, nuevasLineas);
                } else {
                    texto.setText(pregunta.getPregunta());
                }
                /* Fin comprobaciones */
            }
            parrafo.setSpacingAfterLines(200);
            parrafo.setSpacingBeforeLines(200);
            parrafo.setAlignment(ParagraphAlignment.BOTH);
            parrafo.addRun(texto);
            
            if (pregunta.getTipoRespuesta().startsWith(Constantes.TIPORESPUESTATABLA)
                    || pregunta.getTipoRespuesta().startsWith(Constantes.TIPORESPUESTAMATRIZ)) {
                crearRespuestaTablaMatriz(doc, pregunta);
            }
        }
        
    }
    
    /**
     * Crear la respuesta tabla o matriz asociada a una pregunta.
     * 
     * @see #crearPreguntasPorArea(List, XWPFDocument)
     * @param doc Documento al que se añade la tabla
     * @param pregunta Pregunta que se añade
     */
    private void crearRespuestaTablaMatriz(XWPFDocument doc, PreguntasCuestionario pregunta) {
        List<ConfiguracionRespuestasCuestionario> valoresColumnas = configuracionRespuestaRepository
                .findColumnasBySeccion(pregunta.getTipoRespuesta());
        
        XWPFTable table;
        if (pregunta.getTipoRespuesta().startsWith(Constantes.TIPORESPUESTATABLA)) {
            table = doc.createTable(1, valoresColumnas.size());
        } else {
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
            
            if (pregunta.getTipoRespuesta().startsWith(Constantes.TIPORESPUESTATABLA)) {
                parrafoCelda = filaCabecera.getCell(col).addParagraph();
            } else {
                parrafoCelda = filaCabecera.getCell(col + 1).addParagraph();
                filaCabecera.getCell(valoresColumnas.size()).setColor(COLORCELDATABLA);
            }
            setTextoYFormatoParrafoCelda(parrafoCelda, valoresColumnas.get(col).getConfig().getValor());
            filaCabecera.getCell(col).setColor(COLORCELDATABLA);
        }
        
        if (pregunta.getTipoRespuesta().startsWith(Constantes.TIPORESPUESTAMATRIZ)) {
            // Añado una fila por cada línea de la matriz
            List<ConfiguracionRespuestasCuestionario> valoresFilas = configuracionRespuestaRepository
                    .findFilasBySeccion(pregunta.getTipoRespuesta());
            for (ConfiguracionRespuestasCuestionario config : valoresFilas) {
                XWPFTableRow row = table.createRow();
                parrafoCelda = row.getCell(0).addParagraph();
                setTextoYFormatoParrafoCelda(parrafoCelda, config.getConfig().getValor());
                row.getCell(0).setColor(COLORCELDATABLA);
            }
        } else {
            // Creo una fila vacía
            table.createRow();
        }
    }
    
    /**
     * Crea el texto que se añadirá a un párrafo y se le da formato.
     * 
     * @see #crearRespuestaTablaMatriz(XWPFDocument, PreguntasCuestionario)
     * @param parrafoCelda Párrafo
     * @param valor Valor a añadir
     */
    private void setTextoYFormatoParrafoCelda(XWPFParagraph parrafoCelda, String valor) {
        XWPFRun texto = parrafoCelda.createRun();
        texto.setText(valor);
        texto.setBold(true);
        texto.setFontFamily(FONTFAMILY);
        parrafoCelda.addRun(texto);
        parrafoCelda.setAlignment(ParagraphAlignment.CENTER);
        parrafoCelda.setVerticalAlignment(TextAlignment.CENTER);
    }
    
    /**
     * Obtiene un String con los posibles valores de un RADIO unidos por ", ".
     * 
     * @see #crearPreguntasPorArea(List, XWPFDocument)
     * @param pregunta Pregunta a añadir
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
