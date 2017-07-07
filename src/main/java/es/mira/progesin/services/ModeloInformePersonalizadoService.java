package es.mira.progesin.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.persistence.repositories.IModeloInformePersonalizadoRepository;

/**
 * Servicio del modelo de informes personalizados.
 * 
 * @author EZENTIS
 *
 */
@Service("modeloInformePersonalizadoService")
public class ModeloInformePersonalizadoService implements IModeloInformePersonalizadoService{
    
    /**
     * Repositorio de modelos de informes personalizados.
     */
    @Autowired
    private IModeloInformePersonalizadoRepository informePersonalizadoRepositoy;
    
    /**
     * Servicio del registro de actividad.
     */
    @Autowired
    private IRegistroActividadService registroActivadService;

    /**
     * Busca el informe personalizado cargando las subareas en el objeto devuelto.
     * 
     * @param idInformePersonalizado id del informe personalizado
     * @return modelo de informe personalizado
     */
    @Override
    public ModeloInformePersonalizado findModeloPersonalizadoCompleto(Long idInformePersonalizado) {
        return informePersonalizadoRepositoy.findById(idInformePersonalizado);
    }
    

    /**
     * Guarda el modelo de informe personalizado en BBDD.
     * 
     * @param nombre nombre del informe personalizado
     * @param modelo modelo a partir del que se ha creado
     * @param subareasSeleccionadas subareas que formarán parte del informe personalizado
     * @return mode modelo de informe personalizado
     */
    @Override
    public ModeloInformePersonalizado guardarInformePersonalizado(String nombre, ModeloInforme modelo, Map<AreaInforme, SubareaInforme[]> subareasSeleccionadas) {
        ModeloInformePersonalizado informePersonalizado = new ModeloInformePersonalizado();
        informePersonalizado.setNombre(nombre);
        informePersonalizado.setModeloInforme(modelo);
        List<SubareaInforme> subareasInformePersonalizado = new ArrayList<>();
        for (AreaInforme area : modelo.getAreas()) {
            Object[] subareas = subareasSeleccionadas.get(area);
            for (int i = 0; i < subareas.length; i++) {
                subareasInformePersonalizado.add((SubareaInforme)subareas[i]);
            }
        }
        informePersonalizado.setSubareas(subareasInformePersonalizado);
        ModeloInformePersonalizado informeGuardado = null;
        try {
            informeGuardado = informePersonalizadoRepositoy.save(informePersonalizado);
        } catch(DataAccessException e) {
            registroActivadService.altaRegActividadError(SeccionesEnum.INFORMES.getDescripcion(), e);
        }
        return informeGuardado;
    }
    
}
