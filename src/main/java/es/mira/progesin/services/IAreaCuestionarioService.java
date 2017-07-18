package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;

/**
 * Interfaz del servicio de Áreas de Cuestionario.
 * 
 * @author EZENTIS
 *
 */
public interface IAreaCuestionarioService {
    
    /**
     * Busca las áreas de un cuestionario ordenadas de manera ascendente por el campo orden.
     * 
     * @param idCuestionario Id del cuestionario del que se desea obtener las áreas
     * @return Lista de áreas
     */
    List<AreasCuestionario> findDistinctByIdCuestionarioOrderByOrdenAsc(Integer idCuestionario);
    
    /**
     * Busca un listado de áreas a partir de una lista de id recibida como parámetro.
     * 
     * @param listaIdAreasElegidas lista de id de áreas a buscar
     * @return Listado de áreas localizadas
     */
    List<AreasCuestionario> findByIdIn(List<Long> listaIdAreasElegidas);
    
    /**
     * Recupera un listado de todas las áreas que no se han dado de baja, ordenadas ascendentemente, de un cuestionario
     * cuyo id se recibe como parámetro.
     * 
     * @param idCuestionario identificador del cuestionario del que se obtendrán las áreas
     * @return Listado de áreas pertenecientes al cuestionario recibido como parámetro
     */
    List<AreasCuestionario> findDistinctByIdCuestionarioAndFechaBajaIsNullOrderByOrdenAsc(Integer idCuestionario);
    
    /**
     * Busca un área identificada por su id recibido como parámetro en cuestionarios personalizados para verificar si ha
     * sido empleada en alguno.
     * 
     * @param idArea id del área a buscar en cuestionarios personalizados
     * @return Área encontrada en cuestionarios personalizados
     */
    AreasCuestionario findAreaExistenteEnCuestionariosPersonalizados(Long idArea);
    
}
