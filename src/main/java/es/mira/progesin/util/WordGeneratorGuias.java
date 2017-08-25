package es.mira.progesin.util;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.services.IGuiaPersonalizadaService;
import es.mira.progesin.services.IGuiaService;

/**
 * Clase para la generación de documentos DOCX.
 * 
 * @author EZENTIS
 *
 */
@Component("wordGeneratorGuias")
public class WordGeneratorGuias extends WordAbstractGenerator {
    
    /**
     * Servicio de guías.
     */
    @Autowired
    private IGuiaService guiaService;
    
    /**
     * Servicio de guías personalizadas.
     */
    @Autowired
    private IGuiaPersonalizadaService guiaPersonalizadaService;
    
    /**
     * Genera un documento DOCX a partir de una guia.
     * 
     * @param guia Guía a partir de la cual se xdesea generar el documento word
     * @return StreamedContent Stream para descargar el fichero en la ventana del navegador
     * @throws ProgesinException excepción lanzada
     */
    
    public StreamedContent crearDocumentoGuia(Guia guia) throws ProgesinException {
        try (XWPFDocument doc = new XWPFDocument()) {
            // Tamaño página A4 595x842 en puntos (hay que multiplicarlo por 20)
            setTamanioYMargenDocumento(doc, 1200, 1200, 1440, 1440, 595*20, 842*20);
            crearCabecera(doc);
            crearTitulo(doc, guia.getNombre());
            List<GuiaPasos> listaPasos = guiaService.listaPasos(guia);
            creaCuerpoGuia(doc, listaPasos);
            return exportarFichero(doc, guia.getNombre());
        } catch (InvalidFormatException | IOException e) {
            throw new ProgesinException(e);
        }
    }
    
    /**
     * Genera un documento DOCX a partir de una guia personalizada mostrando el número de inspección que tiene asignado
     * en el caso de existir y los pasos elegidos.
     * 
     * @param guia Guía a partir de la cual se xdesea generar el documento word
     * @return StreamedContent Stream para descargar el fichero en la ventana del navegador
     * @throws ProgesinException excepción lanzada
     */
    
    public StreamedContent crearDocumentoGuia(GuiaPersonalizada guia) throws ProgesinException {
        try (XWPFDocument doc = new XWPFDocument()) {
            // Tamaño página A4 595x842 en puntos (hay que multiplicarlo por 20)
            setTamanioYMargenDocumento(doc, 1200, 1200, 1440, 1440, 595*20, 842*20);
            crearCabecera(doc);
            crearTitulo(doc, guia.getNombreGuiaPersonalizada());
            if (guia.getInspeccion() != null) {
                creaNumeroInspeccion(doc, guia.getInspeccion());
            }
            List<GuiaPasos> listaPasos = guiaPersonalizadaService.listaPasos(guia);
            creaCuerpoGuia(doc, listaPasos);
            return exportarFichero(doc, guia.getNombreGuiaPersonalizada());
        } catch (InvalidFormatException | IOException e) {
            throw new ProgesinException(e);
        }
    }
    
    /**
     * 
     * Crea un párrafo formateado que contiene el identificador de la inspección a la que está asignado el documento.
     * 
     * @param doc Documento al que se desea anexar los números de inspección
     * @param inspecciones Lista de inspecciones Inspección a la que está asignado
     * 
     */
    private void creaNumeroInspeccion(XWPFDocument doc, List<Inspeccion> inspecciones) {
        
        XWPFParagraph parrafo = doc.createParagraph();
        XWPFRun texto = parrafo.createRun();
        if (inspecciones.size() > 1) {
            texto.setText("Inspecciones asociadas ");
        } else {
            texto.setText("Inspección asociada ");
        }
        texto.setBold(true);
        texto.setFontSize(12);
        texto.setFontFamily(FONTFAMILY);
        parrafo.addRun(texto);
        for (Inspeccion ins : inspecciones) {
            texto.addCarriageReturn();
            texto.setText(ins.getNumero());
            parrafo.addRun(texto);
        }
        
        parrafo.setSpacingAfterLines(200);
        parrafo.setAlignment(ParagraphAlignment.LEFT);
        parrafo.addRun(texto);
        
    }
    
    /**
     * Genera el cuerpo del documento a partir de una lista de pasos que recibe como parámetro.
     * 
     * @param doc Documento al que se desea anexar el cuerpo con las preguntas
     * @param listaPasos Listado de los pasos que se deben pintar en el documento
     */
    
    private void creaCuerpoGuia(XWPFDocument doc, List<GuiaPasos> listaPasos) {
        XWPFParagraph parrafo = doc.createParagraph();
        XWPFRun texto = parrafo.createRun();
        parrafo.setSpacingBeforeLines(100);
        parrafo.setSpacingAfterLines(100);
        texto.setText("Pasos");
        texto.setBold(true);
        texto.setFontSize(16);
        texto.setFontFamily(FONTFAMILY);
        parrafo.addRun(texto);
        
        for (GuiaPasos paso : listaPasos) {
            parrafo = doc.createParagraph();
            // Texto pregunta
            texto = parrafo.createRun();
            texto.setFontFamily(FONTFAMILY);
            /* Comprobamos si hay saltos de línea */
            String textoPaso = paso.getPaso();
            if (textoPaso.contains("\n")) {
                String[] nuevasLineas = textoPaso.split("\n");
                
                // Por cada línea hay que generar un nuevo XWPFRun insertándole el texto de la linea y un retorno de
                // carro
                textoConSalto(parrafo, texto, nuevasLineas);
            } else {
                texto.setText(paso.getPaso());
            }
            /* Fin comprobaciones */
            parrafo.setSpacingAfterLines(200);
            parrafo.setSpacingBeforeLines(200);
            parrafo.setAlignment(ParagraphAlignment.BOTH);
            parrafo.addRun(texto);
        }
    }
    
}
