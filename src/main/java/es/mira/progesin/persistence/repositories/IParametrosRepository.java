package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.Parametro;
import es.mira.progesin.persistence.entities.ParametroId;

public interface IParametrosRepository extends CrudRepository<Parametro, ParametroId> {
    
    /**
     * 
     * findValueForKey
     * 
     * Devuelve el valor de una clave localizada en la tabla de Parámetros de BDD.
     * 
     * @author Ezentis
     * 
     * @param clave
     * @param seccion
     * @return valor
     *
     */
    
    @Query("select param.valor from Parametro c where c.param.clave = :clave and c.param.seccion= :seccion)")
    String findValueForKey(@Param("clave") String clave, @Param("seccion") String seccion);
    
    /**
     * 
     * findValuesForSeccion
     * 
     * Devuelve los valores de una seccion localizada en la tabla de Parámetros de BDD.
     * 
     * @author Ezentis
     * 
     * @param seccion
     * @return valor
     *
     */
    
    @Query("select param.valor from Parametro c where c.param.seccion= :seccion)")
    List<String> findValuesForSeccion(@Param("seccion") String seccion);
    
    /**
     * 
     * findParamByParamSeccion
     * 
     * Devuelve una lista de objetos Parametro de una seccion localizada en la tabla de Parámetros de BDD.
     * 
     * @author Ezentis
     * 
     * @param seccion
     * @return Lista parámetros
     *
     */
    
    List<Parametro> findParamByParamSeccion(String seccion);
    
    /**
     * Devuelve lista de secciones a las que pertenecen todos los parámetros
     * @return secciones
     */
    @Query("select distinct param.seccion from Parametro")
    List<String> findSecciones();
}
