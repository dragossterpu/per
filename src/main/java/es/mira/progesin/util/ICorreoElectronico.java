package es.mira.progesin.util;

import java.io.File;
import java.util.List;

/**
 * 
 * Clase para el envío de correos electrónicos
 * 
 * @author Ezentis
 * 
 */
public interface ICorreoElectronico {
    
    /**
     * 
     * Envío de correos electrónicos a una lista de destinatarios pasada como parámetros. El asunto, cuerpo del mensaje
     * y los documentos adjuntos se reciben como parámetros
     * 
     * @param paramDestino Lista de destinatarios
     * @param paramAsunto Asunto del correo
     * @param paramCuerpo Cuerpo del correo
     * @param paramAdjunto Lista de ficheros adjuntos
     * 
     */
    
    public void envioCorreo(List<String> paramDestino, String paramAsunto, String paramCuerpo, List<File> paramAdjunto);
    
    /**
     * 
     * Envío de correos electrónico. El destinatario, destinatario en copia, asunto, cuerpo del mensaje y los documentos
     * adjuntos se reciben como parámetros.
     * 
     * @param paramDestino Destinatario
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
     * Envío de correos electrónico. El destinatario, asunto, cuerpo del mensaje y los documentos adjuntos se reciben
     * como parámetros
     * 
     * @param paramDestino Destinatario
     * @param paramAsunto Asunto del correo
     * @param paramCuerpo Cuerpo del correo
     * @param paramAdjunto Lista de ficheros adjuntos
     * 
     */
    void envioCorreo(String paramDestino, String paramAsunto, String paramCuerpo, List<File> paramAdjunto);
    
    /**
     * 
     * Envío de correos electrónico sin adjuntos. El destinatario, asunto y cuerpo del mensaje se reciben como
     * parámetros
     * 
     * @param paramDestino Destinatario
     * @param paramAsunto del correo
     * @param paramCuerpo Cuerpo del correo
     * 
     */
    
    void envioCorreo(String paramDestino, String paramAsunto, String paramCuerpo);
    
    /**
     * 
     * Envío de correos electrónico sin adjuntos. La lista de destinatarios, asunto y cuerpo del mensaje se reciben como
     * parámetros
     * 
     * @param paramDestino Lista de destinatarios
     * @param paramAsunto Asunto del correo
     * @param paramCuerpo Cuerpo del correo
     * 
     */
    
    void envioCorreo(List<String> paramDestino, String paramAsunto, String paramCuerpo);
    
}
