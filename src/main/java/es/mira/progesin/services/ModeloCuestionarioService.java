package es.mira.progesin.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.repositories.IModeloCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;

/**
 * Implementación del servicio que gestiona los modelos de cuestionario.
 * 
 * @author EZENTIS
 */
@Service
public class ModeloCuestionarioService implements IModeloCuestionarioService {
    
    /**
     * Variable utilizada para inyectar el repositorio de modelos de cuestionario.
     * 
     */
    @Autowired
    private IModeloCuestionarioRepository modeloCuestionarioRepository;
    
    /**
     * Repositorio de preguntas de cuestionario.
     */
    @Autowired
    private IPreguntaCuestionarioRepository preguntasRepository;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    private IRegistroActividadService registroActividadService;
    
    /**
     * Servicio de cuestionarios personalizados.
     */
    @Autowired
    private ICuestionarioPersonalizadoService cuestionarioPersonalizadoService;
    
    /**
     * Servicio sde áreas de cuestionario.
     */
    @Autowired
    private IAreaCuestionarioService areaCuestionarioService;
    
    /**
     * Guarda o actualiza un modelo de cuestionario.
     * 
     * @param modeloCuestionario a guardar o actualizar
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public ModeloCuestionario save(ModeloCuestionario modeloCuestionario) {
        return modeloCuestionarioRepository.save(modeloCuestionario);
    }
    
    /**
     * Busca todos los modelos de cuestionario que hay en BBDD.
     * 
     * @return List<ModeloCuestionario> lista de modelos en BBDD
     */
    @Override
    public List<ModeloCuestionario> findAllByFechaBajaIsNull() {
        return modeloCuestionarioRepository.findAllByFechaBajaIsNull();
    }
    
    /**
     * Devuelve un modelo de cuestionario identificado por su id.
     * 
     * @param id Identificador del modelo
     * @return Modelo recuperado
     */
    @Override
    public ModeloCuestionario findOne(Integer id) {
        return modeloCuestionarioRepository.findOne(id);
    }
    
    /**
     * Elimina un modelo pasado como parámetro.
     * 
     * @param modelo Modelo a eliminar
     * @return Modelo eliminado
     */
    @Override
    public ModeloCuestionario eliminarModelo(ModeloCuestionario modelo) {
        ModeloCuestionario modeloActualizado = null;
        
        try {
            if (cuestionarioPersonalizadoService.existsByModelo(modelo)) {
                String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
                modelo.setFechaBaja(new Date());
                modelo.setUsernameBaja(usuarioActual);
                modeloActualizado = modeloCuestionarioRepository.save(modelo);
                
            } else {
                
                modeloCuestionarioRepository.delete(modelo);
                
                modeloActualizado = modelo;
            }
            String descripcion = "Se ha eliminado el modelo de cuestionario: " + modelo.getDescripcion();
            // Guardamos la actividad en bbdd
            registroActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                    SeccionesEnum.INFORMES.getDescripcion());
        } catch (DataAccessException e) {
            registroActividadService.altaRegActividadError(SeccionesEnum.INFORMES.getDescripcion(), e);
        }
        
        return modeloActualizado;
    }
    
    /**
     * Visualiza un modelo de cuestionario.
     * 
     * @param modeloVisualizar Modelo a visualizar.
     * @return Modelo visualizado
     */
    @Override
    public ModeloCuestionario visualizarModelo(ModeloCuestionario modeloVisualizar) {
        ModeloCuestionario visualiza = modeloCuestionarioRepository.findOne(modeloVisualizar.getId());
        List<AreasCuestionario> listaAreas = areaCuestionarioService
                .findDistinctByIdCuestionarioAndFechaBajaIsNullOrderByOrdenAsc(modeloVisualizar.getId());
        for (AreasCuestionario area : listaAreas) {
            List<PreguntasCuestionario> listaPreguntas = preguntasRepository
                    .findByAreaAndFechaBajaIsNullOrderByOrdenAsc(area);
            area.setPreguntas(listaPreguntas);
        }
        visualiza.setAreas(listaAreas);
        return visualiza;
    }
    
}
