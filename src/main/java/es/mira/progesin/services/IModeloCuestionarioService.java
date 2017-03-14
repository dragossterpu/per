package es.mira.progesin.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;

/**
 * Interfaz que contiene los métodos necesarios para trabajar con la clase ModeloCuestionario
 * 
 * @author EZENTIS
 *
 */
public interface IModeloCuestionarioService {
    
    @Transactional(readOnly = false)
    public ModeloCuestionario save(ModeloCuestionario modeloCuestionario);
    
    public Iterable<ModeloCuestionario> findAll();
    
    public ModeloCuestionario findOne(Integer id);
    
    /**
     * Reemplaza de la lista de áreas el área pasada como parámetro. Se usa para actualizar la lista de áreas de un
     * modelo
     * 
     * @param listaAreas
     * @param area
     */
    void reemplazarAreaModelo(List<AreasCuestionario> listaAreas, AreasCuestionario area);
    
}
