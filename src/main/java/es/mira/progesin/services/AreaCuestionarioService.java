package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.repositories.IAreaCuestionarioRepository;

/**
 * Implementación del servicio de Áreas de cuestionario.
 * 
 * @author EZENTIS
 *
 */
@Service
public class AreaCuestionarioService implements IAreaCuestionarioService {
    /**
     * Repositorio de áreas de cuestionario.
     */
    @Autowired
    private IAreaCuestionarioRepository areaRepository;
    
    /**
     * Busca las áreas de un cuestionario ordenadas de manera ascendente por el campo orden.
     * 
     * @param idCuestionario Id del cuestionario del que se desea obtener las áreas
     * @return Lista de áreas
     */
    @Override
    public List<AreasCuestionario> findDistinctByIdCuestionarioOrderByOrdenAsc(Integer idCuestionario) {
        return areaRepository.findDistinctByIdCuestionarioOrderByOrdenAsc(idCuestionario);
    }
    
    /**
     * Busca un listado de áreas a partir de una lista de id recibida como parámetro.
     * 
     * @param listaIdAreasElegidas lista de id de áreas a buscar
     * @return Listado de áreas localizadas
     */
    @Override
    public List<AreasCuestionario> findByIdIn(List<Long> listaIdAreasElegidas) {
        return areaRepository.findByIdIn(listaIdAreasElegidas);
    }
    
    /**
     * Recupera un listado de áreas a partir de una lista de id recibida como parámetro.
     * @param idArea del area
     * 
     * @return Listado de áreas localizadas
     */
    @Override
    public AreasCuestionario findAreaExistenteEnCuestionariosPersonalizados(Long idArea) {
        return areaRepository.findAreaExistenteEnCuestionariosPersonalizados(idArea);
    }
    
    /**
     * Recupera un listado de todas las áreas que no se han dado de baja, ordenadas ascendentemente, de un cuestionario
     * cuyo id se recibe como parámetro.
     * 
     * @param idCuestionario identificador del cuestionario del que se obtendrán las áreas
     * @return Listado de áreas pertenecientes al cuestionario recibido como parámetro
     */
    @Override
    public List<AreasCuestionario> findDistinctByIdCuestionarioAndFechaBajaIsNullOrderByOrdenAsc(
            Integer idCuestionario) {
        return areaRepository.findDistinctByIdCuestionarioAndFechaBajaIsNullOrderByOrdenAsc(idCuestionario);
    }
    
}
