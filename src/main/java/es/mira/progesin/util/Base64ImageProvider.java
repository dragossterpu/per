package es.mira.progesin.util;

import java.io.IOException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase utilizada para sobreescribir el método 'retrieve' la clase AbstractImageProvider, usada por la librería itext
 * para incrustar imágenes en la generación un PDF, y poder pintar las imágenes que vienen codificadas en el html.
 * 
 * @author EZENTIS
 * 
 */
@Slf4j
public class Base64ImageProvider extends AbstractImageProvider {
    
    /**
     * Factor de escala para las imágenes de los documentos PDF.
     */
    private static final float ESCALA = 70f;
    
    /**
     * Devuelve la imagen que se encuentra codificada en base64 (usado en la generación en pdf de los informes, al
     * encontrarse las imagenes almacenadas en BBDD y codificadas).
     */
    @Override
    public Image retrieve(String src) {
        Image image = null;
        int pos = src.indexOf("base64,");
        try {
            if (src.startsWith("data") && pos > 0) {
                byte[] img = Base64.decode(src.substring(pos + 7));
                image = Image.getInstance(img);
            } else {
                image = Image.getInstance(src);
            }
            image.scalePercent(ESCALA);
        } catch (BadElementException | IOException ex) {
            log.error("Error al obtener la imagen", ex);
        }
        return image;
    }
    
    /**
     * 
     */
    @Override
    public String getImageRootPath() {
        return null;
    }
}
