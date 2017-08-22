package es.mira.progesin.util;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;

import com.itextpdf.io.image.ImageDataFactory;
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

import es.mira.progesin.constantes.Constantes;
import lombok.Getter;
import lombok.Setter;

/**
 * Generador de cabeceras y pie para PDF.
 * 
 * @author EZENTIS
 *
 */
@Getter
@Setter
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
     * 
     * @param document Documento al que se quiere añadir el header/footer
     * @param imagenUno Ruta de la imagen en la primera posición del header
     * @param imagenDos Ruta de la imagen en la segunda posición del header
     * @param cabeceraRepetido Ruta de la imagen que se repite en todas las páginas del header (salvo la primera)
     * @param pie Ruta de la imagen del footer
     * @throws IOException excepción se puede lanzar al crear las imágenes a partir de las rutas
     */
    public HeaderFooterPdf(Document document, String imagenUno, String imagenDos, String cabeceraRepetido, String pie) throws IOException {
        this.doc = document;
        this.headerRepetido = crearImagen(cabeceraRepetido);
        this.header1 = crearImagen(imagenUno);
        this.header2 = crearImagen(imagenDos);
        this.footer1 = crearImagen(pie);
    } 
    /**
     * Crea una cabecera y un pie de solicitud de documentación.
     * 
     * @param event Evento que dispara la generación
     */
    @Override
    public void handleEvent(Event event) {
        crearHeader(event);
        if (this.footer1 != null) {
            crearFooter(event);
        }
    }
    
    /**
     * Crea una cabecera de un documento.
     * 
     * @param event Evento que dispara la generación
     */
    private void crearHeader(Event event) {
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
    private void crearFooter(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        Rectangle pageSize = docEvent.getPage().getPageSize();
        
        footer1.setFixedPosition(
                (pageSize.getRight() - doc.getRightMargin() - (pageSize.getLeft() + doc.getLeftMargin())) / 2
                        + doc.getLeftMargin(),
                pageSize.getBottom() + 10);
       
        PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
        Rectangle rectFooter = new Rectangle(pdfDoc.getDefaultPageSize().getX() + doc.getLeftMargin(),
                pdfDoc.getDefaultPageSize().getBottom(), 523, footer1.getImageHeight());
        
        Canvas canvas = new Canvas(pdfCanvas, pdfDoc, rectFooter);
        canvas.add(footer1);
        canvas.close();
    }
    
    /**
     * Crea una imagen a partir de la ruta.
     * 
     * @param path Ruta de la imagen
     * @return Imagen de itext
     * @throws IOException Excepción que se puede lanzar al crear la imagen
     */
    private Image crearImagen(String path) throws IOException {
        Image imagen = null;
        if (path != null) {
            File file = new ClassPathResource(path).getFile();
            imagen = new Image(ImageDataFactory.create(file.getPath()));
            imagen.scaleAbsolute(imagen.getImageWidth() * Constantes.ESCALA,
                    imagen.getImageHeight() *  Constantes.ESCALA);
        }
        return imagen;
    }
}