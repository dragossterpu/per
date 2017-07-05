package es.mira.progesin.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.contenttype.CTOverride;
import org.docx4j.openpackaging.contenttype.ContentTypeManager;
import org.docx4j.openpackaging.contenttype.ContentTypes;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.org.xhtmlrenderer.util.XRLog;
import org.docx4j.toc.Toc;
import org.docx4j.toc.TocGenerator;
import org.primefaces.model.DefaultStreamedContent;
import org.springframework.stereotype.Component;

import com.itextpdf.text.io.StreamUtil;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.enums.ContentTypeEnum;

/**
 * Genera archivos DOCX de informes de inspecciones compuestos por bloques de texto recogidos por la aplicación a partir
 * de código HTML proviniente del editor de texto de PrimeFaces con estilos propios.
 * 
 * @author EZENTIS
 */
@Component("htmlDocxGenerator")
public class HtmlDocxGenerator {
    
    /**
     * Constructor.
     */
    public HtmlDocxGenerator() {
        XRLog.setLoggingEnabled(false);
    }
    
    // /**
    // * Constructor de elementos wml.
    // */
    // private ObjectFactory objectFactory = Context.getWmlObjectFactory();
    
    /**
     * Documento generado.
     */
    private WordprocessingMLPackage document;
    
    /**
     * Genera archivo DOCX a partir de un documento en XHTML.
     * 
     * @param nombreDocumento nombre del archivo
     * @param documentoXHTML documento en formato XHTML
     * @param fechaFinalizacion fecha en que se termino el documento
     * @param titulo título del documento
     * @param imagenPortada fondo de la portada
     * @param autor usuario que genera el documento
     * @return archivo DOCX
     * @throws ProgesinException al manejar archivos y generar el DOCX
     */
    public DefaultStreamedContent generarInformeDocx(String nombreDocumento, String documentoXHTML, String titulo,
            String fechaFinalizacion, String imagenPortada, String autor) throws ProgesinException {
        DefaultStreamedContent docxStream = null;
        try {
            crearDocumentoConPlantilla(titulo);
            
            incluirCSS();
            
            importarXHTML(documentoXHTML);
            
            incluirTablaContenidos();
            
            docxStream = exportarDocx(nombreDocumento);
            
        } catch (Docx4JException | URISyntaxException | IOException e) {
            throw new ProgesinException(e);
        }
        return docxStream;
    }
    
    /**
     * Genera un nuevo documento a partir de una plantilla dotx.
     * 
     * @param titulo título del documento
     * @throws Docx4JException error al cargar la plantilla
     * @throws URISyntaxException error al cambiar el tipo de dotx a docx
     */
    private void crearDocumentoConPlantilla(String titulo) throws Docx4JException, URISyntaxException {
        document = WordprocessingMLPackage
                .load(StreamUtil.getResourceStream("/documentos/Plantillas/PlantillaInforme.dotx"));
        
        document.setTitle(titulo);
        
        // Replace dotx content type with docx
        ContentTypeManager ctm = document.getContentTypeManager();
        // Get <Override PartName="/word/document.xml"
        // ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.template.main+xml"/>
        CTOverride override = ctm.getOverrideContentType().get(new URI("/word/document.xml"));
        
        // override.setContentType(ContentTypes.WORDPROCESSINGML_DOCUMENT_MACROENABLED); //dotm con macros
        override.setContentType(ContentTypes.WORDPROCESSINGML_DOCUMENT);
    }
    
    /**
     * Reglas de estilos.
     * 
     * @throws UnsupportedEncodingException error al leer utf8
     * @throws IOException error al acceder al css
     */
    private void incluirCSS() throws UnsupportedEncodingException, IOException {
        String css = new String(
                StreamUtil.inputStreamToArray(StreamUtil.getResourceStream(Constantes.CSSTEXTEDITORPDF)), "UTF-8");
        document.getMainDocumentPart().getStyleDefinitionsPart().setCss(css);
    }
    
    /**
     * @param documentoXHTML documento en formato XHTML
     * @throws Docx4JException error al procesar xhtml a docx
     */
    private void importarXHTML(String documentoXHTML) throws Docx4JException {
        XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(document);
        document.getMainDocumentPart().getContent().addAll(xhtmlImporter.convert(documentoXHTML, null));
    }
    
    /**
     * Actualizar la tabla de contenidos del documento word con las áreas y subáreas.
     * @throws Docx4JException error
     */
    private void incluirTablaContenidos() throws Docx4JException {
        Toc.setTocHeadingText("Índice");
        TocGenerator tocGenerator = new TocGenerator(document);
        
        // Tabla con núm. página, usa export-fo
        tocGenerator.generateToc(1, "TOC \\o \"1-3\" \\h \\z \\u ", false);
        
        // // Tabla sin núm. página
        // tocGenerator.generateToc(1, "TOC \\o \"1-3\" \\h \\z \\u ", true);
        // // Forzar actualizar al abrir documento
        // document.getMainDocumentPart().getDocumentSettingsPart().getContents()
        // .setUpdateFields(new BooleanDefaultTrue());
    }
    
    /**
     * Pasa el contenido del documento a un stream para su descarga.
     * 
     * @param nombreDocumento nombre del archivo
     * @return stream
     * @throws Docx4JException error al guardar
     */
    private DefaultStreamedContent exportarDocx(String nombreDocumento) throws Docx4JException {
        DefaultStreamedContent docxStream;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        
        document.save(byteStream);
        
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteStream.toByteArray());
        docxStream = new DefaultStreamedContent(inputStream, ContentTypeEnum.DOCX.getContentType(),
                nombreDocumento + ".docx");
        return docxStream;
    }
    
    // EXPERIMENTAL
    // /**
    // * Pasa el contenido del documento a un stream para su descarga.
    // *
    // * @param nombreDocumento nombre del archivo
    // * @return stream
    // * @throws Docx4JException error al guardar
    // */
    // private DefaultStreamedContent exportarPdf(String nombreDocumento) throws Docx4JException {
    // DefaultStreamedContent docxStream;
    // OutputStream byteStream = new ByteArrayOutputStream();
    //
    // Docx4J.toPDF(document, byteStream);
    //
    // ByteArrayInputStream inputStream = new ByteArrayInputStream(((ByteArrayOutputStream) byteStream).toByteArray());
    // docxStream = new DefaultStreamedContent(inputStream, ContentTypeEnum.PDF.getContentType(),
    // nombreDocumento + ".pdf");
    // return docxStream;
    // }
    
}