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
    public List<AreasCuestionario> findAreasByIdCuestionarioByOrder(Integer idCuestionario) {
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
    
}
