package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.repositories.IAreaCuestionarioRepository;

/**
 * Implementación del servicio de Áreas de cuestionario
 * 
 * @author Ezentis
 *
 */
@Service
public class AreaCuestionarioService implements IAreaCuestionarioService {
    
    @Autowired
    private IAreaCuestionarioRepository areaRepository;
    
    /**
     * 
     * Elimina un área cuyo id se recibe como parámetro
     * 
     * @param id Identificador del área a eliminar
     * 
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        areaRepository.delete(id);
    }
    
    /***
     * 
     * Elimina varias áreas pasadas como parámetro
     * 
     * @param entities Áreas a eliminar
     * 
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Iterable<AreasCuestionario> entities) {
        areaRepository.delete(entities);
    }
    
    /***
     * Elimina un área pasada como parámetro
     * 
     * @param entity Area a eliminar
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(AreasCuestionario entity) {
        areaRepository.delete(entity);
    }
    
    /***
     * Comprueba la existencia en la base de datos de un área cuyo id se recibe como parámetro
     * 
     * @param id Identificador del área a buscar en la base de datos
     * @return boolean
     * 
     */
    
    @Override
    public boolean exists(Long id) {
        return areaRepository.exists(id);
    }
    
    /***
     * Devuelve un objeto iterable conteniendo todas las áreas de la base de datos
     * 
     * @return Iterable<AreasCuestionario>
     *
     */
    
    @Override
    public Iterable<AreasCuestionario> findAll() {
        return areaRepository.findAll();
    }
    
    /***
     * Devuelve un objeto iterable conteniendo todas las áreas de la base de datos
     * 
     * @param ids Identificadores de las áreas a recuperar de la base de datos
     * @return Iterable<AreasCuestionario>
     */
    
    @Override
    public Iterable<AreasCuestionario> findAll(Iterable<Long> ids) {
        return areaRepository.findAll(ids);
    }
    
    /**
     * Recupera un área de la base de datos identificada por su id
     * 
     * @param id Identificador del área a buscar
     * @return AreasCuestionario
     */
    @Override
    public AreasCuestionario findOne(Long id) {
        return areaRepository.findOne(id);
    }
    
    /**
     * Guarda en base de datos una serie de entidades pasadas como parámetro
     * 
     * @param entities Áreas a guardar
     * @return Iterable<AreasCuestionario>
     */
    @Override
    @Transactional(readOnly = false)
    public Iterable<AreasCuestionario> save(Iterable<AreasCuestionario> entities) {
        return areaRepository.save(entities);
    }
    
    /**
     * Guarda en base de datos un área pasada como parámetro
     * 
     * @param entity área a guardar
     * @return AreasCuestionario
     */
    @Override
    @Transactional(readOnly = false)
    public AreasCuestionario save(AreasCuestionario entity) {
        return areaRepository.save(entity);
    }
    
    /**
     * Devuelve una lista de las áreas contenidas en un cuestionario
     * 
     * @param idCuestionario identificador del cuestionario
     * @return List<AreasCuestionario> preguntas del cuestionario
     */
    @Override
    public List<AreasCuestionario> findAreasByIdCuestionarioByOrder(Integer idCuestionario) {
        return areaRepository.findDistinctByIdCuestionarioOrderByOrdenAsc(idCuestionario);
    }
    
}
