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
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

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
     * @param imagenPortada fondo de la portada
     * @param autor usuario que genera el informe
     * @return archivo PDF
     * @throws ProgesinException al manejar archivos y generar el PDF
     */
    public static DefaultStreamedContent generarInformePdf(String nombreDocumento, String documentoXHTML, String titulo,
            String fechaFinalizacion, String imagenPortada, String autor) throws ProgesinException {
        
        DefaultStreamedContent pdfStream = null;
        
        try {
            // Archivo temporal
            // File file = File.createTempFile(nombreDocumento, ".pdf");
            // OutputStream fileOS = new FileOutputStream(file);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            
            // Initialize PDF document A4
            Document document = new Document();
            
            // Initialize PDF writer
            PdfWriter writer = PdfWriter.getInstance(document, byteStream);
            
            // Eventos para incluir cabecera, pie y número de página
            PdfPageEvent pdfPageEvent = new ProgesinPdfPageEvent();
            writer.setPageEvent(pdfPageEvent);
            
            // Open document
            document.open();
            document.addTitle(titulo);
            document.addAuthor(autor);
            document.addCreationDate();
            
            // Portada
            generarPortada(document, titulo, fechaFinalizacion, imagenPortada);
            
            // Márgenes para el resto de páginas
            document.setMargins(70, 36, 70, 36);
            
            // CSS
            CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
            CssFile cssTextEditor = XMLWorkerHelper.getCSS(StreamUtil.getResourceStream(Constantes.CSSTEXTEDITORPDF));
            cssResolver.addCss(cssTextEditor);
            
            // HTML
            HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
            htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
            htmlContext.setImageProvider(new Base64ImageProvider());
            
            // Pipelines
            PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
            HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
            CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
            
            // XML Worker
            XMLWorker worker = new XMLWorker(css, true);
            XMLParser parser = new XMLParser(worker);
            
            // Volcar XHTML en el PDF
            parser.parse(new ByteArrayInputStream(documentoXHTML.getBytes()));
            
            // InputStream html = new ByteArrayInputStream(limpiarHtml(documentoHTML).getBytes());
            // InputStream css = StreamUtil.getResourceStream(Constantes.CSSTEXTEDITORPDF);
            // XMLWorkerHelper xmlWorkerHelper = XMLWorkerHelper.getInstance();
            // xmlWorkerHelper.parseXHtml(writer, document, html, css);
            
            // Close document
            document.close();
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
     * @param document documento generado
     * @param titulo texto portada
     * @param fechaFinalizacion fecha
     * @param imagenPortada plantilla fondo
     */
    private static void generarPortada(Document document, String titulo, String fechaFinalizacion,
            String imagenPortada) {
        try {
            Image png = PngImage.getImage(StreamUtil.getResourceStream(imagenPortada));
            png.scaleAbsolute(595, 842);
            document.setMargins(0, 0, 0, 0);
            document.newPage();
            document.add(png);
            // TODO incluir título y fecha finalización encima de la imagen de fondo.
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
    
}
