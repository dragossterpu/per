package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.TipoInspeccion;

/**
 * Interfaz del servicio para la gestión de tipos de inspecciones
 * 
 * @author EZENTIS
 *
 */
public interface ITipoInspeccionService {
    
    /**
     * Borra físicamente un tipo de la base de datos
     * 
     * @param tipo a borrar
     */
    void borrarTipo(TipoInspeccion tipo);
    
    /**
     * @param entity
     * @return boolean (alta correcta)
     */
    TipoInspeccion guardarTipo(TipoInspeccion entity);
    
    /**
     * Recupera todos los tipos de inspección que no están dados de baja lógica
     * 
     * @return lista de tipos
     */
    List<TipoInspeccion> buscaByFechaBajaIsNull();
    
    /**
     * Comprueba si existe un tipo de inspección comparando por su código
     * 
     * @param codigo
     * @return
     */
    boolean existeByCodigoIgnoreCase(String codigo);
    
}
