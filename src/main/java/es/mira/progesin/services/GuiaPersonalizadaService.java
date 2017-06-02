package es.mira.progesin.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.repositories.IGuiaPersonalizadaRepository;
import es.mira.progesin.persistence.repositories.IGuiasPasosRepository;
import es.mira.progesin.persistence.repositories.IInspeccionesRepository;
import es.mira.progesin.web.beans.GuiaBusqueda;

/**
 * Implementación de los métodos definidos en la interfaz IGuiaPersonalizadaService.
 * 
 * @author EZENTIS
 *
 */

@Service
public class GuiaPersonalizadaService implements IGuiaPersonalizadaService {
    /**
     * Formato de fecha.
     */
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * Factoría de sesion.
     */
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * Repositorio de guías personalizadas.
     */
    
    @Autowired
    private IGuiaPersonalizadaRepository guiaPersonalizadaRepository;
    
    /**
     * Repositorio de pasos de guía.
     */
    @Autowired
    private IGuiasPasosRepository pasosRepository;
    
    /**
     * Repositorio de inspecciones.
     */
    @Autowired
    private IInspeccionesRepository inspeccionRepository;
    
    /**
     * Elimina una guía personalizada de la base de datos.
     * 
     * @param guia que se desea eliminar
     * 
     */
    @Override
    public void eliminar(GuiaPersonalizada guia) {
        guiaPersonalizadaRepository.delete(guia);
        
    }
    
    /**
     * Almacena una guía personalizada en la base de datos.
     * 
     * @param guia guia a guardar
     * @return guia guardada
     * 
     */
    @Override
    public GuiaPersonalizada save(GuiaPersonalizada guia) {
        return guiaPersonalizadaRepository.save(guia);
    }
    
    /**
     * Busca en base de datos los resultados que se ajustan a los parámetros recibidos en el objeto de tipo
     * GuiaPersonalizadaBusqueda acotados por los parámetros first (primer resultado) y pageSize (máximo de resultados
     * de la búsqueda).
     * 
     * @param first primer elemento de los resultados
     * @param pageSize número máximo de resultados
     * @param sortField campo de ordenación
     * @param sortOrder sentido de la orientación
     * @param busqueda objeto que contiene los parámetros de búsqueda
     * @return lista de Guías personalizadas que corresponden a la búsqueda
     * 
     */
    @Override
    public List<GuiaPersonalizada> buscarGuiaPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            GuiaBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(GuiaPersonalizada.class, "guiaPersonalizada");
        
        consultaCriteriaGuiasPersonalizadas(busqueda, criteria);
        criteria.setFirstResult(first);
        criteria.setMaxResults(pageSize);
        
        if (sortField != null && sortOrder.equals(SortOrder.ASCENDING)) {
            criteria.addOrder(Order.asc(sortField));
        } else if (sortField != null && sortOrder.equals(SortOrder.DESCENDING)) {
            criteria.addOrder(Order.desc(sortField));
        } else if (sortField == null) {
            criteria.addOrder(Order.asc("id"));
        }
        
        @SuppressWarnings("unchecked")
        List<GuiaPersonalizada> listaGuias = criteria.list();
        session.close();
        
        return listaGuias;
    }
    
    /**
     * Devuelve el número de registros de la base de datos que cumplen con los criterio pasados como parámetro.
     * 
     * @param busqueda Objeto que contiene los parámetros de búsqueda
     * @return Número de registros que responden a los parámetros
     * 
     */
    @Override
    public int getCountGuiaCriteria(GuiaBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(GuiaPersonalizada.class, "guiaPersonalizada");
        
        consultaCriteriaGuiasPersonalizadas(busqueda, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
    /**
     * Añade los parámetros de búsqueda al criteria.
     * 
     * @param busqueda Objeto que contiene los criterios de búsqueda
     * @param criteria Criteria al que se añadirán los parámetros
     */
    private void consultaCriteriaGuiasPersonalizadas(GuiaBusqueda busqueda, Criteria criteria) {
        
        if (busqueda.getFechaDesde() != null) {
            criteria.add(Restrictions.ge(Constantes.FECHACREACION, busqueda.getFechaDesde()));
        }
        if (busqueda.getFechaHasta() != null) {
            Date fechaHasta = new Date(busqueda.getFechaHasta().getTime() + TimeUnit.DAYS.toMillis(1));
            criteria.add(Restrictions.le(Constantes.FECHACREACION, fechaHasta));
        }
        
        if (busqueda.getNombre() != null) {
            criteria.add(Restrictions.ilike("nombreGuiaPersonalizada", busqueda.getNombre(), MatchMode.ANYWHERE));
        }
        
        if (busqueda.getUsuarioCreacion() != null) {
            criteria.add(Restrictions.ilike("usernameCreacion", busqueda.getUsuarioCreacion(), MatchMode.ANYWHERE));
        }
        criteria.createAlias("guiaPersonalizada.guia", "guia"); // inner join
        if (busqueda.getTipoInspeccion() != null) {
            criteria.add(Restrictions.eq("guia.tipoInspeccion", busqueda.getTipoInspeccion()));
        }
        if (busqueda.getEstado() != null) {
            if (EstadoEnum.INACTIVO.equals(busqueda.getEstado())) {
                criteria.add(Restrictions.isNotNull("fechaAnulacion"));
            } else {
                criteria.add(Restrictions.isNull("fechaAnulacion"));
            }
        }
        criteria.add(Restrictions.isNull("fechaBaja"));
        
    }
    
    /**
     * Devuelve una lista de pasos contenidos en una guía personalizada pasada como parámetro.
     * 
     * @param guia de la que se desea recuperar los pasos
     * @return lista de los pasos
     * 
     */
    @Override
    public List<GuiaPasos> listaPasos(GuiaPersonalizada guia) {
        return pasosRepository.findPasosElegidosGuiaPersonalizada(guia.getId());
    }
    
    /**
     * Anula una guía personalizada de la base de datos.
     * 
     * @param guia que se desea eliminar
     * 
     */
    @Override
    public void anular(GuiaPersonalizada guia) {
        guia.setFechaAnulacion(new Date());
        guia.setUsernameAnulacion(SecurityContextHolder.getContext().getAuthentication().getName());
        guiaPersonalizadaRepository.save(guia);
        
    }
    
    /**
     * Comprueba la existencia en base de datos de guías personalizadas cuya guía modelo corresponde a la recibida como
     * parámetro.
     * 
     * @param guia Guía de la que quiere confirmarse si existen guías personalizadas
     * @return Booleano correspondiendo a la respuesta
     * 
     */
    @Override
    public boolean buscarPorModeloGuia(Guia guia) {
        return guiaPersonalizadaRepository.findByIdGuia(guia);
    }
    
    /**
     * Lista las inspecciones asignadas a una guía personalizada.
     * 
     * @param guia Guía de la que se quiere recuperar las inspecciones
     * @return respuesta
     */
    @Override
    public List<Inspeccion> listaInspecciones(GuiaPersonalizada guia) {
        return inspeccionRepository.cargaInspeccionesGuia(guia.getId());
    }
    
}
