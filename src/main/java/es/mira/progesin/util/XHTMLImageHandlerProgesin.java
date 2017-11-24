package es.mira.progesin.util;

import org.docx4j.UnitsOfMeasurement;
import org.docx4j.convert.in.xhtml.XHTMLImageHandlerDefault;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.org.xhtmlrenderer.docx.Docx4jUserAgent;
import org.docx4j.wml.P;
import org.w3c.dom.Element;

/**
 * Gestor de imágenes que corrige el tamaño asignado por Docx4j a las imágenes importadas de un documento html.
 * 
 * @author Ezentis
 */
public class XHTMLImageHandlerProgesin extends XHTMLImageHandlerDefault {
    
    /**
     * @param importer que usa este gestor de imágenes.
     */
    public XHTMLImageHandlerProgesin(XHTMLImporterImpl importer) {
        super(importer);
    }
    
    /**
     * Cambia la llamada con el alto y ancho incorrectos y deja que que se calculen más adelante.
     */
    @Override
    public void addImage(Docx4jUserAgent docx4jUserAgent, WordprocessingMLPackage wordMLPackage, P p, Element e,
            Long cx, Long cy) {
        Long e_cx = UnitsOfMeasurement.twipToEMU(UnitsOfMeasurement.pxToTwip(Float.valueOf(e.getAttribute("width"))));
        Long e_cy = UnitsOfMeasurement.twipToEMU(UnitsOfMeasurement.pxToTwip(Float.valueOf(e.getAttribute("height"))));
        super.addImage(docx4jUserAgent, wordMLPackage, p, e, e_cx, e_cy);
    }
    
}
