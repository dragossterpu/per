package es.mira.progesin.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.primefaces.model.DefaultStreamedContent;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.io.StreamUtil;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.PngImage;
import com.itextpdf.tool.xml.parser.XMLParser;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.enums.ContentTypeEnum;

/**
 * Genera archivos PDF de informes de inspecciones compuestos por bloques de texto recogidos por la aplicación a partir
 * de código HTML proviniente del editor de texto de PrimeFaces con estilos propios.
 * 
 * @author EZENTIS
 */
@Component("htmlPdfGenerator")
public class HtmlPdfGenerator {
    
    /**
     * Genera archivo PDF a partir de un documento en XHTML.
     * 
     * @param nombreDocumento nombre del archivo
     * @param documentoXHTML documento en formato XHTML
     * @param fechaFinalizacion fecha en que se termino el informe
     * @param titulo título del pdf
     * @param autor usuario que genera el informe
     * @return archivo PDF
     * @throws ProgesinException al manejar archivos y generar el PDF
     */
    public DefaultStreamedContent generarInformePdf(String nombreDocumento, String documentoXHTML, String titulo,
            String fechaFinalizacion, String autor) throws ProgesinException {
        
        DefaultStreamedContent pdfStream = null;
        
        try {
            // Archivo temporal
            // File file = File.createTempFile(nombreDocumento, ".pdf");
            // OutputStream fileOS = new FileOutputStream(file);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            
            // Initialize PDF document A4
            Document documento = new Document();
            
            // Metadatos
            documento.addTitle(titulo);
            documento.addAuthor(autor);
            documento.setAccessibleAttribute(PdfName.TITLE, new PdfString(titulo));
            
            // Initialize PDF writer
            PdfWriter writer = PdfWriter.getInstance(documento, byteStream);
            
            // Eventos para incluir cabecera, pie y número de página
            PdfPageEvent pdfPageEvent = new ProgesinPdfPageEvent();
            writer.setPageEvent(pdfPageEvent);
            
            // Open document
            documento.open();
            
            // Portada
            generarPortada(documento, writer, titulo, fechaFinalizacion);
            
            // Márgenes para el resto de páginas
            documento.setMargins(40, 40, 80, 80);
            documento.newPage();
            
            // Parser
            XMLParser parser = new ProgesinXMLParser(documento, writer);
            
            // Volcar XHTML en el PDF
            parser.parse(new ByteArrayInputStream(documentoXHTML.getBytes()));
            
            // Close document
            documento.close();
            writer.close();
            
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteStream.toByteArray());
            // InputStream inputStream = new FileInputStream(file);
            pdfStream = new DefaultStreamedContent(inputStream, ContentTypeEnum.PDF.getContentType(),
                    nombreDocumento + ".pdf");
            
        } catch (IOException | DocumentException e) {
            throw new ProgesinException(e);
        }
        return pdfStream;
    }
    
    /**
     * Generar portada con una imagen de plantilla.
     * 
     * @param documento documento generado
     * @param writer writer
     * @param titulo texto portada
     * @param fechaFinalizacion fecha
     * @throws IOException error
     * @throws DocumentException error
     */
    private void generarPortada(Document documento, PdfWriter writer, String titulo, String fechaFinalizacion)
            throws IOException, DocumentException {
        float width = PageSize.A4.getWidth();
        float height = PageSize.A4.getHeight();
        Image png = PngImage.getImage(StreamUtil.getResourceStream(Constantes.PORTADAINFORME));
        
        PdfTemplate portada = writer.getDirectContent().createTemplate(width, height);
        portada.addImage(png, width, 0, 0, height, 0, 0);
        
        ColumnText ctT = new ColumnText(portada);
        ctT.setSimpleColumn(24, 540, 400, 300);
        ctT.setAlignment(Element.ALIGN_CENTER);
        ctT.setLeading(28);
        ctT.setText(new Phrase(titulo, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24)));
        ctT.go();
        
        ColumnText ctF = new ColumnText(portada);
        ctF.setSimpleColumn(890, 100, 50, 50);
        ctF.setAlignment(Element.ALIGN_CENTER);
        ctF.setLeading(22);
        ctF.setText(new Phrase(fechaFinalizacion, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        ctF.go();
        
        documento.setMargins(0, 0, 0, 0);
        documento.newPage();
        documento.add(Image.getInstance(portada));
    }
    
}
