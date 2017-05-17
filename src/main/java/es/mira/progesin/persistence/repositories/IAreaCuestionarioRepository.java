package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;

/**
 * Repositorio de operaciones con base de datos para la entidad AreasCuestionario
 * 
 * @author EZENTIS
 *
 */
public interface IAreaCuestionarioRepository extends CrudRepository<AreasCuestionario, Long> {
    
    /**
     * Recupera un listado de todas las áreas ordenadas ascendentemente de un cuestionario cuyo id se recibe como
     * parámetro
     * 
     * @param idCuestionario identificador del cuestionario del que se obtendrán las áreas
     * @return Listado de áreas pertenecientes al cuestionario recibido como parámetro
     */
    @EntityGraph(value = "AreasCuestionario.preguntas", type = EntityGraphType.LOAD)
    List<AreasCuestionario> findDistinctByIdCuestionarioOrderByOrdenAsc(Integer idCuestionario);
    
    /**
     * Recupera un listado de todas las áreas que no se han dado de baja, ordenadas ascendentemente, de un cuestionario
     * cuyo id se recibe como parámetro
     * 
     * @param idCuestionario identificador del cuestionario del que se obtendrán las áreas
     * @return Listado de áreas pertenecientes al cuestionario recibido como parámetro
     */
    List<AreasCuestionario> findDistinctByIdCuestionarioAndFechaBajaIsNullOrderByOrdenAsc(Integer idCuestionario);
    
    /**
     * Busca un área identificada por su id recibido como parámetro en cuestionarios personalizados para verificar si ha
     * sido empleada en alguno
     * 
     * @param idArea id del área a buscar en cuestionarios personalizados
     * @return Área encontrada en cuestionarios personalizados
     */
    @Query(value = "select distinct a.* from areascuestionario a, preguntascuestionario p, cuest_per_preguntas cpp, cuestionario_personalizado cp "
            + "where a.id = p.id_area and p.id = cpp.id_preg_elegida and cpp.id_cuest_pers = cp.id "
            + "and a.id = ?1", nativeQuery = true)
    AreasCuestionario findAreaExistenteEnCuestionariosPersonalizados(Long idArea);
    
    /**
     * Recupera un listado de áreas a partir de una lista de id recibida como parámetro
     * 
     * @param listaIdAreasElegidas lista de id de áreas a buscar
     * @return Listado de áreas localizadas
     */
    List<AreasCuestionario> findByIdIn(List<Long> listaIdAreasElegidas);
    
}
