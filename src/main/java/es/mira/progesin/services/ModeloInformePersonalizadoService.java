package es.mira.progesin.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.persistence.repositories.IModeloInformePersonalizadoRepository;
import es.mira.progesin.web.beans.informes.InformePersonalizadoBusqueda;

/**
 * Servicio del modelo de informes personalizados.
 * 
 * @author EZENTIS
 *
 */
@Service("modeloInformePersonalizadoService")
public class ModeloInformePersonalizadoService implements IModeloInformePersonalizadoService {
    
    /**
     * Variable utilizada para inyectar la sesión de spring.
     * 
     */
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * Servicio para usar los métodos usados junto con criteria.
     */
    @Autowired
    private ICriteriaService criteriaService;
    
    /**
     * Repositorio de modelos de informes personalizados.
     */
    @Autowired
    private IModeloInformePersonalizadoRepository informePersonalizadoRepositoy;
    
    /**
     * Servicio de informes.
     */
    @Autowired
    private IInformeService informeService;
    
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
    public ModeloInformePersonalizado guardarInformePersonalizado(String nombre, ModeloInforme modelo,
            Map<AreaInforme, SubareaInforme[]> subareasSeleccionadas) {
        ModeloInformePersonalizado informePersonalizado = new ModeloInformePersonalizado();
        informePersonalizado.setNombre(nombre);
        informePersonalizado.setModeloInforme(modelo);
        List<SubareaInforme> subareasInformePersonalizado = new ArrayList<>();
        for (AreaInforme area : modelo.getAreas()) {
            Object[] subareas = subareasSeleccionadas.get(area);
            for (int i = 0; i < subareas.length; i++) {
                subareasInformePersonalizado.add((SubareaInforme) subareas[i]);
            }
        }
        informePersonalizado.setSubareas(subareasInformePersonalizado);
        ModeloInformePersonalizado informeGuardado = null;
        try {
            informeGuardado = informePersonalizadoRepositoy.save(informePersonalizado);
        } catch (DataAccessException e) {
            registroActivadService.altaRegActividadError(SeccionesEnum.INFORMES.getDescripcion(), e);
        }
        return informeGuardado;
    }
    
    /**
     * Método que devuelve la lista de modelos de cuestionario personalizados en una consulta basada en criteria.
     * 
     * @param informePersonalizadoBusqueda objeto con los criterios de búsqueda
     * @param first primer elemento de la consulta
     * @param pageSize tamaño de cada página de resultados
     * @param sortField campo por el que se ordenan los resultados
     * @param sortOrder sentido de la ordenacion (ascendente/descendente)
     * @return la lista de modelos de informes personalizados.
     * 
     */
    @Override
    public List<ModeloInformePersonalizado> buscarInformePersonalizadoCriteria(int first, int pageSize,
            String sortField, SortOrder sortOrder, InformePersonalizadoBusqueda informePersonalizadoBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(ModeloInformePersonalizado.class);
        creaCriteria(informePersonalizadoBusqueda, criteria);
        
        criteriaService.prepararPaginacionOrdenCriteria(criteria, first, pageSize, sortField, sortOrder, "id");
        
        @SuppressWarnings("unchecked")
        List<ModeloInformePersonalizado> listaInformesPersonalizados = criteria.list();
        session.close();
        
        return listaInformesPersonalizados;
    }
    
    /**
     * Método que devuelve el número de modelos de cuestionario personalizados en una consulta basada en criteria.
     * 
     * @param informePersonalizadoBusqueda objeto con parámetros de búsqueda
     * @return devuelve el número de registros de la consulta criteria.
     * 
     */
    @Override
    public int getCountInformePersonalizadoCriteria(InformePersonalizadoBusqueda informePersonalizadoBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(ModeloInformePersonalizado.class);
        creaCriteria(informePersonalizadoBusqueda, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
    /**
     * Crea la consulta criteria.
     * 
     * @param informePersonalizadoBusqueda informe personalizado a guardar
     * @param criteria informe personalizado a guardar
     * 
     */
    private void creaCriteria(InformePersonalizadoBusqueda informePersonalizadoBusqueda, Criteria criteria) {
        
        if (informePersonalizadoBusqueda.getFechaDesde() != null) {
            criteria.add(Restrictions.ge(Constantes.FECHAALTA, informePersonalizadoBusqueda.getFechaDesde()));
        }
        if (informePersonalizadoBusqueda.getFechaHasta() != null) {
            Date fechaHasta = new Date(
                    informePersonalizadoBusqueda.getFechaHasta().getTime() + TimeUnit.DAYS.toMillis(1));
            criteria.add(Restrictions.le(Constantes.FECHAALTA, fechaHasta));
        }
        
        if (informePersonalizadoBusqueda.getUsernameAlta() != null) {
            criteria.add(Restrictions.ilike(Constantes.USERNAMEALTA, informePersonalizadoBusqueda.getUsernameAlta(),
                    MatchMode.ANYWHERE));
        }
        if (informePersonalizadoBusqueda.getModeloInformeSeleccionado() != null) {
            criteria.add(Restrictions.eq("modeloInforme", informePersonalizadoBusqueda.getModeloInformeSeleccionado()));
        }
        if (informePersonalizadoBusqueda.getNombreInforme() != null) {
            criteria.add(
                    Restrictions.ilike("nombre", informePersonalizadoBusqueda.getNombreInforme(), MatchMode.ANYWHERE));
        }
        
        criteria.add(Restrictions.isNull("fechaBaja"));
        
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    }
    
    /**
     * Elimina o anula un modelo personalizado de informe ya usado.
     * 
     * @param modeloPersonalizado modelo seleccionado
     * @return modelo sincronizado
     */
    @Override
    public ModeloInformePersonalizado eliminarModeloPersonalizado(ModeloInformePersonalizado modeloPersonalizado) {
        ModeloInformePersonalizado modeloPersonalizadoActualizado = null;
        try {
            if (informeService.existsByModeloPersonalizado(modeloPersonalizado)) {
                String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
                modeloPersonalizado.setFechaBaja(new Date());
                modeloPersonalizado.setUsernameBaja(usuarioActual);
                modeloPersonalizadoActualizado = informePersonalizadoRepositoy.save(modeloPersonalizado);
                String descripcion = "Se ha anulado el modelo de informe personalizado: "
                        + modeloPersonalizado.getNombre();
                // Guardamos la actividad en bbdd
                registroActivadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                        SeccionesEnum.INFORMES.getDescripcion());
            } else {
                informePersonalizadoRepositoy.delete(modeloPersonalizado.getId());
                // devolvemos el mismo objeto para diferenciarlo de null en caso de excepción
                modeloPersonalizadoActualizado = modeloPersonalizado;
            }
        } catch (DataAccessException e) {
            registroActivadService.altaRegActividadError(SeccionesEnum.INFORMES.getDescripcion(), e);
        }
        return modeloPersonalizadoActualizado;
    }
    
    /**
     * Determina si existen modelos personalizados del tipo pasado como referencia.
     * 
     * @param modelo Modelo del que se desea saber si existen personalizados.
     * @return Booleano con la respuesta.
     */
    @Override
    public boolean existsByModelo(ModeloInforme modelo) {
        return informePersonalizadoRepositoy.existsByModeloInforme(modelo);
    }
}
