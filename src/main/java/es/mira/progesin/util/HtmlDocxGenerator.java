package es.mira.progesin.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.wml.Br;
import org.docx4j.wml.FldChar;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.STBrType;
import org.docx4j.wml.STFldCharType;
import org.docx4j.wml.Text;
import org.primefaces.model.DefaultStreamedContent;

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
// @Component("htmlDocxGenerator")
public final class HtmlDocxGenerator {
    
    /**
     * Constructor para que no se pueda instanciar la clase.
     */
    private HtmlDocxGenerator() {
        throw new IllegalAccessError("HtmlDocxGenerator class");
    }
    
    /**
     * Genera archivo DOCX a partir de un documento en XHTML.
     * 
     * @param nombreDocumento nombre del archivo
     * @param documentoXHTML documento en formato XHTML
     * @param fechaFinalizacion fecha en que se termino el informe
     * @param titulo título del docx
     * @param imagenPortada fondo de la portada
     * @param autor usuario que genera el informe
     * @return archivo DOCX
     * @throws ProgesinException al manejar archivos y generar el DOCX
     */
    public static DefaultStreamedContent generarInformeDocx(String nombreDocumento, String documentoXHTML,
            String titulo, String fechaFinalizacion, String imagenPortada, String autor) throws ProgesinException {
        DefaultStreamedContent docxStream = null;
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            
            WordprocessingMLPackage document = WordprocessingMLPackage.createPackage();
            document.setTitle(titulo);
            
            // XRLog.setLoggingEnabled(false);
            XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(document);
            
            String css = new String(
                    StreamUtil.inputStreamToArray(StreamUtil.getResourceStream(Constantes.CSSTEXTEDITORPDF)), "UTF-8");
            document.getMainDocumentPart().getStyleDefinitionsPart().setCss(css);
            
            // incluirPortada(document, imagenPortada);
            incluirTablaContenidos(document);
            
            // PRUEBAS
            // removeSDTs(document);
            // TocGenerator tocGenerator = new TocGenerator(document);
            // tocGenerator.generateToc(0, "TOC \\o \"1-3\" \\h \\z \\u \\h", false);
            
            document.getMainDocumentPart().getContent().addAll(xhtmlImporter.convert(documentoXHTML, null));
            // tocGenerator.updateToc(false);
            
            document.save(byteStream);
            
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteStream.toByteArray());
            docxStream = new DefaultStreamedContent(inputStream, ContentTypeEnum.DOCX.getContentType(),
                    nombreDocumento + ".docx");
        } catch (Docx4JException | IOException e) {
            throw new ProgesinException(e);
        }
        return docxStream;
    }
    
    // /**
    // * Añadir al documento word una tabla de contenidos con las áreas y subáreas.
    // *
    // * @param document documento docx
    // * @param imagenPortada imagen de fondo
    // */
    // private static void incluirPortada(WordprocessingMLPackage document, String imagenPortada) {
    // ObjectFactory objectFactory = Context.getWmlObjectFactory();
    // P portada = null;
    // try {
    // byte[] bytesImagen;
    // bytesImagen = StreamUtil.inputStreamToArray(StreamUtil.getResourceStream(imagenPortada));
    // ImagePngPart imagen = (ImagePngPart) ImagePngPart.createImagePart(document, bytesImagen);
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    
    /**
     * Añadir al documento word una tabla de contenidos con las áreas y subáreas.
     * 
     * @param document documento docx
     */
    private static void incluirTablaContenidos(WordprocessingMLPackage document) {
        ObjectFactory objectFactory = Context.getWmlObjectFactory();
        
        // Tabla de contenidos
        P tablaContenidos = objectFactory.createP();
        addFieldBegin(tablaContenidos, objectFactory);
        addTableOfContentField(tablaContenidos, objectFactory);
        addFieldEnd(tablaContenidos, objectFactory);
        document.getMainDocumentPart().getContent().add(tablaContenidos);
        
        // Salto de página
        Br br = objectFactory.createBr();
        br.setType(STBrType.PAGE);
        document.getMainDocumentPart().getContent().add(br);
    }
    
    /**
     * Define la tabla.
     * @param paragraph párrado
     * @param objectFactory constructor
     */
    private static void addTableOfContentField(P paragraph, ObjectFactory objectFactory) {
        R run = objectFactory.createR();
        Text txt = new Text();
        txt.setSpace("preserve");
        txt.setValue("TOC \\o \"1-3\" \\h \\z \\u \\h");
        run.getContent().add(objectFactory.createRInstrText(txt));
        paragraph.getContent().add(run);
    }
    
    /**
     *
     * @param paragraph párrafo
     * @param objectFactory constructor
     */
    private static void addFieldBegin(P paragraph, ObjectFactory objectFactory) {
        R run = objectFactory.createR();
        FldChar fldchar = objectFactory.createFldChar();
        fldchar.setFldCharType(STFldCharType.BEGIN);
        // TODO buscar alternativa para no requerir interaccion usuario
        fldchar.setDirty(true);
        run.getContent().add(getWrappedFldChar(fldchar));
        paragraph.getContent().add(run);
    }
    
    /**
     *
     * @param paragraph párrafo
     * @param objectFactory constructor
     */
    private static void addFieldEnd(P paragraph, ObjectFactory objectFactory) {
        R run = objectFactory.createR();
        FldChar fldcharend = objectFactory.createFldChar();
        fldcharend.setFldCharType(STFldCharType.END);
        run.getContent().add(getWrappedFldChar(fldcharend));
        paragraph.getContent().add(run);
    }
    
    /**
     *
     * @param fldchar caracter
     * @return elemento
     */
    private static JAXBElement<FldChar> getWrappedFldChar(FldChar fldchar) {
        return new JAXBElement<FldChar>(new QName(Namespaces.NS_WORD12, "fldChar"), FldChar.class, fldchar);
    }
    
    // protected static void removeSDTs(WordprocessingMLPackage wmlPackage) throws Docx4JException {
    // RemovalHandler removalHandler;
    // removalHandler = new RemovalHandler();
    // removalHandler.removeSDTs(wmlPackage.getMainDocumentPart(), RemovalHandler.Quantifier.ALL, (String[]) null);
    // for (Part part : wmlPackage.getParts().getParts().values()) {
    // if (part instanceof HeaderPart) {
    // removalHandler.removeSDTs((HeaderPart) part, RemovalHandler.Quantifier.ALL, (String[]) null);
    // } else if (part instanceof FooterPart) {
    // removalHandler.removeSDTs((FooterPart) part, RemovalHandler.Quantifier.ALL, (String[]) null);
    // }
    // }
    // }
    
}