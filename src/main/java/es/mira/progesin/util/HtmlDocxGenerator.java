package es.mira.progesin.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.docx4j.Docx4J;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.docProps.core.dc.elements.ObjectFactory;
import org.docx4j.docProps.core.dc.elements.SimpleLiteral;
import org.docx4j.openpackaging.contenttype.CTOverride;
import org.docx4j.openpackaging.contenttype.ContentTypeManager;
import org.docx4j.openpackaging.contenttype.ContentTypes;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.toc.Toc;
import org.docx4j.toc.TocGenerator;
import org.docx4j.toc.TocHelper;
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
     * Documento generado.
     */
    private WordprocessingMLPackage documento;
    
    /**
     * Genera archivo DOCX a partir de un documento en XHTML.
     * 
     * @param nombreDocumento nombre del archivo
     * @param documentoXHTML documento en formato XHTML
     * @param fechaFinalizacion fecha en que se termino el documento
     * @param titulo título del documento
     * @param autor usuario que genera el documento
     * @return archivo DOCX
     * @throws ProgesinException al manejar archivos y generar el DOCX
     */
    public DefaultStreamedContent generarInformeDocx(String nombreDocumento, String documentoXHTML, String titulo,
            String fechaFinalizacion, String autor) throws ProgesinException {
        DefaultStreamedContent docxStream = null;
        try {
            crearDocumentoConPlantilla(titulo, autor, fechaFinalizacion);
            
            incluirCSS();
            
            importarXHTML(documentoXHTML);
            
            incluirTablaContenidos();
            
            docxStream = exportarDocx(nombreDocumento);
            // docxStream = exportarPdf(nombreDocumento);
            
        } catch (Docx4JException | URISyntaxException | IOException e) {
            throw new ProgesinException(e);
        }
        return docxStream;
    }
    
    /**
     * Genera un nuevo documento a partir de una plantilla dotx.
     * 
     * @param titulo título del documento
     * @param autor autor del documento
     * @param fechaFinalizacion fecha fin del informe
     * @throws Docx4JException error al cargar la plantilla
     * @throws URISyntaxException error al cambiar el tipo de dotx a docx
     * @throws IOException error al cerrar el stream
     */
    private void crearDocumentoConPlantilla(String titulo, String autor, String fechaFinalizacion)
            throws Docx4JException, URISyntaxException, IOException {
        InputStream plantillaStream = StreamUtil.getResourceStream(Constantes.PLANTILLAINFORMEDOTX);
        documento = WordprocessingMLPackage.load(plantillaStream);
        plantillaStream.close();
        
        documento.setTitle(titulo);
        ObjectFactory of = new ObjectFactory();
        SimpleLiteral creator = of.createSimpleLiteral();
        creator.getContent().add(autor);
        documento.getDocPropsCorePart().getContents().setCreator(creator);
        SimpleLiteral created = of.createSimpleLiteral();
        created.getContent().add(fechaFinalizacion);
        documento.getDocPropsCorePart().getContents().setCreated(created);
        
        // Replace dotx content type with docx
        ContentTypeManager ctm = documento.getContentTypeManager();
        // Get <Override PartName="/word/document.xml"
        // ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.template.main+xml"/>
        CTOverride override = ctm.getOverrideContentType().get(new URI("/word/document.xml"));
        
        override.setContentType(ContentTypes.WORDPROCESSINGML_DOCUMENT);
    }
    
    /**
     * Reglas de estilos.
     * 
     * @throws UnsupportedEncodingException error al leer utf8
     * @throws IOException error al acceder al css
     */
    private void incluirCSS() throws UnsupportedEncodingException, IOException {
        InputStream cssStream = StreamUtil.getResourceStream(Constantes.CSSTEXTEDITORPDF);
        String css = new String(StreamUtil.inputStreamToArray(cssStream), "UTF-8");
        cssStream.close();
        documento.getMainDocumentPart().getStyleDefinitionsPart().setCss(css);
    }
    
    /**
     * @param documentoXHTML documento en formato XHTML
     * @throws Docx4JException error al procesar xhtml a docx
     */
    private void importarXHTML(String documentoXHTML) throws Docx4JException {
        XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(documento);
        documento.getMainDocumentPart().getContent().addAll(xhtmlImporter.convert(documentoXHTML, null));
    }
    
    /**
     * Incluir en el documento word una tabla de contenidos detrás de la portada desplazando el resto del contenido.
     * @throws Docx4JException error
     */
    private void incluirTablaContenidos() throws Docx4JException {
        Toc.setTocHeadingText("Índice");
        TocGenerator tocGenerator = new TocGenerator(documento);
        
        // Tabla con núm. página, usa export-fo
        tocGenerator.generateToc(2, TocHelper.DEFAULT_TOC_INSTRUCTION, false);
        
        // Tabla sin núm. página
        // tocGenerator.generateToc(2, TocHelper.DEFAULT_TOC_INSTRUCTION, true);
        // Forzar actualizar al abrir documento
        // documento.getMainDocumentPart().getDocumentSettingsPart().getContents()
        // .setUpdateFields(new BooleanDefaultTrue());
    }
    
    /**
     * Pasa el contenido del documento a un stream para su descarga.
     * 
     * @param nombreDocumento nombre del archivo
     * @return stream
     * @throws Docx4JException error al guardar
     * @throws IOException error al cerrar el outputstream
     */
    private DefaultStreamedContent exportarDocx(String nombreDocumento) throws Docx4JException, IOException {
        DefaultStreamedContent contenidoDOCX;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        documento.save(outputStream);
        
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        
        outputStream.close();
        
        contenidoDOCX = new DefaultStreamedContent(inputStream, ContentTypeEnum.DOCX.getContentType(),
                nombreDocumento + ".docx");
        return contenidoDOCX;
    }
    
    /**
     * Pasa el contenido del documento a un stream para su descarga.
     *
     * @param nombreDocumento nombre del archivo
     * @return stream
     * @throws Docx4JException error al guardar
     * @throws IOException error al cerrar el outputstream
     */
    private DefaultStreamedContent exportarPdf(String nombreDocumento) throws Docx4JException, IOException {
        DefaultStreamedContent contenidoPDF;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        Docx4J.toPDF(documento, outputStream);
        
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        
        outputStream.close();
        
        contenidoPDF = new DefaultStreamedContent(inputStream, ContentTypeEnum.PDF.getContentType(),
                nombreDocumento + ".pdf");
        return contenidoPDF;
    }
    
}