package es.mira.progesin.services;

import java.util.Map;

/**
 * Interfaz del servicio de parámetros.
 * 
 * @author EZENTIS
 *
 */

@FunctionalInterface
public interface IParametroService {
    
    /**
     * Recupera en un mapa los parámetros de la aplicación que están cargados en base de datos.
     * 
     * @return Mapa con los parámetros de configuración
     */
    Map<String, Map<String, String>> getMapaParametros();
    
}