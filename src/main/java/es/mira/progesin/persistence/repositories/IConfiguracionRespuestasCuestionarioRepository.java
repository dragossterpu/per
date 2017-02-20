package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionarioId;

public interface IConfiguracionRespuestasCuestionarioRepository
        extends CrudRepository<ConfiguracionRespuestasCuestionario, ConfiguracionRespuestasCuestionarioId> {
    
    @Query("select c.config.valor from ConfiguracionRespuestasCuestionario c where c.config.seccion = :seccion)")
    List<String> findValoresPorSeccion(@Param("seccion") String seccion);
    
    List<ConfiguracionRespuestasCuestionario> findByConfigSeccionOrderByConfigClaveAsc(String seccion);
    
    @Query("select c from ConfiguracionRespuestasCuestionario c where c.config.seccion = :seccion and c.config.clave not like 'nombreFila%')")
    List<ConfiguracionRespuestasCuestionario> findColumnasBySeccion(@Param("seccion") String seccion);
    
    @Query("select c from ConfiguracionRespuestasCuestionario c where c.config.seccion = :seccion and c.config.clave like 'nombreFila%')")
    List<ConfiguracionRespuestasCuestionario> findFilasBySeccion(@Param("seccion") String seccion);
    
    List<ConfiguracionRespuestasCuestionario> findByConfigSeccionIn(List<String> seccion);
    
    @Query("select distinct(c.config.seccion) from ConfiguracionRespuestasCuestionario c")
    List<String> findAllDistinctTipoRespuestaOrderByTipoRespuestaAsc();
    
}
