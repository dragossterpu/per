package es.mira.progesin.tasks;

/**
 * 
 * Interfaz para el servicio de Tareas
 * 
 * @author Ezentis
 * @see TareasService
 * 
 */

public interface ITareasService {
    
    void recordatorioEnvioCuestionario();
    
    void recordatorioEnvioDocumentacion();
    
    void limpiarPapelera();
}