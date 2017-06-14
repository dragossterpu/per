package es.mira.progesin.exceptions;

/**
 * Excepción personalizada.
 * 
 * @author EZENTIS
 *
 */
public class ProgesinException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Mensaje de la excepción.
     */
    String mensaje;
    
    /**
     * Excepción general.
     * 
     * @param e Excepción
     */
    public ProgesinException(Exception e) {
        super(e);
        mensaje = e.getMessage();
    }
    
    /**
     * Excepción general sin parámetros.
     */
    
    public ProgesinException() {
        super();
    }
    
    /**
     * Sobreescritura del método getMessage() para mostrar el mensaje personalizado.
     */
    @Override
    public String getMessage() {
        return mensaje;
    }
    
}
