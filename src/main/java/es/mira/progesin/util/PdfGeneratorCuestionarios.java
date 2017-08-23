package es.mira.progesin.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.services.ICuestionarioEnvioService;
import lombok.Setter;

/**
 * Clase para generar el PDF de un cuestionario enviado.
 * 
 * @author EZENTIS
 *
 */
@Setter
@Component("pdfGeneratorCuestionarios")
public class PdfGeneratorCuestionarios extends PdfAbstractGenerator {
    
    /**
     * Nombre del pdf del cuestionario.
     */
    private static final String NOMBREPDFCUESTIONARIO = "Cuestionario";
    
    /**
     * Servicio de cuestionarios enviados.
     */
    @Autowired
    private ICuestionarioEnvioService cuestionarioEnvioService;
    
    /**
     * Cuestionario del que se quiere generar el PDF.
     */
    private CuestionarioEnvio cuestionarioEnviado;
    
    /**
     * Genera un documento PDF con las preguntas y respuestas de un cuestionario enviado.
     * 
     * @return pdf con el contenido del cuestionario
     * @throws ProgesinException excepción que puede lanzar
     */
    @Override
    public StreamedContent exportarPdf() throws ProgesinException {
        return crearPdf(
                String.format("%s_Inspeccion_%s-%s.pdf", NOMBREPDFCUESTIONARIO,
                        cuestionarioEnviado.getInspeccion().getId(), cuestionarioEnviado.getInspeccion().getAnio()),
                false, true);
    }
    
    /**
     * Genera el contenido que se mostrará en el PDF.
     * 
     * @param document Documento pdf al que se adjuntará el contenido
     * @throws ProgesinException excepción que puede lanzar
     */
    @Override
    public void crearCuerpoPdf(Document document) throws ProgesinException {
        Paragraph p = new Paragraph(
                cuestionarioEnviado.getCuestionarioPersonalizado().getNombreCuestionario().toUpperCase());
        p.setBold();
        p.setTextAlignment(TextAlignment.CENTER);
        p.setPadding(10);
        p.setFontSize(14);
        document.add(p);
        
        p = new Paragraph();
        Text text = new Text("INSPECCIÓN:  ").setBold();
        p.add(text);
        p.add(cuestionarioEnviado.getInspeccion().getTipoInspeccion().getCodigo() + " "
                + cuestionarioEnviado.getInspeccion().getNumero());
        p.setPaddingBottom(10);
        p.setFontSize(14);
        document.add(p);
        
        List<RespuestaCuestionario> listaRespuestas = cuestionarioEnvioService
                .findRespuestasCuestionarioEnviado(cuestionarioEnviado);
        
        // Ordena la lista de respuestas por el orden del area para tenerlas ya ordenadas en el mapa
        Collections.sort(listaRespuestas,
                (o1, o2) -> Long.compare(o1.getRespuestaId().getPregunta().getArea().getOrden(),
                        o2.getRespuestaId().getPregunta().getArea().getOrden()));
        
        // Construyo un mapa con las respuestas asociadas a cada área
        Map<AreasCuestionario, List<RespuestaCuestionario>> mapaAreaRespuesta = construirMapaAreaRespuestas(
                listaRespuestas);
        
        Iterator<AreasCuestionario> areasSet = mapaAreaRespuesta.keySet().iterator();
        AreasCuestionario area;
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
        
    }
    
    /**
     * Construye un mapa con las respuestas asociadas a cada área.
     * 
     * @param listaRespuestas lista usada para contruir el mapa
     * @return mapa de lista de repuestas por área
     */
    private Map<AreasCuestionario, List<RespuestaCuestionario>> construirMapaAreaRespuestas(
            List<RespuestaCuestionario> listaRespuestas) {
        Map<AreasCuestionario, List<RespuestaCuestionario>> mapaAreaRespuesta = new LinkedHashMap<>();
        AreasCuestionario area;
        List<RespuestaCuestionario> listaRtasArea;
        for (RespuestaCuestionario rta : listaRespuestas) {
            area = rta.getRespuestaId().getPregunta().getArea();
            if (mapaAreaRespuesta.get(area) == null) {
                listaRtasArea = new ArrayList<>();
            } else {
                listaRtasArea = mapaAreaRespuesta.get(area);
            }
            
            listaRtasArea.add(rta);
            
            mapaAreaRespuesta.put(area, listaRtasArea);
        }
        return mapaAreaRespuesta;
    }
    
    /**
     * Crear las respuestas de un área del cuestionario.
     * 
     * @param listaRespuestas lista de respuestas
     * @param document documento donde se insertan las respuestas
     * @throws ProgesinException excepción que puede lanzar
     * @throws IllegalAccessException excepción que puede lanzar
     */
    private void crearRespuestasPorArea(List<RespuestaCuestionario> listaRespuestas, Document document)
            throws ProgesinException {
        for (RespuestaCuestionario respuesta : listaRespuestas) {
            String textoPregunta = respuesta.getRespuestaId().getPregunta().getPregunta();
            String tipoRespuesta = respuesta.getRespuestaId().getPregunta().getTipoRespuesta();
            Paragraph p = new Paragraph(textoPregunta);
            p.setBold();
            p.setFontSize(10);
            p.setTextAlignment(TextAlignment.JUSTIFIED);
            p.setMarginBottom(5);
            p.setMarginTop(5);
            
            document.add(p);
            
            if (tipoRespuesta.startsWith(Constantes.TIPORESPUESTATABLA)
                    || tipoRespuesta.startsWith(Constantes.TIPORESPUESTAMATRIZ)) {
                document.add(crearRespuestaTipoTablaMatriz(respuesta));
            } else {
                p = new Paragraph(respuesta.getRespuestaTexto());
                p.setFontSize(10);
                document.add(p);
                if (tipoRespuesta.startsWith("ADJUNTO") && respuesta.getDocumentos().isEmpty() == Boolean.FALSE) {
                    document.add(crearTablaDocumentos(respuesta));
                }
            }
        }
    }
    
    /**
     * Crear una tabla con la respuesta.
     * 
     * @param respuesta Respuesta de la que se creará la tabla
     * @return tabla
     * @throws ProgesinException excepción que puede lanzar
     */
    private Table crearRespuestaTipoTablaMatriz(RespuestaCuestionario respuesta) throws ProgesinException {
        List<DatosTablaGenerica> listaDatosTabla = respuesta.getRespuestaTablaMatriz();
        String tipoRespuesta = respuesta.getRespuestaId().getPregunta().getTipoRespuesta();
        List<ConfiguracionRespuestasCuestionario> valoresColumnas = cuestionarioEnvioService
                .findColumnasBySeccion(tipoRespuesta);
        
        Table tabla;
        try {
            if (tipoRespuesta.startsWith(Constantes.TIPORESPUESTAMATRIZ)) {
                tabla = new Table(valoresColumnas.size() + 1);
                // Añado la primera columna de la cabecera vacía
                tabla.addHeaderCell("");
            } else {
                tabla = new Table(valoresColumnas.size());
            }
            
            tabla.setWidthPercent(100);
            tabla.setKeepTogether(true);
            tabla.setMarginBottom(10);
            tabla.setFontSize(8);
            
            for (ConfiguracionRespuestasCuestionario columna : valoresColumnas) {
                Cell cell = new Cell();
                cell.add(columna.getConfig().getValor());
                cell.setTextAlignment(TextAlignment.CENTER);
                cell.setFontSize(8);
                tabla.addHeaderCell(cell);
            }
            tabla.getHeader().setBackgroundColor(Color.LIGHT_GRAY);
            tabla.getHeader().setPaddingTop(20);
            
            for (DatosTablaGenerica datosTabla : listaDatosTabla) {
                if (tipoRespuesta.startsWith(Constantes.TIPORESPUESTAMATRIZ)) {
                    Cell cell = new Cell();
                    cell.add(datosTabla.getNombreFila());
                    cell.setBackgroundColor(Color.LIGHT_GRAY);
                    cell.setTextAlignment(TextAlignment.LEFT);
                    cell.setFontSize(8);
                    tabla.addCell(cell);
                }
                for (ConfiguracionRespuestasCuestionario columna : valoresColumnas) {
                    Field field = DatosTablaGenerica.class.getDeclaredField(columna.getConfig().getClave());
                    field.setAccessible(true);
                    Cell cell = new Cell();
                    cell.add((String) field.get(datosTabla));
                    cell.setFontSize(8);
                    tabla.addCell(cell);
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            throw new ProgesinException(e);
        }
        return tabla;
    }
    
    /**
     * Crear una tabla con los documentos adjuntados en una respuesta.
     * 
     * @param respuesta Respuesta con los documentos adjuntados
     * @return tabla
     */
    private Table crearTablaDocumentos(RespuestaCuestionario respuesta) {
        Table tabla = new Table(1);
        tabla.setWidthPercent(100);
        tabla.setKeepTogether(true);
        tabla.setMarginBottom(10);
        tabla.setFontSize(8);
        
        tabla.addHeaderCell("DOCUMENTOS ADJUNTADOS");
        tabla.getHeader().setBackgroundColor(Color.LIGHT_GRAY);
        tabla.getHeader().setTextAlignment(TextAlignment.CENTER);
        
        List<Documento> listaDocumentos = respuesta.getDocumentos();
        for (Documento documento : listaDocumentos) {
            tabla.addCell(documento.getNombre());
        }
        
        return tabla;
    }
    
}
