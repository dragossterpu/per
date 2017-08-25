package es.mira.progesin.util;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.repositories.IConfiguracionRespuestasCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;

/**
 * Clase para la generación de documentos DOCX.
 * 
 * @author EZENTIS
 *
 */
@Component("wordGeneratorCuestionarios")
public class WordGeneratorCuestionarios extends WordAbstractGenerator {
    
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
     * @throws ProgesinException excepción lanzada
     */
    public StreamedContent exportar(CuestionarioPersonalizado cuestionarioPersonalizado) throws ProgesinException {
        try (XWPFDocument doc = new XWPFDocument()) {
            // Tamaño página A4 595x842 en puntos (hay que multiplicarlo por 20)
            setTamanioYMargenDocumento(doc, 800, 720, 1440, 1440, 595 * 20, 842 * 20);
            crearCabecera(doc);
            crearTitulo(doc, cuestionarioPersonalizado.getNombreCuestionario());
            List<PreguntasCuestionario> listaPreguntas = preguntasRepository
                    .findPreguntasElegidasCuestionarioPersonalizado(cuestionarioPersonalizado.getId());
            
            creaCuerpoCuestionario(doc, listaPreguntas);
            return exportarFichero(doc, cuestionarioPersonalizado.getNombreCuestionario());
        } catch (InvalidFormatException | IOException e) {
            throw new ProgesinException(e);
        }
        
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
            texto.setFontSize(10);
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
        width.setW(BigInteger.valueOf(10300));
        
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
        texto.setFontSize(8);
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
