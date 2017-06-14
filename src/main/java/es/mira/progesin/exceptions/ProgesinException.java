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
    
    @Override
    public String getMessage() {
        return mensaje;
    }
    
}
