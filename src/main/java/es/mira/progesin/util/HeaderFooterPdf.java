package es.mira.progesin.util;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import lombok.Getter;

/**
 * Generador de cabeceras y pie para PDF.
 * 
 * @author EZENTIS
 *
 */
@Getter
public class HeaderFooterPdf implements IEventHandler {
    /**
     * Dcoumento.
     */
    private Document doc;
    
    /**
     * Imagen de cabecera.
     */
    private Image headerRepetido;
    
    /**
     * Imagen de cabecera.
     */
    private Image header1;
    
    /**
     * Imagen de cabecera.
     */
    private Image header2;
    
    /**
     * Imagen de pie.
     */
    private Image footer1;
    
    /**
     * Constructor que recibe como parámetros el documento y las imágenes.
     * 
     * @param document Documento
     * @param imagenUno Imagen 1
     * @param imagenDos Imagen 2
     * @param cabeceraRepetido Imagen
     * @param pie1 Imagen de pie
     */
    public HeaderFooterPdf(Document document, Image imagenUno, Image imagenDos, Image cabeceraRepetido, Image pie1) {
        this.doc = document;
        this.headerRepetido = cabeceraRepetido;
        this.header1 = imagenUno;
        this.header2 = imagenDos;
        this.footer1 = pie1;
    }
    
    /**
     * Crea una cabecera y un pie de solicitud de documentación.
     * 
     * @param event Evento que dispara la generación
     */
    @Override
    public void handleEvent(Event event) {
        createHeaderSolicitudDocumentacion(event);
        if (this.footer1 != null) {
            createFoterSolicitudDocumentacion(event);
        }
    }
    
    /**
     * Crea una cabecera de un documento.
     * 
     * @param event Evento que dispara la generación
     */
    private void createHeaderSolicitudDocumentacion(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        Rectangle pageSize = docEvent.getPage().getPageSize();
        
        // centrado x = (pageSize.getRight() - doc.getRightMargin() - (pageSize.getLeft() + doc.getLeftMargin())) / 2 +
        // doc.getLeftMargin()
        PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
        
        if (pdfDoc.getPageNumber(page) == 1) {
            Rectangle rect = new Rectangle(pdfDoc.getDefaultPageSize().getX() + doc.getLeftMargin(),
                    pdfDoc.getDefaultPageSize().getTop() - doc.getTopMargin(), 523, header1.getImageHeight());
            header1.setFixedPosition(pageSize.getLeft() + doc.getLeftMargin(),
                    pageSize.getTop() - doc.getTopMargin() + 10);
            header2.setFixedPosition(pageSize.getRight() - doc.getRightMargin() - header2.getImageScaledWidth(),
                    pageSize.getTop() - doc.getTopMargin() + 10);
            Canvas c = new Canvas(canvas, pdfDoc, rect);
            c.add(header1);
            c.add(header2);
            c.close();
        } else {
            Rectangle rect = new Rectangle(pdfDoc.getDefaultPageSize().getX() + doc.getLeftMargin(),
                    pdfDoc.getDefaultPageSize().getTop() - doc.getTopMargin(), 523, headerRepetido.getImageHeight());
            headerRepetido.setFixedPosition(
                    pageSize.getRight() - doc.getRightMargin() - headerRepetido.getImageScaledWidth(),
                    pageSize.getTop() - doc.getTopMargin() + 30);
            Canvas c = new Canvas(canvas, pdfDoc, rect);
            c.add(headerRepetido);
            c.close();
        }
    }
    
    /**
     * Crea un pie de página de un documento.
     * 
     * @param event Evento que dispara la generación
     */
    private void createFoterSolicitudDocumentacion(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        Rectangle pageSize = docEvent.getPage().getPageSize();
        
        footer1.setFixedPosition(
                (pageSize.getRight() - doc.getRightMargin() - (pageSize.getLeft() + doc.getLeftMargin())) / 2
                        + doc.getLeftMargin(),
                pageSize.getBottom() + 10);
        
        PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
        Rectangle rectFooter = new Rectangle(pdfDoc.getDefaultPageSize().getX() + doc.getLeftMargin(),
                pdfDoc.getDefaultPageSize().getBottom(), 523, footer1.getImageHeight());
        Canvas c = new Canvas(canvas, pdfDoc, rectFooter);
        c.add(footer1);
        c.close();
    }
    
}