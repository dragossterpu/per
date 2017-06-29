package es.mira.progesin.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.Br;
import org.docx4j.wml.CTSimpleField;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.FldChar;
import org.docx4j.wml.FooterReference;
import org.docx4j.wml.Ftr;
import org.docx4j.wml.Hdr;
import org.docx4j.wml.HdrFtrRef;
import org.docx4j.wml.HeaderReference;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.STBrType;
import org.docx4j.wml.STFldCharType;
import org.docx4j.wml.SectPr;
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
            
            ObjectFactory objectFactory = Context.getWmlObjectFactory();
            
            // XRLog.setLoggingEnabled(false);
            XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(document);
            adaptarModelo(document, objectFactory);
            incluirCSS(document);
            incluirPortada(document, objectFactory, imagenPortada);
            incluirTablaContenidos(document, objectFactory);
            
            // PRUEBAS
            // removeSDTs(document);
            // TocGenerator tocGenerator = new TocGenerator(document);
            // tocGenerator.generateToc(0, "TOC \\o \"1-3\" \\h \\z \\u", false);
            
            document.getMainDocumentPart().getContent().addAll(xhtmlImporter.convert(documentoXHTML, null));
            // tocGenerator.updateToc(false);
            
            document.save(byteStream);
            
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteStream.toByteArray());
            docxStream = new DefaultStreamedContent(inputStream, ContentTypeEnum.DOCX.getContentType(),
                    nombreDocumento + ".docx");
        } catch (Exception e) {
            throw new ProgesinException(e);
        }
        return docxStream;
    }
    
    /**
     * Preparar portada, cabeceras, pies con número de página.
     * 
     * @param document documento docx
     * @param objectFactory constructor
     * @throws Exception error
     */
    private static void adaptarModelo(WordprocessingMLPackage document, ObjectFactory objectFactory) throws Exception {
        // HEADER
        HeaderPart cover_hdr_part = new HeaderPart(new PartName("/word/cover-header.xml"));
        HeaderPart content_hdr_part = new HeaderPart(new PartName("/word/content-header.xml"));
        document.getParts().put(cover_hdr_part);
        document.getParts().put(content_hdr_part);
        // link cover and content headers
        HeaderReference hdr_ref; // this variable is reused
        
        Hdr cover_hdr = objectFactory.createHdr();
        Hdr content_hdr = objectFactory.createHdr();
        // Bind the header JAXB elements as representing their header parts
        
        cover_hdr_part.setJaxbElement(cover_hdr);
        content_hdr_part.setJaxbElement(content_hdr);
        Relationship cover_hdr_rel = document.getMainDocumentPart().addTargetPart(cover_hdr_part);
        Relationship content_hdr_rel = document.getMainDocumentPart().addTargetPart(content_hdr_part);
        
        // FOOTER
        FooterPart cover_ftr_part = new FooterPart(new PartName("/word/cover-footer.xml"));
        FooterPart content_ftr_part = new FooterPart(new PartName("/word/content-footer.xml"));
        document.getParts().put(cover_ftr_part);
        document.getParts().put(cover_ftr_part);
        
        Ftr cover_ftr = objectFactory.createFtr();// getFtr(objectFactory);
        Ftr content_ftr = getNumPagina(objectFactory);
        cover_ftr_part.setJaxbElement(cover_ftr);
        content_ftr_part.setJaxbElement(content_ftr);
        Relationship cover_ftr_rel = document.getMainDocumentPart().addTargetPart(cover_ftr_part);
        Relationship content_ftr_rel = document.getMainDocumentPart().addTargetPart(content_ftr_part);
        // link cover and content footers
        FooterReference ftr_ref; // this variable is reused
        
        // IMAGENES HEADER Y FOOTER
        // cover_hdr.getContent().add(makeParagraph(objectFactory, "Cover header text"));
        content_hdr.getContent().add(cargarImagen(document, content_hdr_part, objectFactory, "static/images/logo.png"));
        content_ftr.getContent().add(0,
                cargarImagen(document, content_ftr_part, objectFactory, "static/images/footer_solicitud_1.png"));
        
        List<SectionWrapper> sections = document.getDocumentModel().getSections();
        
        // Get last section SectPr and create a new one if it doesn't exist
        SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
        if (sectPr == null) {
            sectPr = objectFactory.createSectPr();
            document.getMainDocumentPart().addObject(sectPr);
            sections.get(sections.size() - 1).setSectPr(sectPr);
        }
        
        hdr_ref = objectFactory.createHeaderReference();
        hdr_ref.setId(cover_hdr_rel.getId());
        hdr_ref.setType(HdrFtrRef.FIRST);
        sectPr.getEGHdrFtrReferences().add(hdr_ref);
        
        hdr_ref = objectFactory.createHeaderReference();
        hdr_ref.setId(content_hdr_rel.getId());
        hdr_ref.setType(HdrFtrRef.DEFAULT);
        sectPr.getEGHdrFtrReferences().add(hdr_ref);
        
        ftr_ref = objectFactory.createFooterReference();
        ftr_ref.setId(cover_ftr_rel.getId());
        ftr_ref.setType(HdrFtrRef.FIRST);
        sectPr.getEGHdrFtrReferences().add(ftr_ref);
        
        ftr_ref = objectFactory.createFooterReference();
        ftr_ref.setId(content_ftr_rel.getId());
        ftr_ref.setType(HdrFtrRef.DEFAULT);
        sectPr.getEGHdrFtrReferences().add(ftr_ref);
        
        BooleanDefaultTrue boolanDefaultTrue = new BooleanDefaultTrue();
        sectPr.setTitlePg(boolanDefaultTrue);
    }
    
    /**
     * Reglas de estilos.
     * 
     * @param document documento docx
     * @throws UnsupportedEncodingException error al leer utf8
     * @throws IOException error al acceder al css
     */
    private static void incluirCSS(WordprocessingMLPackage document) throws UnsupportedEncodingException, IOException {
        String css = new String(
                StreamUtil.inputStreamToArray(StreamUtil.getResourceStream(Constantes.CSSTEXTEDITORPDF)), "UTF-8");
        document.getMainDocumentPart().getStyleDefinitionsPart().setCss(css);
    }
    
    /**
     * Añadir al documento word una tabla de contenidos con las áreas y subáreas.
     *
     * @param document documento docx
     * @param imagenPortada imagen de fondo
     * @param objectFactory constructor
     * @throws Exception error
     */
    private static void incluirPortada(WordprocessingMLPackage document, ObjectFactory objectFactory,
            String imagenPortada) throws Exception {
        P portada = cargarImagen(document, document.getMainDocumentPart(), objectFactory, imagenPortada);
        document.getMainDocumentPart().getContent().add(portada);
    }
    
    /**
     * Añadir al documento word una imagen.
     * 
     * @param document documento docx
     * @param objectFactory constructor
     * @param rutaImagen ruta de la imagen
     * @return párrafo con la imagen incrustada
     * @throws Exception error
     */
    private static P cargarImagen(WordprocessingMLPackage document, Part parteDoc, ObjectFactory objectFactory,
            String rutaImagen) throws Exception {
        // Parrafo
        P parrafo = objectFactory.createP();
        R run = objectFactory.createR();
        parrafo.getContent().add(run);
        
        // Imagen
        byte[] bytesImagen = StreamUtil.inputStreamToArray(StreamUtil.getResourceStream(rutaImagen));
        BinaryPartAbstractImage imagen = BinaryPartAbstractImage.createImagePart(document, parteDoc, bytesImagen);
        Inline inline = imagen.createImageInline(null, null, 0, 1, false);
        Drawing canvas = objectFactory.createDrawing();
        run.getContent().add(canvas);
        canvas.getAnchorOrInline().add(inline);
        return parrafo;
    }
    
    /**
     * Añadir al documento word una tabla de contenidos con las áreas y subáreas.
     * 
     * @param document documento docx
     * @param objectFactory constructor
     */
    private static void incluirTablaContenidos(WordprocessingMLPackage document, ObjectFactory objectFactory) {
        P tablaContenidos = objectFactory.createP();
        addFieldBegin(tablaContenidos, objectFactory);
        addTableOfContentField(tablaContenidos, objectFactory);
        addFieldEnd(tablaContenidos, objectFactory);
        document.getMainDocumentPart().getContent().add(tablaContenidos);
        document.getMainDocumentPart().getContent().add(makePageBr(objectFactory));
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
        txt.setValue("TOC \\o \"1-3\" \\h \\z \\u");
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
    
    /**
     * Número de página.
     * 
     * @param objectFactory constructor
     * @return footer
     * @throws Exception error
     */
    public static Ftr getNumPagina(ObjectFactory objectFactory) throws Exception {
        // AddPage Numbers
        CTSimpleField pgnum = objectFactory.createCTSimpleField();
        pgnum.setInstr("PAGE \\* MERGEFORMAT");
        Text de = objectFactory.createText();
        de.setSpace("preserve");
        de.setValue(" de ");
        R r = objectFactory.createR();
        r.getContent().add(de);
        CTSimpleField pgsnum = objectFactory.createCTSimpleField();
        pgsnum.setInstr("NUMPAGES \\* MERGEFORMAT");
        RPr RPr = objectFactory.createRPr();
        RPr.setNoProof(new BooleanDefaultTrue());
        PPr ppr = objectFactory.createPPr();
        Jc jc = objectFactory.createJc();
        jc.setVal(JcEnumeration.CENTER);
        ppr.setJc(jc);
        PPrBase.Spacing pprbase = objectFactory.createPPrBaseSpacing();
        pprbase.setBefore(BigInteger.valueOf(240));
        pprbase.setAfter(BigInteger.valueOf(0));
        ppr.setSpacing(pprbase);
        
        R run = objectFactory.createR();
        run.getContent().add(RPr);
        pgnum.getContent().add(run);
        pgsnum.getContent().add(run);
        
        JAXBElement<CTSimpleField> fldSimple = objectFactory.createPFldSimple(pgnum);
        JAXBElement<CTSimpleField> fldSimple2 = objectFactory.createPFldSimple(pgsnum);
        P para = objectFactory.createP();
        para.getContent().add(fldSimple);
        para.getContent().add(r);
        para.getContent().add(fldSimple2);
        para.setPPr(ppr);
        // Now add our paragraph to the footer
        Ftr ftr = objectFactory.createFtr();
        ftr.getContent().add(para);
        return ftr;
    }
    
    /**
     * Parrafo.
     * 
     * @param objectFactory constructor
     * @param text texto
     * @return parrafo
     */
    private static P makeParagraph(ObjectFactory objectFactory, String text) {
        P p = objectFactory.createP();
        R r = objectFactory.createR();
        p.getContent().add(r);
        Text t = objectFactory.createText();
        r.getContent().add(t);
        t.setValue(text);
        return p;
    }
    
    /**
     * Salto de página.
     * 
     * @param objectFactory constructor
     * @return parrafo
     * @throws Exception error
     */
    private static P makePageBr(ObjectFactory objectFactory) {
        P p = objectFactory.createP();
        R r = objectFactory.createR();
        Br br = objectFactory.createBr();
        br.setType(STBrType.PAGE);
        r.getContent().add(br);
        p.getContent().add(r);
        return p;
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