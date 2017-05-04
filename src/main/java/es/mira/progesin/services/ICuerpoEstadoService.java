package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.CuerpoEstado;

/**
 * Declaración de los métodos que se usarán para la persistencia de los cuerpos de estado
 * @author Ezentis
 *
 */
public interface ICuerpoEstadoService {
    
    /**
     * @param cuerpo
     * @return CuerpoEstado actualizado
     */
    CuerpoEstado save(CuerpoEstado cuerpo);
    
    /**
     * Busca todos los cuerpos del estado dados de alta en la BBDD
     * @return Iterable<CuerpoEstado>
     */
    Iterable<CuerpoEstado> findAll();
    
    /**
     * Cuerpos del estado sin fecha de baja, es decir activos
     * @return List<CuerpoEstado>
     */
    List<CuerpoEstado> findByFechaBajaIsNull();
    
    /**
     * Existe un Cuerpo que no coincide con un id pero coincide con un nombre corto
     * @param nombreCorto
     * @param id
     * 
     * @return
     */
    boolean existeByNombreCortoIgnoreCaseAndIdNotIn(String nombreCorto, int id);
    
    /**
     * @param nombreCorto
     * @return
     */
    boolean existeByNombreCortoIgnoreCase(String nombreCorto);
    
}
