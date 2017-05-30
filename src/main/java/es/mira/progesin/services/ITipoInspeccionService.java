package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.TipoInspeccion;

/**
 * Interfaz del servicio para la gestión de tipos de inspecciones.
 * 
 * @author EZENTIS
 *
 */
public interface ITipoInspeccionService {
    
    /**
     * Borra físicamente un tipo de la base de datos.
     * 
     * @param tipo a borrar
     * @throws ProgesinException error de borrado
     */
    void borrarTipo(TipoInspeccion tipo) throws ProgesinException;
    
    /**
     * @param entity tipo a guaradar
     * @return boolean (alta correcta)
     * @throws ProgesinException error de guardado
     */
    TipoInspeccion guardarTipo(TipoInspeccion entity) throws ProgesinException;
    
    /**
     * Comprueba si existe un tipo de inspección comparando por su código.
     * 
     * @param codigo del tipo
     * @return resultado de la consulta.
     */
    boolean existeByCodigoIgnoreCase(String codigo);
    
    /**
     * Busca todos los tipos de inspección.
     * @return lista tipos isnpecciones
     */
    List<TipoInspeccion> buscaTodos();
    
}
