package es.mira.progesin.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.primefaces.model.DefaultStreamedContent;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.io.StreamUtil;
import com.itextpdf.text.pdf.PdfPageEvent;
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
// @Component("htmlPdfGenerator")
public final class HtmlPdfGenerator {
    
    /**
     * Constructor para que no se pueda instanciar la clase.
     */
    private HtmlPdfGenerator() {
        throw new IllegalAccessError("HtmlPdfGenerator class");
    }
    
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
    public static DefaultStreamedContent generarInformePdf(String nombreDocumento, String documentoXHTML, String titulo,
            String fechaFinalizacion, String autor) throws ProgesinException {
        
        DefaultStreamedContent pdfStream = null;
        
        try {
            // Archivo temporal
            // File file = File.createTempFile(nombreDocumento, ".pdf");
            // OutputStream fileOS = new FileOutputStream(file);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            
            // Initialize PDF document A4
            Document documento = new Document();
            
            // Initialize PDF writer
            PdfWriter writer = PdfWriter.getInstance(documento, byteStream);
            
            // Eventos para incluir cabecera, pie y número de página
            PdfPageEvent pdfPageEvent = new ProgesinPdfPageEvent();
            writer.setPageEvent(pdfPageEvent);
            
            // Open document
            documento.open();
            documento.addTitle(titulo);
            documento.addAuthor(autor);
            documento.addCreationDate();
            
            // Portada
            generarPortada(documento, titulo, fechaFinalizacion);
            
            // Márgenes para el resto de páginas
            documento.setMargins(70, 36, 70, 36);
            
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
     * @param titulo texto portada
     * @param fechaFinalizacion fecha
     */
    private static void generarPortada(Document documento, String titulo, String fechaFinalizacion) {
        try {
            Image png = PngImage.getImage(StreamUtil.getResourceStream(Constantes.PORTADAINFORME));
            png.scaleAbsolute(595, 842);
            documento.setMargins(0, 0, 0, 0);
            documento.newPage();
            documento.add(png);
            // TODO incluir título y fecha finalización encima de la imagen de fondo.
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
    
}
