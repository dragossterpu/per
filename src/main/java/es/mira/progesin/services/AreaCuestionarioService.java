package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.repositories.IAreaCuestionarioRepository;

/**
 * Implementación del servicio de Áreas de cuestionario
 * 
 * @author EZENTIS
 *
 */
@Service
public class AreaCuestionarioService implements IAreaCuestionarioService {
    
    @Autowired
    private IAreaCuestionarioRepository areaRepository;
    
    @Override
    public List<AreasCuestionario> findAreasByIdCuestionarioByOrder(Integer idCuestionario) {
        return areaRepository.findDistinctByIdCuestionarioOrderByOrdenAsc(idCuestionario);
    }
    
    @Override
    public List<AreasCuestionario> findByIdIn(List<Long> listaIdAreasElegidas) {
        return areaRepository.findByIdIn(listaIdAreasElegidas);
    }
    
}
