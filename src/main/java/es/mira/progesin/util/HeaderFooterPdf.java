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

	protected Image headerRepetido;

	protected Image header1;

	protected Image header2;

	protected Image footer1;

	public HeaderFooterPdf(Document doc, Image imagen1, Image imagen2, Image headerRepetido, Image footer1) {
		this.doc = doc;
		this.headerRepetido = headerRepetido;
		this.header1 = imagen1;
		this.header2 = imagen2;
		this.footer1 = footer1;
	}

	@Override
	public void handleEvent(Event event) {
		createHeaderSolicitudDocumentacion(event);
		if (this.footer1 != null) {
			createFoterSolicitudDocumentacion(event);
		}
	}

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
			new Canvas(canvas, pdfDoc, rect).add(header1);
			new Canvas(canvas, pdfDoc, rect).add(header2);
		}
		else {
			Rectangle rect = new Rectangle(pdfDoc.getDefaultPageSize().getX() + doc.getLeftMargin(),
					pdfDoc.getDefaultPageSize().getTop() - doc.getTopMargin(), 523, headerRepetido.getImageHeight());
			headerRepetido.setFixedPosition(
					pageSize.getRight() - doc.getRightMargin() - headerRepetido.getImageScaledWidth(),
					pageSize.getTop() - doc.getTopMargin() + 30);
			new Canvas(canvas, pdfDoc, rect).add(headerRepetido);
		}
	}

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
		new Canvas(canvas, pdfDoc, rectFooter).add(footer1);
	}

}