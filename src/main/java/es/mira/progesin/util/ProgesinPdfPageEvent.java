package es.mira.progesin.util;

import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.io.StreamUtil;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.PngImage;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.services.RegistroActividadService;

/**
 * Generador de cabeceras y pie para PDF de informes en HTML.
 * 
 * @author Ezentis
 */
public class ProgesinPdfPageEvent extends PdfPageEventHelper {
    /**
     * Fuente.
     */
    private BaseFont baseFont = FontFactory.getFont(FontFactory.HELVETICA).getBaseFont();
    
    /**
     * Plantilla num página.
     */
    private PdfTemplate totalPages;
    
    /**
     * Tamaño fuente.
     */
    private static final float FOOTERTEXTSIZE = 10f;
    
    /**
     * Servicio para el registro de actividad.
     */
    private transient RegistroActividadService regActividad;
    
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
        // Excluir portada
        if (writer.getPageNumber() != 1) {
            try {
                
                insertarCabecera(document);
                
                insertarPie(writer, document);
                
            } catch (ProgesinException e) {
                regActividad.altaRegActividadError(SeccionesEnum.INFORMES.getDescripcion(), e);
            }
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
    
    /**
     * @param document pdf
     * @throws ProgesinException error
     */
    private void insertarCabecera(Document document) throws ProgesinException {
        try {
            Image cabecera = PngImage.getImage(StreamUtil.getResourceStream(Constantes.CABECERAINFORMEPDF));
            cabecera.scaleToFit(document.right() - document.leftMargin(), document.topMargin());
            cabecera.setAbsolutePosition((document.right() + document.leftMargin() - cabecera.getScaledWidth()) / 2,
                    document.top());
            document.add(cabecera);
        } catch (IOException | DocumentException e) {
            throw new ProgesinException(e);
        }
        
    }
    
    /**
     * @param writer writer
     * @param document pdf
     * @throws ProgesinException error
     */
    private void insertarPie(PdfWriter writer, Document document) throws ProgesinException {
        try {
            Image pie = PngImage.getImage(StreamUtil.getResourceStream(Constantes.PIEINFORMEPDF));
            pie.scaleToFit(document.right() - document.leftMargin(), document.bottomMargin());
            pie.setAbsolutePosition((document.right() + document.leftMargin() - pie.getScaledWidth()) / 2,
                    document.bottom() - 60);
            document.add(pie);
            
            PdfContentByte cb = writer.getDirectContent();
            cb.saveState();
            
            // Título informe
            ColumnText ctT = new ColumnText(cb);
            ctT.setSimpleColumn(document.left(), document.bottom() - 60, 250, 60);
            ctT.setAlignment(Element.ALIGN_RIGHT);
            ctT.addElement(new Paragraph(document.getAccessibleAttribute(PdfName.TITLE).toString(),
                    FontFactory.getFont(FontFactory.HELVETICA, FOOTERTEXTSIZE)));
            ctT.go();
            
            // Número de página
            String text = String.format("Página %s de ", writer.getPageNumber());
            float textBase = document.bottom() - 45;
            float textSize = baseFont.getWidthPoint(text, FOOTERTEXTSIZE);
            cb.beginText();
            cb.setFontAndSize(baseFont, FOOTERTEXTSIZE);
            cb.setTextMatrix(document.right() - document.left() / 2 - textSize, textBase);
            cb.showText(text);
            cb.endText();
            cb.addTemplate(totalPages, document.right() - document.left() / 2, textBase);
            
            cb.restoreState();
        } catch (IOException | DocumentException e) {
            throw new ProgesinException(e);
        }
    }
    
}
