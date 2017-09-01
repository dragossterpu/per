package es.mira.progesin.util;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 
 * Clase para el envío de correos electrónicos.
 * 
 * @author Ezentis
 * 
 */
public interface ICorreoElectronico {
    
    /**
     * 
     * Envío de correos electrónico. Destinatarios, destinatarios en copia, asunto, cuerpo del mensaje y los documentos
     * adjuntos se reciben como parámetros.
     * 
     * @param paramDestino Destinatarios separados por ','
     * @param paramCC Destinatario en copia
     * @param paramAsunto Asunto del correo
     * @param paramCuerpo Cuerpo del correo
     * @param paramAdjunto Lista de ficheros adjuntos
     * 
     */
    public void envioCorreo(String paramDestino, String paramCC, String paramAsunto, String paramCuerpo,
            List<File> paramAdjunto);
    
    /**
     * 
     * Envío de correos electrónico. Destinatarios, asunto, cuerpo del mensaje y los documentos adjuntos se reciben como
     * parámetros
     * 
     * @param paramDestino Destinatarios separados por ','
     * @param paramAsunto Asunto del correo
     * @param paramCuerpo Cuerpo del correo
     * @param paramAdjunto Lista de ficheros adjuntos
     * 
     */
    void envioCorreo(String paramDestino, String paramAsunto, String paramCuerpo, List<File> paramAdjunto);
    
    /**
     * 
     * Envío de correos electrónico sin adjuntos. Destinatarios, asunto y cuerpo del mensaje se reciben como parámetros
     * 
     * @param paramDestino Destinatarios separados por ','
     * @param paramAsunto del correo
     * @param paramCuerpo Cuerpo del correo
     * 
     */
    void envioCorreo(String paramDestino, String paramAsunto, String paramCuerpo);
    
    /**
     * 
     * Envío de correos electrónico sin adjuntos con plantilla personalizada. Destinatarios, asunto, datos del cuerpo
     * del mensaje y ruta de la plantilla se reciben como parámetros
     * 
     * @param paramDestino Destinatarios separados por ','
     * @param paramAsunto del correo
     * @param plantilla ruta del archivo de la plantilla pebble
     * @param paramPlantilla parametros del cuerpo del correo que se usan en la plantilla
     * 
     */
    void envioCorreo(String paramDestino, String paramAsunto, String plantilla, Map<String, String> paramPlantilla);
}
