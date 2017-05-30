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
     * Excepción general.
     * 
     * @param e Excepción
     */
    public ProgesinException(Exception e) {
        super(e);
    }
    
    /**
     * Excepción general sin parámetros.
     */
    
    public ProgesinException() {
        super();
    }
    
}
