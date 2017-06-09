package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.enums.AdministracionAccionEnum;

/**
 * Servicio para la gestión de los cuerpos de estado.
 * 
 * @author EZENTIS
 *
 */
public interface ICuerpoEstadoService {
    
    /**
     * Guarda o actualiza un Cuerpo.
     * 
     * @param cuerpo a guardar
     * @param accion alta/baja/modificación
     * @return CuerpoEstado actualizado
     */
    CuerpoEstado save(CuerpoEstado cuerpo, AdministracionAccionEnum accion);
    
    /**
     * Busca todos los cuerpos del estado existentes en la BBDD.
     * 
     * @return lista con todos los cuerpos
     */
    List<CuerpoEstado> findAll();
    
    /**
     * Existe un Cuerpo que no coincide con un id pero coincide con un nombre corto empleado para la modificación en
     * caliente.
     * @param nombreCorto del cuerpo
     * @param id identificador del cuerpo
     * 
     * @return valor booleano
     */
    boolean existeByNombreCortoIgnoreCaseAndIdNotIn(String nombreCorto, int id);
    
    /**
     * Elimina un cuerpo de estado.
     * 
     * @param cuerpo a eliminar
     */
    void delete(CuerpoEstado cuerpo);
    
}
