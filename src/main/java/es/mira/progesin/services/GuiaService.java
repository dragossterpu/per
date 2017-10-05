package es.mira.progesin.services;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.repositories.IGuiasPasosRepository;
import es.mira.progesin.persistence.repositories.IGuiasRepository;
import es.mira.progesin.web.beans.GuiaBusqueda;
import lombok.NoArgsConstructor;

/**
 * 
 * Implementación de los métodos definidos en la interfaz IGuiaService.
 * 
 * @author EZENTIS
 *
 */
@NoArgsConstructor
@Service
public class GuiaService implements IGuiaService {
    
    /**
     * Factoría de sesiones.
     */
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * Repositorio de pasos de guía.
     */
    @Autowired
    private IGuiasPasosRepository pasosRepository;
    
    /**
     * Repositorio de guías.
     */
    @Autowired
    private IGuiasRepository guiaRepository;
    
    /**
     * Servicio para usar los métodos usados junto con criteria.
     */
    @Autowired
    private ICriteriaService criteriaService;
    
    /**
     * Constructor usado para el test.
     * 
     * @param sessionFact Factoría de sesiones
     * @param criteriaServ Servicio Criteria
     */
    public GuiaService(SessionFactory sessionFact, ICriteriaService criteriaServ) {
        this.sessionFactory = sessionFact;
        this.criteriaService = criteriaServ;
    }
    
    /**
     * Añade los parámetros de búsqueda al criteria.
     * 
     * @param busquedaGuia Objeto que contiene los parámetros e introducir
     * @param criteria Criteria a modificar
     */
    
    private void creaCriteria(GuiaBusqueda busquedaGuia, Criteria criteria) {
        
        if (busquedaGuia.getFechaDesde() != null) {
            criteria.add(Restrictions.ge(Constantes.FECHAALTA, busquedaGuia.getFechaDesde()));
        }
        
        if (busquedaGuia.getFechaHasta() != null) {
            Date fechaHasta = new Date(busquedaGuia.getFechaHasta().getTime() + TimeUnit.DAYS.toMillis(1));
            criteria.add(Restrictions.le(Constantes.FECHAALTA, fechaHasta));
        }
        
        if (busquedaGuia.getNombre() != null) {
            criteria.add(Restrictions.ilike("nombre", busquedaGuia.getNombre(), MatchMode.ANYWHERE));
        }
        
        if (busquedaGuia.getUsuarioCreacion() != null) {
            criteria.add(Restrictions.ilike("usernameAlta", busquedaGuia.getUsuarioCreacion(), MatchMode.ANYWHERE));
        }
        
        if (busquedaGuia.getTipoInspeccion() != null) {
            criteria.add(Restrictions.eq("tipoInspeccion", busquedaGuia.getTipoInspeccion()));
        }
        
        if (busquedaGuia.getEstado() != null) {
            if (EstadoEnum.INACTIVO.equals(busquedaGuia.getEstado())) {
                criteria.add(Restrictions.isNotNull("fechaAnulacion"));
            } else {
                criteria.add(Restrictions.isNull("fechaAnulacion"));
            }
        }
        
        criteria.add(Restrictions.isNull("fechaBaja"));
        
    }
    
    /**
     * 
     * Devuelve la lista de pasos contenidos en una guía recibida como parámetro.
     * 
     * @param guia Guía de la que se desean recuperar los pasos
     * @return lista de pasos
     * 
     */
    @Override
    public List<GuiaPasos> listaPasos(Guia guia) {
        return pasosRepository.findByIdGuiaOrderByOrdenAsc(guia);
    }
    
    /**
     * Devuelve la lista de pasos que no han sido dados de baja y están contenidos en una guía recibida como parámetro.
     *
     * @param guia Guía de la que se desean recuperar los pasos
     * @return Lista de pasos
     */
    @Override
    public List<GuiaPasos> listaPasosNoNull(Guia guia) {
        return pasosRepository.findByIdGuiaAndFechaBajaIsNullOrderByOrdenAsc(guia);
        
    }
    
    /**
     *
     * Almacena en BDD una guía pasada como parámetro.
     *
     * @param guia a guardar
     * @return Guia guardada
     *
     */
    @Override
    @Transactional(readOnly = false)
    public Guia guardaGuia(Guia guia) {
        return guiaRepository.save(guia);
    }
    
    /**
     *
     * Comprueba la existencia de un paso recibido como parámetro en las guías personalizadas.
     *
     * @param paso Paso del que queremos verificar la existencia
     * @return boolean Existencia o no del paso
     * 
     */
    @Override
    public boolean existePaso(GuiaPasos paso) {
        return pasosRepository.findPasoExistenteEnGuiasPersonalizadas(paso.getId()) != null;
        
    }
    
    /**
     * Permite la eliminación de una guía de la base de datos.
     *
     * @param guia guía a eliminar
     */
    @Override
    public void eliminar(Guia guia) {
        guiaRepository.delete(guia);
        
    }
    
    /**
     * Comprueba si existen modelos de gías asociadas a un determinado tipo de inspección.
     *
     * @param tipo tipo de Inspección
     * @return boolean existencia o no de la asociación
     */
    @Override
    public boolean existeByTipoInspeccion(TipoInspeccion tipo) {
        return guiaRepository.existsByTipoInspeccion(tipo);
    }
    
    /**
     * 
     * Devuelve una lista de guías en función de los criterios de búsqueda recibidos como parámetro. El listado se
     * devuelve paginado.
     * 
     * @param first Primer elemento del listado
     * @param pageSize Número máximo de registros recuperados
     * @param sortField Campo por el que sed realiza la ordenación del listado
     * @param sortOrder Sentido de la ordenación
     * @param busqueda Objeto que contiene los parámetros de búsqueda
     * 
     * @return Listado resultante de la búsqueda
     * 
     */
    @Override
    public List<Guia> buscarGuiaPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            GuiaBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Guia.class);
        creaCriteria(busqueda, criteria);
        
        criteriaService.prepararPaginacionOrdenCriteria(criteria, first, pageSize, sortField, sortOrder, "id");
        
        @SuppressWarnings("unchecked")
        List<Guia> listado = criteria.list();
        session.close();
        
        return listado;
    }
    
    /**
     * Devuelve el número total de registros resultado de la búsqueda.
     * 
     * @param busqueda Objeto que contiene los parámetros de búsqueda
     * @return número de registros resultantes de la búsqueda
     */
    @Override
    public int getCounCriteria(GuiaBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Guia.class);
        creaCriteria(busqueda, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
    /**
     * Devuelve un modelo de guía identificado por su id.
     * 
     * @param id Identificador del modelo
     * @return Modelo recuperado
     */
    @Override
    public Guia findOne(Long id) {
        return guiaRepository.findOne(id);
    }
    
}
