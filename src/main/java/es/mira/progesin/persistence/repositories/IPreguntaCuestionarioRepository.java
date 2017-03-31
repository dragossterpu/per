package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;

public interface IPreguntaCuestionarioRepository extends CrudRepository<PreguntasCuestionario, Long> {
    
    List<PreguntasCuestionario> findByAreaAndFechaBajaIsNullOrderByOrdenAsc(AreasCuestionario area);
    
    @Query("select c.preguntasElegidas from CuestionarioPersonalizado c where c.id = :idCuestionarioPersonalizado")
    List<PreguntasCuestionario> findPreguntasElegidasCuestionarioPersonalizado(
            @Param("idCuestionarioPersonalizado") Long idCuestionarioPersonalizado);
    
    @Query(value = "select p.* from CUESTIONARIO_PERSONALIZADO c, CUEST_PER_PREGUNTAS cp, PREGUNTASCUESTIONARIO p "
            + "where c.id = cp.ID_CUEST_PERS and cp.ID_PREG_ELEGIDA = p.id and (p.TIPO_RESPUESTA like 'TABLA%' or p.TIPO_RESPUESTA like 'MATRIZ%') "
            + "and c.id = :idCuestionarioPersonalizado", nativeQuery = true)
    List<PreguntasCuestionario> findPreguntasElegidasTablaMatrizCuestionarioPersonalizado(
            @Param("idCuestionarioPersonalizado") Long idCuestionarioPersonalizado);
    
    @Query(value = "select distinct p.* from preguntascuestionario p, cuest_per_preguntas cpp, cuestionario_personalizado cp "
            + "where p.id = cpp.id_preg_elegida and cpp.id_cuest_pers = cp.id and p.id = ?1", nativeQuery = true)
    PreguntasCuestionario findPreguntaExistenteEnCuestionariosPersonalizados(Long idPregunta);
    
    @Query(value = "select p.* from CUESTIONARIO_PERSONALIZADO c, CUEST_PER_PREGUNTAS cp, PREGUNTASCUESTIONARIO p "
            + "where c.id = cp.ID_CUEST_PERS and cp.ID_PREG_ELEGIDA = p.id and p.id_area in (?2) "
            + "and c.id = ?1", nativeQuery = true)
    List<PreguntasCuestionario> findPreguntasElegidasCuestionarioPersonalizadoAndAreaIn(
            Long idCuestionarioPersonalizado, List<Long> listaIdAreasCuestionario);
}