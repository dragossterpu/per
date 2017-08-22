package es.mira.progesin.util;

import java.util.List;

import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import lombok.Setter;

/**
 * Clase para generar el PDF de una solicitud de documentación previa.
 * 
 * @author EZENTIS
 *
 */
@Setter
@Component("pdfGeneratorSolicitudes")
public class PdfGeneratorSolicitudes extends PdfAbstractGenerator {
    
    /**
     * Datos de la solicitud a imprimir.
     */
    private SolicitudDocumentacionPrevia solicitudDocPrevia;
    
    /**
     * Listado de los documentos que contendrá la solicitud.
     */
    private List<DocumentacionPrevia> listadoDocumentosSolicitud;
    
    /**
     * Nombre del pdf generado para la solicitud de documentación previa.
     */
    public static final String NOMBREFICHEROSOLICITUD = "Solicitud_Documentacion.pdf";
    
    /**
     * Genera un PDF con la solicitud de documentación previa.
     * 
     * @return StreamedContent
     * @throws ProgesinException excepción que puede lanzar
     */
    @Override
    public StreamedContent exportarPdf() throws ProgesinException {
        return crearPdf(NOMBREFICHEROSOLICITUD, true, false);
    }
    
    /**
     * Obtiene la tabla con los documentos solicitados.
     * 
     * @see imprimirSolicitudDocumentacionPrevia
     * @param tipoDocumentacion listado de documentos pedidos en la solicitud
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
     * Obtiene la tabla con los datos de la persona de apoyo.
     * 
     * @see imprimirSolicitudDocumentacionPrevia
     * @param solDocPrevia solicitud de documentación previa
     * @return Table Tabla con los datos de la persona de apoyo
     */
    private Table crearTablaDatosApoyo(SolicitudDocumentacionPrevia solDocPrevia) {
        Table tabla = new Table(3);
        tabla.setWidthPercent(100);
        
        Cell celda = new Cell();
        celda.add(solDocPrevia.getApoyoPuesto());
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
     * Genera el contenido que se mostrará en el PDF.
     * 
     * @param document Documento pdf al que se adjuntará el contenido
     * @throws ProgesinException excepción que puede lanzar
     */
    @Override
    public void crearCuerpoPdf(Document document) {
        Paragraph p1 = new Paragraph("Nº INSPECCIÓN: " + solicitudDocPrevia.getInspeccion().getNumero());
        p1.setMarginTop(30);
        Paragraph p2 = new Paragraph(
                "FECHA: Madrid, " + Utilities.getFechaFormateada(solicitudDocPrevia.getFechaAlta(), "dd/MM/yyyy"));
        Paragraph p3 = new Paragraph("ASUNTO: " + solicitudDocPrevia.getAsunto());
        Paragraph p4 = new Paragraph("DESTINATARIO: " + solicitudDocPrevia.getDestinatario());
        p4.setBold();
        p4.setMarginBottom(20);
        
        document.add(p1);
        document.add(p2);
        document.add(p3);
        document.add(p4);
        
        // TODO: añadir el resto de textos
        
        document.add(crearTablaTipoDocumentacion(listadoDocumentosSolicitud));
        
        Paragraph p = new Paragraph("Para cualquier aclaración, podrá dirigirse a:");
        p.setPaddingBottom(20);
        p.setPaddingTop(20);
        document.add(p);
        
        document.add(crearTablaDatosApoyo(solicitudDocPrevia));
        
        p = new Paragraph(
                "El Jefe del Equipo Auditor encargado de dirigir y coordinar la Inspección, próximamente, se pondrá en contacto con el Jefe de esa Unidad a los fines expuestos.");
        p.setPaddingTop(20);
        document.add(p);
    }
}
