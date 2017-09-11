package es.mira.progesin.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.persistence.repositories.IModeloInformeRepository;

/**
 * Implementación del servicio que gestiona los modelos de informe.
 * 
 * @author EZENTIS
 */
@Service
public class ModeloInformeService implements IModeloInformeService {
    
    /**
     * Variable utilizada para inyectar el repositorio de modelos de informe.
     * 
     */
    @Autowired
    private IModeloInformeRepository modeloInformeRepository;
    
    /**
     * Servicio de áreas de informe.
     */
    @Autowired
    private IAreaInformeService areainformeservice;
    
    /**
     * Servicio de subáreas de informe.
     */
    @Autowired
    private SubareaInformeService subareaInformeService;
    
    /**
     * Servicio de modelos de informes personalizados.
     */
    @Autowired
    private IModeloInformePersonalizadoService modeloInformePersonalizadoService;
    
    /**
     * Servicio de registr de actividad.
     */
    @Autowired
    private IRegistroActividadService registroActividadService;
    
    /**
     * Busca todos los modelos de informe que hay en BBDD.
     * 
     * @return List<ModeloInforme> lista de modelos en BBDD
     */
    @Override
    public List<ModeloInforme> findAll() {
        return (List<ModeloInforme>) modeloInformeRepository.findAll();
    }
    
    /**
     * Recupera un modelo de informe con sus areas y subareas a partir del id.
     * 
     * @param id id del modelo
     * @return modelo completo
     */
    @Override
    public ModeloInforme findDistinctById(Long id) {
        return modeloInformeRepository.findDistinctById(id);
    }
    
    /**
     * Elimina un modelo. La eliminación será lógica si existen modelos personalizados de este tipo o física si no es
     * así.
     * 
     * @param modelo Modelo a eliminar
     * @return modelo eliminado
     */
    @Override
    public ModeloInforme eliminarModelo(ModeloInforme modelo) {
        ModeloInforme modeloActualizado = null;
        try {
            if (modeloInformePersonalizadoService.existsByModelo(modelo)) {
                String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
                modelo.setFechaBaja(new Date());
                modelo.setUsernameBaja(usuarioActual);
                modeloActualizado = modeloInformeRepository.save(modelo);
                
            } else {
                subareaInformeService.deleteByArea(areainformeservice.findByModeloInformeId(modelo.getId()));
                areainformeservice.deleteByModeloInformeId(modelo.getId());
                modeloInformeRepository.delete(modelo);
                // devolvemos el mismo objeto para diferenciarlo de null en caso de excepción
                modeloActualizado = modelo;
            }
            String descripcion = "Se ha eliminado el modelo de informe personalizado: " + modelo.getNombre();
            // Guardamos la actividad en bbdd
            registroActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                    SeccionesEnum.INFORMES.getDescripcion());
        } catch (DataAccessException e) {
            registroActividadService.altaRegActividadError(SeccionesEnum.INFORMES.getDescripcion(), e);
        }
        return modeloActualizado;
    }
    
    /**
     * Recupera una lista con todos los modelos de informe de la bdd que no tengan fecha de baja.
     * 
     * @return Lista de todos los modelos
     */
    @Override
    public List<ModeloInforme> findAllByFechaBajaIsNull() {
        return modeloInformeRepository.findAllByFechaBajaIsNull();
    }
    
    /**
     * Visualiza un modelo de informe.
     * 
     * @param modeloVisualizar Modelo que se desea visualizar.
     * @return Modelo a visualizar.
     */
    @Override
    public ModeloInforme visualizarModelo(ModeloInforme modeloVisualizar) {
        ModeloInforme respuesta;
        
        respuesta = modeloInformeRepository.findOne(modeloVisualizar.getId());
        respuesta.setAreas(areainformeservice.findByModeloInformeId(respuesta.getId()));
        
        return respuesta;
    }
    
    /**
     * Carga el mapa de relaciones áreas-subáreas para la lista de áreas recibida como parámetro.
     * 
     * @param areasVisualizar Listado de áreas que se desean visualizar
     * @return Mapa de relaciones
     */
    @Override
    public Map<AreaInforme, List<SubareaInforme>> cargarMapaSubareas(List<AreaInforme> areasVisualizar) {
        Map<AreaInforme, List<SubareaInforme>> mapaAreasSubareas = new HashMap<AreaInforme, List<SubareaInforme>>();
        
        for (AreaInforme area : areasVisualizar) {
            mapaAreasSubareas.put(area, subareaInformeService.findByArea(area));
        }
        return mapaAreasSubareas;
    }
}
