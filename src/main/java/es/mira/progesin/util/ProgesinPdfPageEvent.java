package es.mira.progesin.util;

import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.io.StreamUtil;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.PngImage;

import es.mira.progesin.constantes.Constantes;

/**
 * Generador de cabeceras y pie para PDF de informes en HTML.
 * 
 * @author Ezentis
 */
public class ProgesinPdfPageEvent extends PdfPageEventHelper {
    /**
     * Fuente.
     */
    private BaseFont baseFont = FontFactory.getFont(FontFactory.HELVETICA, Font.BOLD).getBaseFont();
    
    /**
     * Plantilla num página.
     */
    private PdfTemplate totalPages;
    
    /**
     * Tamaño fuente.
     */
    private final static float FOOTERTEXTSIZE = 12f;
    
    /**
     * Inicio del documento.
     */
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        totalPages = writer.getDirectContent().createTemplate(100, 100);
        totalPages.setBoundingBox(new Rectangle(-15, -15, 100, 100));
    }
    
    /**
     * Al finalizar cada página.
     */
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        if (writer.getPageNumber() != 1) {
            PdfContentByte cb = writer.getDirectContent();
            cb.saveState();
            try {
                Image header = PngImage.getImage(StreamUtil.getResourceStream(Constantes.LOGOMININISTERIOINTERIOR));
                header.scaleAbsolute(523, 842);
                header.setAbsolutePosition(document.left() + document.leftMargin(),
                        document.top() + document.topMargin());
                cb.addImage(header);
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
            
            String text = String.format("Página %s de ", writer.getPageNumber());
            
            float textBase = document.bottom() - 15;
            float textSize = baseFont.getWidthPoint(text, FOOTERTEXTSIZE);
            
            cb.beginText();
            cb.setFontAndSize(baseFont, FOOTERTEXTSIZE);
            cb.setTextMatrix((document.right() / 2), textBase);
            cb.showText(text);
            cb.endText();
            cb.addTemplate(totalPages, (document.right() / 2) + textSize, textBase);
            
            cb.restoreState();
        }
    }
    
    /**
     * Al terminar el documento.
     */
    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        totalPages.beginText();
        totalPages.setFontAndSize(baseFont, FOOTERTEXTSIZE);
        totalPages.setTextMatrix(0, 0);
        totalPages.showText(String.valueOf(writer.getPageNumber()));
        totalPages.endText();
    }
}
