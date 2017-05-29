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
    List<AreasCuestionario> findAreasByIdCuestionarioByOrder(Integer idCuestionario);
    
    /**
     * Busca un listado de áreas a partir de una lista de id recibida como parámetro.
     * 
     * @param listaIdAreasElegidas lista de id de áreas a buscar
     * @return Listado de áreas localizadas
     */
    List<AreasCuestionario> findByIdIn(List<Long> listaIdAreasElegidas);
    
}
