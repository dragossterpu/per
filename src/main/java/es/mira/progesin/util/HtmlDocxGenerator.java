package es.mira.progesin.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.primefaces.model.DefaultStreamedContent;

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
        throw new IllegalAccessError("HtmlDoccGenerator class");
    }
    
    /**
     * Genera archivo DOCX a partir de un documento en XHTML.
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
    
    public static DefaultStreamedContent generarInformeDocx(String nombreDocumento, String documentoXHTML,
            String titulo, String fechaFinalizacion, String imagenPortada, String autor) throws ProgesinException {
        DefaultStreamedContent docxStream = null;
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            
            WordprocessingMLPackage document = WordprocessingMLPackage.createPackage();
            
            XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(document);
            
            document.getMainDocumentPart().getContent().addAll(xhtmlImporter.convert(documentoXHTML, null));
            
            document.save(byteStream);
            
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteStream.toByteArray());
            docxStream = new DefaultStreamedContent(inputStream, ContentTypeEnum.DOCX.getContentType(),
                    nombreDocumento + ".docx");
        } catch (Docx4JException e) {
            throw new ProgesinException(e);
        }
        return docxStream;
    }
    
}