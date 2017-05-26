package es.mira.progesin.exceptions;

import java.util.Arrays;

/**
 * 
 * Se crea una nueva excepción personalizada para los errores de la clase CorreoElectronico
 * 
 * @author Ezentis
 *
 */

public class CorreoException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private final Throwable e;
    
    private static final String MENSAJE = "Se ha producido un error en el envío de correo electrónico ";
    
    /**
     * Se genera una nueva excepción de tipo CorreoException y se almacena el error detectado por el sistema y que es
     * recibido como parámetro.
     * 
     * @param e
     */
    public CorreoException(Throwable e) {
        this.e = e;
    }
    
    /**
     * Muestra un error tipo
     * 
     * @return String
     */
    public String excError() {
        return MENSAJE;
    }
    
    /**
     * Muestra el error tipo y le anexa el StackTrace de la excepción pos si de desea trazar el error mas
     * concienzudamente
     * 
     * @return String
     */
    public String excErrorCompleto() {
        return MENSAJE.concat(Arrays.toString(e.getStackTrace()));
    }
}
