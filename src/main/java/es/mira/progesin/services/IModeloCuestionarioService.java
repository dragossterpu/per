package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;

public interface IModeloCuestionarioService {
    
    public ModeloCuestionario save(ModeloCuestionario modeloCuestionario);
    
    public Iterable<ModeloCuestionario> findAll();
    
    public ModeloCuestionario findOne(Integer id);
    
    public void saveModeloCuestionarioModificado(ModeloCuestionario modeloCuestionario,
            List<AreasCuestionario> listaAreasCuestionario, List<AreasCuestionario> listaAreasEliminacionFisica);
}
