package es.mira.progesin.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabs;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.core.io.ClassPathResource;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.enums.ContentTypeEnum;

/**
 * Clase abastracta para la generación de documentos de Word.
 * 
 * @author EZENTIS
 *
 */
public abstract class WordAbstractGenerator {
    
    /**
     * Constante que contiene la familia de fuentes que se usarán en los documentos.
     */
    protected static final String FONTFAMILY = "Arial";
    
    /**
     * Añade tabulación en un párrafo.
     * 
     * @see #crearCabeceraCuestionario(XWPFDocument, String, String)
     * @param oParagraph Párrafo al que se quiere añadir tabulación
     * @param oSTTabJc tabulación a añadir
     * @param oPos posición donde se añade
     */
    protected void setTabStop(XWPFParagraph oParagraph, STTabJc.Enum oSTTabJc, BigInteger oPos) {
        CTP oCTP = oParagraph.getCTP();
        CTPPr oPPr = oCTP.getPPr();
        if (oPPr == null) {
            oPPr = oCTP.addNewPPr();
        }
        
        CTTabs oTabs = oPPr.getTabs();
        if (oTabs == null) {
            oTabs = oPPr.addNewTabs();
        }
        
        CTTabStop oTabStop = oTabs.addNewTab();
        oTabStop.setVal(oSTTabJc);
        oTabStop.setPos(oPos);
    }
    
    /**
     * Crea la cabecera del documento.
     * 
     * @see #crearDocumentoCuestionarioPersonalizado(CuestionarioPersonalizado)
     * @param doc XWPFDocument Documento donde se insertará la cabecera
     * @throws InvalidFormatException Excepción de formato inválido
     * @throws IOException Excepción de entrada/salida
     */
    protected void crearCabecera(XWPFDocument doc) throws InvalidFormatException, IOException {
        CTSectPr sectPr = doc.getDocument().getBody().getSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc, sectPr);
        
        XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);
        
        XWPFParagraph paragraph = header.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        
        XWPFRun run = paragraph.createRun();
        
        ClassPathResource logo = new ClassPathResource(Constantes.LOGOMININISTERIOINTERIOR);
        XWPFPicture picture = run.addPicture(logo.getInputStream(), XWPFDocument.PICTURE_TYPE_PNG, logo.getPath(),
                Units.toEMU(177 * Constantes.ESCALA), Units.toEMU(90 * Constantes.ESCALA));
        
        String blipID = "";
        for (XWPFPictureData picturedata : header.getAllPackagePictures()) {
            blipID = header.getRelationId(picturedata);
        }
        
        picture.getCTPicture().getBlipFill().getBlip().setEmbed(blipID);
        
        // ponermos la segunda imagen en la parte derecha del documento
        BigInteger pos2 = BigInteger.valueOf(sectPr.getPgSz().getW().intValue() - sectPr.getPgMar().getLeft().intValue()
                - sectPr.getPgMar().getRight().intValue());
        setTabStop(paragraph, STTabJc.Enum.forString("right"), pos2);
        
        run.addTab();
        logo = new ClassPathResource(Constantes.LOGOIPSS);
        XWPFPicture picture2 = run.addPicture(logo.getInputStream(), XWPFDocument.PICTURE_TYPE_PNG, logo.getPath(),
                Units.toEMU(264 * Constantes.ESCALA), Units.toEMU(85 * Constantes.ESCALA));
        blipID = "";
        for (XWPFPictureData picturedata : header.getAllPackagePictures()) {
            blipID = header.getRelationId(picturedata);
        }
        picture2.getCTPicture().getBlipFill().getBlip().setEmbed(blipID);
    }
    
    /**
     * Esta función suple la incapacidad de POI para pintar textos que contengan saltos de línea (carácter '\n').
     * 
     * @param parrafo Párrafo al que se añaden las nuevas líneas
     * @param texto Objeto de tipo XWPFRun donde se añade el texto
     * @param nuevasLineas Array que contiene las líneas acabando con el carácter ('\n')
     */
    protected void textoConSalto(XWPFParagraph parrafo, XWPFRun texto, String[] nuevasLineas) {
        for (int i = 0; i < nuevasLineas.length; i++) {
            
            String textoLinea = nuevasLineas[i];
            if (i == nuevasLineas.length - 1) {
                texto.setText(textoLinea, 0);
                texto.addCarriageReturn();
            } else {
                parrafo.insertNewRun(i);
                XWPFRun newRun = parrafo.getRuns().get(i);
                
                CTRPr rPr;
                if (newRun.getCTR().isSetRPr()) {
                    rPr = newRun.getCTR().getRPr();
                } else {
                    rPr = newRun.getCTR().addNewRPr();
                }
                rPr.set(texto.getCTR().getRPr());
                newRun.setText(textoLinea);
                newRun.addCarriageReturn();
            }
        }
    }
    
    /**
     * 
     * Generación de un objeto StreamedContent que habilita la posibilidad de que el documento sea descargado desde el
     * navegador.
     * 
     * @param doc Documento a partir del cual se desea generar el fichero
     * @param titulo Título con el que se desea generar el fichero
     * @return StreamedContent Flujo generado a partir del fichero y que permitirá la descarga desde el navegador
     * @throws IOException Excepción de entrada/salida
     */
    
    protected StreamedContent exportarFichero(XWPFDocument doc, String titulo) throws IOException {
        ByteArrayOutputStream outputStreamOr = new ByteArrayOutputStream();
        doc.write(outputStreamOr);
        
        InputStream inputStream = new ByteArrayInputStream(outputStreamOr.toByteArray());
        return new DefaultStreamedContent(inputStream, ContentTypeEnum.DOCX.getContentType(), titulo.concat(".docx"));
        
    }
    
    /**
     * Crea el título del documento a partir de una cadena recibida como parámetro. Da formato a la cadena recibida y la
     * anexa como título al documento recibido como parámetro.
     * 
     * @param doc Documento al que se desea anexar el título
     * @param titulo Cadena que contiene el título
     * 
     */
    
    protected void crearTitulo(XWPFDocument doc, String titulo) {
        XWPFParagraph parrafo = doc.createParagraph();
        XWPFRun texto = parrafo.createRun();
        texto.setText(titulo);
        texto.setBold(true);
        texto.setCapitalized(true);
        texto.setFontSize(16);
        texto.setFontFamily(FONTFAMILY);
        parrafo.setSpacingAfterLines(200);
        parrafo.setAlignment(ParagraphAlignment.CENTER);
        parrafo.addRun(texto);
    }
    
    /**
     * Establece el tamaño de página del documento y los márgenes.
     * 
     * @param doc Documento a configurar
     * @param margenIz margen izquierdo
     * @param margenDcho margen derecho
     * @param margenSup margen superior
     * @param margenInf margen inferior
     * @param anchoPag tamaño del ancho de la página
     * @param altoPag tamaño del alto de la página
     */
    protected void setTamanioYMargenDocumento(XWPFDocument doc, long margenIz, long margenDcho, long margenSup,
            long margenInf, long anchoPag, long altoPag) {
        // Tamaño página A4 595x842 en puntos (hay que multiplicarlo por 20)
        // Márgenes del documento
        CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf(margenIz));
        pageMar.setTop(BigInteger.valueOf(margenSup));
        pageMar.setRight(BigInteger.valueOf(margenDcho));
        pageMar.setBottom(BigInteger.valueOf(margenInf));
        
        CTPageSz pageSize = sectPr.addNewPgSz();
        pageSize.setW(BigInteger.valueOf(anchoPag));
        pageSize.setH(BigInteger.valueOf(altoPag));
    }
    
}
