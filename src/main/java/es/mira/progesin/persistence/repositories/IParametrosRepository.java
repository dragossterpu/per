package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.Parametro;
import es.mira.progesin.persistence.entities.ParametroId;

/**
 * Repositorio de operaciones de base de datos para la entidad Parametro.
 * 
 * @author EZENTIS
 *
 */

public interface IParametrosRepository extends CrudRepository<Parametro, ParametroId> {
    
    /**
     * Devuelve el valor de una clave localizada en la tabla de Parámetros de BDD.
     * 
     * @param clave Clave que se desea consultar
     * @param seccion Seccion para la que se desea consultar la clave
     * @return valor Valor de la clave
     *
     */
    
    @Query("select param.valor from Parametro c where c.param.clave = :clave and c.param.seccion= :seccion)")
    String findValueForKey(@Param("clave") String clave, @Param("seccion") String seccion);
    
    /**
     * 
     * Devuelve los valores de una seccion localizada en la tabla de Parámetros de BDD.
     * 
     * 
     * @param seccion Sección de la que se desea recuperar las claves
     * @return Lista de las claves de la sección
     *
     */
    
    @Query("select param.valor from Parametro c where c.param.seccion= :seccion)")
    List<String> findValuesForSeccion(@Param("seccion") String seccion);
    
    /**
     * Devuelve una lista de objetos Parametro de una seccion localizada en la tabla de Parámetros de BDD.
     * 
     * @param seccion Sección de la que se desean recuperar los parámetros
     * @return Lista de parámetros de la sección consultada
     *
     */
    
    List<Parametro> findParamByParamSeccion(String seccion);
    
    /**
     * Devuelve lista de secciones.
     * 
     * @return Listado de secciones
     */
    @Query("select distinct param.seccion from Parametro")
    List<String> findSecciones();
}
