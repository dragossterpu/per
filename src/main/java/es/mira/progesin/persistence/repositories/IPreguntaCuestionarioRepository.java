package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;

/**
 * Repositorio de preguntas de cuestionario.
 * 
 * @author EZENTIS
 *
 */
public interface IPreguntaCuestionarioRepository extends CrudRepository<PreguntasCuestionario, Long> {
    
    /**
     * Busca las preguntas activas de un área ordenadas de forma ascendente por el campo orden.
     * 
     * @param area área de la que deseamos obtener sus preguntas
     * @return lista de preguntas
     */
    List<PreguntasCuestionario> findByAreaAndFechaBajaIsNullOrderByOrdenAsc(AreasCuestionario area);
    
    /**
     * Busca las preguntas de un cuestionario personalizado.
     * 
     * @param idCuestionarioPersonalizado id del cuestionario personalizado
     * @return lista de preguntas
     */
    @Query("select c.preguntasElegidas from CuestionarioPersonalizado c where c.id = :idCuestionarioPersonalizado")
    List<PreguntasCuestionario> findPreguntasElegidasCuestionarioPersonalizado(
            @Param("idCuestionarioPersonalizado") Long idCuestionarioPersonalizado);
    
    /**
     * Busca si una pregunta está siendo usado en algún cuestionario personalizado.
     * 
     * @param idPregunta id de la pregunta
     * @return pregunta
     */
    @Query(value = "select distinct p.* from preguntascuestionario p, cuest_per_preguntas cpp, cuestionario_personalizado cp "
            + "where p.id = cpp.id_preg_elegida and cpp.id_cuest_pers = cp.id and p.id = ?1", nativeQuery = true)
    PreguntasCuestionario findPreguntaExistenteEnCuestionariosPersonalizados(Long idPregunta);
    
    /**
     * Busca las preguntas asociadas de un serie de áreas de un cuestionario personalizado.
     * 
     * @param idCuestionarioPersonalizado id del cuestionario personalizado
     * @param listaIdAreasCuestionario listado de id de áreas
     * @return listado de preguntas
     */
    @Query(value = "select p.* from CUESTIONARIO_PERSONALIZADO c, CUEST_PER_PREGUNTAS cp, PREGUNTASCUESTIONARIO p "
            + "where c.id = cp.ID_CUEST_PERS and cp.ID_PREG_ELEGIDA = p.id and p.id_area in (?2) "
            + "and c.id = ?1", nativeQuery = true)
    List<PreguntasCuestionario> findPreguntasElegidasCuestionarioPersonalizadoAndAreaIn(
            Long idCuestionarioPersonalizado, List<Long> listaIdAreasCuestionario);
}