package es.mira.progesin.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.sax.BodyContentHandler;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.services.IRegistroActividadService;

/**
 * Utilidad para verificar la extensión de los documentos.
 * 
 * @author EZENTIS
 *
 */

public class VerificadorExtensiones {
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    private IRegistroActividadService registroActividadService;
    
    /**
     * Recibe un objeto de tipo UploadedFile y comprueba que el content-type dado por JSF (basándose en su extensión) se
     * corresponde con el content-type real obtenido a través de Tika (basándose en el contenido de la cabecera).
     * 
     * 
     * @param file fichero para el cual se quiere comprobar la validez de la extensión
     * @return boolean
     *
     */
    
    public boolean extensionCorrecta(UploadedFile file) {
        
        String extension = file.getFileName().substring(file.getFileName().lastIndexOf('.') + 1);
        
        List<String> extensionesNoVerificadas = Arrays.asList("mid", "7z", "zip", "csv", "wav", "htm", "html", "txt",
                "wmv", "avi", "png", "bmp", "jpeg", "mp3", "msg", "jpg");
        
        boolean respuesta = extensionesNoVerificadas.contains(extension);
        
        if (!respuesta) {
            String tipo;
            ContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();
            ParseContext pcontext = new ParseContext();
            Parser parser;
            
            if (file.getContentType().contains("openxmlformats")) {
                parser = new OOXMLParser();
            } else {
                parser = new AutoDetectParser();
            }
            
            try {
                parser.parse(file.getInputstream(), handler, metadata, pcontext);
                tipo = metadata.get("Content-Type");
            } catch (IOException | SAXException | TikaException e) {
                registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), e);
                tipo = "error";
            }
            
            respuesta = tipo.equalsIgnoreCase(file.getContentType());
        }
        return respuesta;
    }
    
}
