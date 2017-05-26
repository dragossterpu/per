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
     * @param e
     */
    public ProgesinException(Exception e) {
        super(e);
    }
}
