package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionarioId;

/**
 * Repositorio de configuración de los tipos de respuesta de un cuestionario.
 * 
 * @author EZENTIS
 *
 */
public interface IConfiguracionRespuestasCuestionarioRepository
        extends CrudRepository<ConfiguracionRespuestasCuestionario, ConfiguracionRespuestasCuestionarioId> {
    
    /**
     * Busca la lista de valores de una sección determinada.
     * 
     * @param seccion sección de la se desea obtener los valores
     * @return lista de valores
     */
    @Query("select c.config.valor from ConfiguracionRespuestasCuestionario c where c.config.seccion = :seccion)")
    List<String> findValoresPorSeccion(@Param("seccion") String seccion);
    
    /**
     * Busca la configuración disponible por sección y ordenado de manera ascendente por clave.
     * 
     * @param seccion sección por la que se busca
     * @return lista de configuraciones
     */
    List<ConfiguracionRespuestasCuestionario> findByConfigSeccionOrderByConfigClaveAsc(String seccion);
    
    /**
     * Busca los valores de una sección tipo TABLA/MATRIZ, pero sólo los valores de las columnas, excluyendo las filas
     * en el caso de las matrices.
     * 
     * @param seccion sección por la que se busca
     * @return lista de configuraciones
     */
    @Query("select c from ConfiguracionRespuestasCuestionario c where c.config.seccion = :seccion and c.config.clave not like 'nombreFila%') order by c.config.seccion asc")
    List<ConfiguracionRespuestasCuestionario> findColumnasBySeccion(@Param("seccion") String seccion);
    
    /**
     * Busca las filas para los tipos de respuesta MATRIZ de una sección dada. en el caso de las matrices.
     * 
     * @param seccion sección por la que se busca
     * @return lista de configuraciones
     */
    @Query("select c from ConfiguracionRespuestasCuestionario c where c.config.seccion = :seccion and c.config.clave like 'nombreFila%') order by c.config.seccion asc")
    List<ConfiguracionRespuestasCuestionario> findFilasBySeccion(@Param("seccion") String seccion);
    
    /**
     * Busca la configuración disponible para una lista de secciones.
     * 
     * @param seccion sección por la que se busca
     * @return lista de configuraciones
     */
    List<ConfiguracionRespuestasCuestionario> findByConfigSeccionIn(List<String> seccion);
    
    /**
     * Busca todas las secciones en la tabla de configuración.
     * 
     * @return lista de secciones
     */
    @Query("select distinct(c.config.seccion) from ConfiguracionRespuestasCuestionario c order by c.config.seccion")
    List<String> findAllDistinctTipoRespuestaOrderByTipoRespuestaAsc();
    
}
