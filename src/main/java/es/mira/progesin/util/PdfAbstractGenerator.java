package es.mira.progesin.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.enums.ContentTypeEnum;

/**
 * Clase para la generación de documentos PDF de la aplicación.
 * 
 * @author EZENTIS
 *
 */
public abstract class PdfAbstractGenerator {
    
    /**
     * Crea la cabecera y el footer del documento.
     * 
     * @param document Dónde se insertará el header y el footer
     * @param insertarFooter Indica si hay que poner un footer al documento o sólo se inserta una cabecera
     * @throws IOException excepción que puede lanzar
     */
    protected void crearCabeceraFooter(Document document, boolean insertarFooter) throws IOException {
        // Footer
        String footer = null;
        if (insertarFooter) {
            footer = Constantes.LOGOCALIDAD;
        }
        
        HeaderFooterPdf handler = new HeaderFooterPdf(document, Constantes.LOGOMININISTERIOINTERIOR,
                Constantes.LOGOIPSS, Constantes.HEADERSOLDOCPAG1, footer);
        document.getPdfDocument().addEventHandler(PdfDocumentEvent.END_PAGE, handler);
    }
    
    /**
     * Método a implementar por las clases que lo hereden. Será el encargado de crear contenido del PDF.
     * 
     * @return StreamedContent necesario para el componente p:fileDownload de primefaces
     * @throws ProgesinException excepción que lanzará si se produce algún error
     */
    public abstract StreamedContent exportarPdf() throws ProgesinException;
    
    /**
     * Crea el documento pdf.
     * 
     * @param nombrePdf Nombre del pdf.
     * @param footer Indica si hay que hay añadir un footer con imagen (el logo de calidad)
     * @param insertarPagina Inserta el número de página (incompatible con el otro footer)
     * @return StreamedContent streamcontent para usar el fileDownload de primefaces
     * @throws ProgesinException posible excepción
     */
    protected StreamedContent crearPdf(String nombrePdf, boolean footer, boolean insertarPagina)
            throws ProgesinException {
        StreamedContent pdfStream = null;
        
        try (ByteArrayOutputStream outputStreamOr = new ByteArrayOutputStream();
                PdfWriter writer = new PdfWriter(outputStreamOr);
                PdfDocument pdf = new PdfDocument(writer)) {
            
            // Initialize document
            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(70, 36, 70, 36);
            
            crearCabeceraFooter(document, footer);
            
            crearCuerpoPdf(document);
            
            document.close();
            
            ByteArrayInputStream inputStreamOr = new ByteArrayInputStream(outputStreamOr.toByteArray());
            
            if (insertarPagina && !footer) {
                ByteArrayOutputStream outputStreamPagina = insertarNumeroPagina(inputStreamOr);
                InputStream inputStreamPagina = new ByteArrayInputStream(outputStreamPagina.toByteArray());
                pdfStream = new DefaultStreamedContent(inputStreamPagina, ContentTypeEnum.PDF.getContentType(),
                        nombrePdf);
                inputStreamPagina.close();
            } else {
                pdfStream = new DefaultStreamedContent(inputStreamOr, ContentTypeEnum.PDF.getContentType(), nombrePdf);
            }
            inputStreamOr.close();
        } catch (IOException e) {
            throw new ProgesinException(e);
        }
        
        return pdfStream;
    }
    
    /**
     * Genera el contenido que se mostrará en el PDF.
     * 
     * @param documento Documento pdf al que se adjuntará el contenido
     * @throws ProgesinException excepción que puede lanzar
     */
    public abstract void crearCuerpoPdf(Document documento) throws ProgesinException;
    
    /**
     * Inserta el número de página del documento (Página i de n).
     * 
     * @param inputOrigen InputStream que lee para calcular el número total de páginas
     * @return OutputStream generado en la información de la página.
     * @throws IOException posible excepción
     */
    protected ByteArrayOutputStream insertarNumeroPagina(InputStream inputOrigen) throws IOException {
        PdfReader reader = new PdfReader(inputOrigen);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(reader, writer);
        Document doc = new Document(pdfDoc);
        int numPaginas = pdfDoc.getNumberOfPages();
        for (int i = 1; i <= numPaginas; i++) {
            doc.showTextAligned(new Paragraph(String.format("Página %s de %s", i, numPaginas)),
                    (pdfDoc.getDefaultPageSize().getRight() - doc.getRightMargin()
                            - (pdfDoc.getDefaultPageSize().getLeft() + doc.getLeftMargin())) / 2 + doc.getLeftMargin(),
                    pdfDoc.getDefaultPageSize().getBottom() + 20, i, TextAlignment.CENTER, VerticalAlignment.BOTTOM, 0);
        }
        doc.close();
        return outputStream;
    }
}
