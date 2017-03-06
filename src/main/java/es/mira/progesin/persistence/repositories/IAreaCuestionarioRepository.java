package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;

public interface IAreaCuestionarioRepository extends CrudRepository<AreasCuestionario, Long> {
    
    // @EntityGraph(value = "AreasCuestionario.preguntas", type = EntityGraphType.LOAD)
    List<AreasCuestionario> findDistinctByIdCuestionarioAndFechaBajaIsNullOrderByOrdenAsc(Integer idCuestionario);
    
    @Query(value = "select distinct a.* from areascuestionario a, preguntascuestionario p, cuest_per_preguntas cpp, cuestionario_personalizado cp "
            + "where a.id = p.id_area and p.id = cpp.id_preg_elegida and cpp.id_cuest_pers = cp.id "
            + "and a.id = ?1", nativeQuery = true)
    AreasCuestionario findAreaExistenteEnCuestionariosPersonalizados(Long idArea);
    
}
