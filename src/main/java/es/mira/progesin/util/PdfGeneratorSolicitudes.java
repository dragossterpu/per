package es.mira.progesin.util;

import java.util.List;

import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

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
    public static final String NOMBREFICHEROSOLICITUD = "Solicitud_Documentacion";
    
    /**
     * Genera un PDF con la solicitud de documentación previa.
     * 
     * @return StreamedContent
     * @throws ProgesinException excepción que puede lanzar
     */
    @Override
    public StreamedContent exportarPdf() throws ProgesinException {
        return crearPdf(
                String.format("%s_Inspeccion_%s-%s.pdf", NOMBREFICHEROSOLICITUD,
                        solicitudDocPrevia.getInspeccion().getId(), solicitudDocPrevia.getInspeccion().getAnio()),
                true, false);
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
        tabla.setKeepTogether(true);
        tabla.setMarginTop(20);
        tabla.setMarginBottom(20);
        
        tabla.addHeaderCell("DOCUMENTO");
        tabla.addHeaderCell("NOMBRE");
        tabla.addHeaderCell("EXTENSIÓN");
        
        tabla.getHeader().setBackgroundColor(Color.LIGHT_GRAY);
        tabla.getHeader().setHorizontalAlignment(HorizontalAlignment.CENTER);
        
        Cell celda;
        if (tipoDocumentacion.isEmpty()) {
            celda = new Cell(1, 3);
            celda.add("No se solicita ningún documento");
            tabla.addCell(celda);
        } else {
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
        tabla.setKeepTogether(true);
        tabla.setTextAlignment(TextAlignment.CENTER);
        
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
        document.setTextAlignment(TextAlignment.JUSTIFIED);
        document.add(
                new Paragraph("Nº INSPECCIÓN: " + solicitudDocPrevia.getInspeccion().getNumero()).setMarginTop(30));
        
        document.add(new Paragraph(
                "FECHA: Madrid, " + Utilities.getFechaFormateada(solicitudDocPrevia.getFechaAlta(), "dd/MM/yyyy")));
        
        document.add(new Paragraph("ASUNTO: " + solicitudDocPrevia.getAsunto()));
        
        document.add(
                new Paragraph("DESTINATARIO: " + solicitudDocPrevia.getDestinatario()).setBold().setMarginBottom(20));
        
        document.add(new Paragraph(
                "En virtud de lo establecido por la Instrucción núm. 5/2015, de la Secretaría de Estado de Seguridad, sobre organización y funciones de la Inspección de Personal y Servicios de Seguridad  y en relación con el Plan Anual de Inspecciones aprobado por el Secretario de Estado de Seguridad, se participa que por personal de esta Subdirección, durante el ")
                        .add(new Text(solicitudDocPrevia.getInspeccion().getCuatrimestre().getDescripcion()).setBold())
                        .add(" del año ")
                        .add(new Text(solicitudDocPrevia.getInspeccion().getAnio().toString()).setBold())
                        .add(", se va a realizar una inspección de carácter ")
                        .add(new Text(solicitudDocPrevia.getInspeccion().getTipoInspeccion().getDescripcion())
                                .setBold())
                        .add(" a esa Unidad."));
        
        document.add(new Paragraph(
                "Como se indicó en la anterior comunicación, en la primera fase de la inspección, se realizará la recopilación de información y datos. Dicha recopilación se realizará a través de la aplicación software, Programa de Gestión de Inspecciones “PROGESIN”."));
        
        document.add(new Paragraph("Mediante dicha herramienta informática, deberá enviar a esta IPSS antes del ")
                .add(new Text(Utilities.getFechaFormateada(solicitudDocPrevia.getFechaLimiteEnvio(), "dd/MM/yyyy"))
                        .setBold())
                .add(", con el nombre del fichero que se indicia y en uno de los tipos de archivo que se especifica, los siguientes documentos."));
        
        document.add(crearTablaTipoDocumentacion(listadoDocumentosSolicitud));
        
        document.add(new Paragraph(
                "A la vista de esta documentación y las características de la Unidad, esta IPSS confeccionará un cuestionario que, utilizando la aplicación PROGESIN, deberán cumplimentar y enviar antes del ")
                        .add(new Text(Utilities.getFechaFormateada(solicitudDocPrevia.getFechaLimiteCumplimentar(),
                                "dd/MM/yyyy")).setBold())
                        .add("."));
        
        document.add(new Paragraph(
                "Una vez enviado el cuestionario cumplimentado y validado por esta IPSS, su usuario de la citada aplicación quedará inactivo."));
        
        document.add(
                new Paragraph("Para cualquier aclaración, podrá dirigirse a:").setPaddingBottom(20).setPaddingTop(20));
        
        document.add(crearTablaDatosApoyo(solicitudDocPrevia));
        
        document.add(new Paragraph(
                "El Jefe del Equipo Auditor encargado de dirigir y coordinar la Inspección, próximamente, se pondrá en contacto con el Jefe de esa Unidad a los fines expuestos.")
                        .setPaddingTop(20));
    }
}
