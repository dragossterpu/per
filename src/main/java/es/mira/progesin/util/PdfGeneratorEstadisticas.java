package es.mira.progesin.util;

import java.io.File;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.web.beans.InspeccionBusqueda;
import lombok.Setter;

/**
 * Clase para generar el PDF de la pantalla de estadísticas.
 * 
 * @author EZENTIS
 *
 */
@Setter
@Component("pdfGeneratorEstadisticas")
public class PdfGeneratorEstadisticas extends PdfAbstractGenerator {
    
    /**
     * Nombre del pdf de estadisticas.
     */
    private static final String NOMBREPDFESTADISTICAS = "Estadisticas.pdf";
    
    /**
     * Estados seleccionados para la generación del informe en PDF.
     */
    private Map<EstadoInspeccionEnum, List<Inspeccion>> mapaEstados;
    
    /**
     * Filtro de búsqueda para seleccionar las estadísticas a mostrar.
     */
    private InspeccionBusqueda filtro;
    
    /**
     * Fichero con la imagen a exportar.
     */
    private File fileImg;
    
    /**
     * Genera un documento PDF con las estadísticas por estado de las inspecciones.
     * 
     * @return pdf con el contenido del cuestionario
     * @throws ProgesinException excepción que puede lanzar
     */
    @Override
    public StreamedContent exportarPdf() throws ProgesinException {
        return crearPdf(NOMBREPDFESTADISTICAS, false, true);
    }
    
    /**
     * Genera el contenido que se mostrará en el PDF.
     * 
     * @param documento Documento pdf al que se adjuntará el contenido
     * @throws ProgesinException excepción que puede lanzar
     */
    @Override
    public void crearCuerpoPdf(Document documento) throws ProgesinException {
        // Título
        Paragraph titulo = new Paragraph("Informe estadístico");
        titulo.setBold();
        titulo.setFontSize(16);
        titulo.setTextAlignment(TextAlignment.CENTER);
        titulo.setPadding(5);
        documento.add(titulo);
        // Fecha
        Paragraph fecha = new Paragraph(
                "Fecha emisión documento : " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        fecha.setTextAlignment(TextAlignment.RIGHT);
        fecha.setPadding(5);
        documento.add(fecha);
        // Datos del informe
        documento.add(creaTitulo("Parámetros del informe"));
        // Filtros usados
        documento.add(creaSubtitulo("Filtros aplicados"));
        creaInfoFiltros(documento);
        List<EstadoInspeccionEnum> listaEstadosSeleccionados = new ArrayList<>(mapaEstados.keySet());
        documento.add(creaSubtitulo("Estados seleccionados"));
        creaInfoSeleccion(documento, listaEstadosSeleccionados);
        documento.add(new AreaBreak()); // Salto de página
        
        // Datos
        documento.add(creaSubtitulo("Desglose"));
        for (EstadoInspeccionEnum estado : listaEstadosSeleccionados) {
            List<Inspeccion> listaInspecciones = mapaEstados.get(estado);
            
            // genera tabla de inspecciones
            creaTablaDesglose(documento, listaInspecciones, estado);
            
        }
        // Gráfica
        documento.add(new AreaBreak()); // Salto de página
        documento.add(creaSubtitulo("Gráfica"));
        try {
            documento.add(new Image(ImageDataFactory.create(fileImg.getPath())));
            if (fileImg.exists()) {
                fileImg.delete();
            }
        } catch (MalformedURLException e) {
            throw new ProgesinException(e);
        }
    }
    
    /**
     * Crea un párrafo formateado para servir como título.
     * 
     * @param texto Texto del título a generar
     * @return Párrafo formateado como título.
     */
    private Paragraph creaTitulo(String texto) {
        Paragraph titulo = new Paragraph(texto);
        titulo.setBold();
        titulo.setFontSize(12);
        titulo.setTextAlignment(TextAlignment.CENTER);
        titulo.setPadding(10);
        return titulo;
    }
    
    /**
     * Crea un párrafo formateado para servir como título secundario.
     * 
     * @param texto Texto del subtítulo a generar
     * @return Párrafo formateado como título secundario.
     */
    private Paragraph creaSubtitulo(String texto) {
        Paragraph subTitulo = new Paragraph(texto);
        subTitulo.setFontSize(12);
        subTitulo.setBackgroundColor(Color.convertRgbToCmyk(new DeviceRgb(153, 201, 255)));
        subTitulo.setMarginTop(20);
        subTitulo.setMarginBottom(10);
        return subTitulo;
    }
    
    /**
     * Inserta en el informe de estadísticas la información relativa a los filtros empleados para generar la
     * información.
     * 
     * @param doc Documento al que se adjunta la información
     */
    private void creaInfoFiltros(Document doc) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        float[] columnas = { 2, 1 };
        Table tabla;
        Cell celdaFiltro;
        if (filtro.getTipoInspeccion() != null || filtro.getProvincia() != null || filtro.getFechaDesde() != null
                || filtro.getFechaHasta() != null) {
            tabla = new Table(columnas);
            tabla.setWidthPercent(50);
            
            tabla.addHeaderCell("Filtro");
            tabla.addHeaderCell("Valor");
            tabla.getHeader().setFontSize(10);
            tabla.getHeader().setBackgroundColor(new DeviceRgb(204, 228, 255));
            if (filtro.getTipoInspeccion() != null) {
                celdaFiltro = new Cell().setFontSize(10).add("Tipo de inspección");
                tabla.addCell(celdaFiltro);
                celdaFiltro = new Cell().setFontSize(9).add(filtro.getTipoInspeccion().getDescripcion());
                tabla.addCell(celdaFiltro);
            }
            if (filtro.getProvincia() != null) {
                celdaFiltro = new Cell().setFontSize(10).add("Provincia");
                tabla.addCell(celdaFiltro);
                celdaFiltro = new Cell().setFontSize(9).add(filtro.getProvincia().getNombre());
                tabla.addCell(celdaFiltro);
            }
            if (filtro.getFechaDesde() != null) {
                celdaFiltro = new Cell().setFontSize(10).add("Fecha desde");
                tabla.addCell(celdaFiltro);
                celdaFiltro = new Cell().setFontSize(9).add(sdf.format(filtro.getFechaDesde()));
                tabla.addCell(celdaFiltro);
            }
            if (filtro.getFechaHasta() != null) {
                celdaFiltro = new Cell().setFontSize(10).add("Fecha hasta");
                tabla.addCell(celdaFiltro);
                celdaFiltro = new Cell().setFontSize(9).add(sdf.format(filtro.getFechaHasta()));
                tabla.addCell(celdaFiltro);
            }
            doc.add(tabla);
        } else {
            Paragraph texto = new Paragraph("No se han aplicado filtros");
            texto.setFontSize(9);
            texto.setPadding(5);
            doc.add(texto);
        }
    }
    
    /**
     * Inserta en el informe de estadísticas los estados seleccionados para la generación del informe.
     * 
     * @param doc Documento al que se adjunta la información
     * @param listado Listado de los estados seleccionados
     */
    private void creaInfoSeleccion(Document doc, List<EstadoInspeccionEnum> listado) {
        for (EstadoInspeccionEnum estado : listado) {
            Paragraph texto = new Paragraph(estado.getDescripcion());
            texto.setFontSize(9);
            doc.add(texto);
        }
    }
    
    /**
     * Inserta en el informe una tabla con el desglose de inspecciones en un estado dado.
     * 
     * @param doc Documento en el que se insertará la tabla
     * @param listaInspecciones Lista de las inspecciones
     * @param estado Estado de las inspecciones
     */
    private void creaTablaDesglose(Document doc, List<Inspeccion> listaInspecciones, EstadoInspeccionEnum estado) {
        float[] columnWidths = { 1, 2, 1, 1, 1, 1 }; // 6 columnas
        Table tabla = new Table(columnWidths);
        if (!listaInspecciones.isEmpty()) {
            Paragraph descripcionEstado = new Paragraph(estado.getDescripcion());
            descripcionEstado.setBackgroundColor(Color.convertRgbToCmyk(new DeviceRgb(153, 201, 255)));
            descripcionEstado.setMarginTop(20);
            descripcionEstado.setFontSize(10);
            doc.add(descripcionEstado);
            tabla.setWidthPercent(100);
            tabla.addHeaderCell("EXPEDIENTE");
            tabla.addHeaderCell("TIPO DE INSPECCION");
            tabla.addHeaderCell("EQUIPO");
            tabla.addHeaderCell("CUERPO");
            tabla.addHeaderCell("UNIDAD");
            tabla.addHeaderCell("PROVINCIA");
            tabla.getHeader().setBackgroundColor(new DeviceRgb(204, 228, 255));
            tabla.getHeader().setPaddingTop(20);
            tabla.getHeader().setHorizontalAlignment(HorizontalAlignment.CENTER);
            tabla.getHeader().setFontSize(9);
        }
        Cell celda;
        
        for (Inspeccion ins : listaInspecciones) {
            celda = new Cell();
            celda.setFontSize(9);
            celda.add(ins.getNumero());
            tabla.addCell(celda);
            celda = new Cell();
            celda.setFontSize(9);
            celda.add(ins.getTipoInspeccion().getDescripcion());
            tabla.addCell(celda);
            celda = new Cell();
            celda.setFontSize(9);
            celda.add(ins.getEquipo().getNombreEquipo());
            tabla.addCell(celda);
            celda = new Cell();
            celda.setFontSize(9);
            celda.add(ins.getAmbito().getDescripcion());
            tabla.addCell(celda);
            celda = new Cell();
            celda.setFontSize(9);
            celda.add(ins.getNombreUnidad());
            tabla.addCell(celda);
            celda = new Cell();
            celda.setFontSize(9);
            celda.add(ins.getMunicipio().getProvincia().getNombre());
            tabla.addCell(celda);
        }
        doc.add(tabla);
    }
}
