package es.mira.progesin.exceptions;

import java.util.Arrays;

/**
 * 
 * Se crea una nueva excepción personalizada para los errores de la clase CorreoElectronico.
 * 
 * @author EZENTIS
 *
 */

public class CorreoException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Objeto de clase Throwable.
     */
    private final Throwable e;
    
    /**
     * Mensaje a mostrar cuando se produce una excepción.
     */
    private static final String MENSAJE = "Se ha producido un error en el envío de correo electrónico ";
    
    /**
     * Se genera una nueva excepción de tipo CorreoException y se almacena el error detectado por el sistema y que es
     * recibido como parámetro.
     * 
     * @param ex Objeto que contiene los datos de la excepción
     */
    public CorreoException(Throwable ex) {
        this.e = ex;
    }
    
    /**
     * Muestra un error tipo.
     * 
     * @return Mensaje que se muestra
     */
    public String excError() {
        return MENSAJE;
    }
    
    /**
     * Muestra el error tipo y le anexa el StackTrace de la excepción pos si de desea trazar el error mas
     * concienzudamente.
     * 
     * @return Mensaje completo con la pila de mensajes de la excepción.
     */
    public String excErrorCompleto() {
        return MENSAJE.concat(Arrays.toString(e.getStackTrace()));
    }
}
