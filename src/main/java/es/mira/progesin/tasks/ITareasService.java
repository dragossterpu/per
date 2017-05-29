package es.mira.progesin.tasks;

/**
 * 
 * Interfaz para el servicio de Tareas.
 * 
 * @author EZENTIS
 * @see TareasService
 * 
 */

public interface ITareasService {
    
    /**
     * Recordatorio de la necesidad de enviar un cuestionario.
     */
    void recordatorioEnvioCuestionario();
    
    /**
     * Recordatorio de la necesidad de enviar la documentación solicitada.
     */
    void recordatorioEnvioDocumentacion();
    
    /**
     * Limpia la papelera de documentos.
     */
    void limpiarPapelera();
}