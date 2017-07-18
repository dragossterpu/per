package es.mira.progesin.services;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.persistence.repositories.IModeloInformeRepository;
import es.mira.progesin.util.FacesUtilities;

/**
 * Implementación del servicio para nuevos modelos de informe.
 * 
 * @author EZENTIS
 *
 */
@Service
public class NuevoModeloInformeService implements INuevoModeloInformeService {
    
    /**
     * Repositorio de modelos de informe.
     * 
     */
    @Autowired
    private IModeloInformeRepository modeloInformeRepository;
    
    /**
     * Servicio de areas de informe.
     */
    @Autowired
    private IAreaInformeService areaInformeService;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    private IRegistroActividadService registroActividadService;
    
    /**
     * Guarda en base de datos un nuevo modelo de informe.
     * 
     * @param nuevoModelo Modelo de informe a guardar
     * @param listaAreas Lista de áreas del informe
     * @return Modelo guardado
     */
    @Override
    public ModeloInforme guardaModelo(ModeloInforme nuevoModelo, List<AreaInforme> listaAreas) {
        ModeloInforme resultado = null;
        try {
            resultado = modeloInformeRepository.save(nuevoModelo);
            int orden = 0;
            for (AreaInforme area : listaAreas) {
                area.setOrden(orden++);
                area.setModeloInformeId(nuevoModelo.getId());
                List<SubareaInforme> listado = area.getSubareas();
                areaInformeService.save(area, listado);
            }
            nuevoModelo.setAreas(listaAreas);
            resultado = modeloInformeRepository.save(nuevoModelo);
            registroActividadService.altaRegActividad(
                    "Se ha creado el modelo de informe ".concat(nuevoModelo.getNombre()), TipoRegistroEnum.ALTA.name(),
                    SeccionesEnum.INFORMES.getDescripcion());
        } catch (DataAccessException e) {
            registroActividadService.altaRegActividadError(SeccionesEnum.INFORMES.getDescripcion(), e);
        }
        
        return resultado;
    }
    
    /**
     * Añade un área al modelo.
     * 
     * @param area Área a añadir
     * @param listaAreas Lista de áreas del modelo
     */
    @Override
    public void aniadeArea(String area, List<AreaInforme> listaAreas) {
        if (area.isEmpty() == Boolean.FALSE) {
            AreaInforme areaAux = new AreaInforme();
            areaAux.setDescripcion(area);
            areaAux.setSubareas(new ArrayList<SubareaInforme>());
            listaAreas.add(areaAux);
        }
        
    }
    
    /**
     * Añade un subárea a un área.
     * @param area Área a la que se añadirá el subárea.
     * @param subArea Subárea a añadir
     */
    @Override
    public void aniadeSubarea(AreaInforme area, String subArea) {
        if (!subArea.isEmpty()) {
            SubareaInforme subAreaAux = new SubareaInforme();
            subAreaAux.setArea(area);
            subAreaAux.setDescripcion(subArea);
            List<SubareaInforme> listado = area.getSubareas();
            listado.add(subAreaAux);
            area.setSubareas(listado);
        } else {
            String textoError = "Debe escribir un texto para la pregunta y seleccionar un tipo de respuesta";
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, textoError, null, null);
        }
        
    }
    
    /**
     * Copia la lista de áreas de un modelo recibido como parámetro.
     * 
     * @param modelo Modelo del que se desea copiar la lista de áreas.
     * @return Lista de áreas copiada.
     */
    @Override
    public List<AreaInforme> clonarListaAreas(ModeloInforme modelo) {
        
        List<AreaInforme> listaAreas = areaInformeService.findByModeloInformeId(modelo.getId());
        List<AreaInforme> respuesta = new ArrayList<>();
        for (AreaInforme area : listaAreas) {
            AreaInforme areaAux = new AreaInforme();
            areaAux.setDescripcion(area.getDescripcion());
            List<SubareaInforme> listaSubareas = area.getSubareas();
            List<SubareaInforme> listaSubareasAux = new ArrayList<>();
            for (SubareaInforme subarea : listaSubareas) {
                subarea.setId(null);
                listaSubareasAux.add(subarea);
            }
            areaAux.setSubareas(listaSubareasAux);
            respuesta.add(areaAux);
        }
        return respuesta;
    }
    
}
