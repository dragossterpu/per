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

@Getter
public class HeaderFooterPdf implements IEventHandler {

	protected Document doc;

	protected Image imagen1;

	protected Image imagen2;

	public HeaderFooterPdf(Document doc, Image imagen1, Image imagen2) {
		this.doc = doc;
		this.imagen1 = imagen1;
		this.imagen2 = imagen2;
	}

	@Override
	public void handleEvent(Event event) {
		addHeader(event);
		PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
		PdfDocument pdfDoc = docEvent.getDocument();
		PdfPage page = docEvent.getPage();
		PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
		Rectangle rect = new Rectangle(pdfDoc.getDefaultPageSize().getX() + doc.getLeftMargin(),
				pdfDoc.getDefaultPageSize().getTop() - doc.getTopMargin(), 523, imagen1.getImageHeight());
		new Canvas(canvas, pdfDoc, rect).add(imagen1);
		new Canvas(canvas, pdfDoc, rect).add(imagen2);
	}

	private void addHeader(Event event) {
		PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
		Rectangle pageSize = docEvent.getPage().getPageSize();

		// centrado x = (pageSize.getRight() - doc.getRightMargin() - (pageSize.getLeft() + doc.getLeftMargin())) / 2 +
		// doc.getLeftMargin()
		imagen1.setFixedPosition(pageSize.getLeft() + doc.getLeftMargin(), pageSize.getTop() - doc.getTopMargin() + 10);

		imagen2.setFixedPosition(pageSize.getRight() - doc.getRightMargin() - imagen2.getImageScaledWidth(),
				pageSize.getTop() - doc.getTopMargin() + 10);
		imagen1.setMarginBottom(100);
	}

}