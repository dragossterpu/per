package es.mira.progesin.services;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.repositories.IEquipoRepository;
import es.mira.progesin.web.beans.EquipoBusqueda;
import lombok.NoArgsConstructor;

/**
 * Sevicio para la clase Equipo.
 *
 * @author EZENTIS
 */
@NoArgsConstructor
@Service
public class EquipoService implements IEquipoService {
    
    /**
     * Repositorio de equipos.
     */
    @Autowired
    private IEquipoRepository equipoRepository;
    
    /**
     * Session factory.
     */
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * Servicio para usar los métodos usados junto con criteria.
     */
    @Autowired
    private ICriteriaService criteriaService;
    
    /**
     * Constructor usado para el test.
     * 
     * @param sessionFact Factoría de sesiones
     * @param criteriaServic Servicio Criteria
     */
    public EquipoService(SessionFactory sessionFact, CriteriaService criteriaServic) {
        this.sessionFactory = sessionFact;
        this.criteriaService = criteriaServic;
    }
    
    /**
     * Recupera todos los equipos activos y dados de baja.
     * 
     * @return lista de equipos
     */
    @Override
    public List<Equipo> findAll() {
        return (List<Equipo>) equipoRepository.findAll();
    }
    
    /**
     * Guarda la información de un equipo en la bdd.
     * 
     * @param entity equipo
     * @return equipo con id
     */
    @Override
    public Equipo save(Equipo entity) {
        return equipoRepository.save(entity);
    }
    
    /**
     * Método que devuelve la lista de equipos en una consulta basada en criteria.
     * @param equipoBusqueda filtro de la búsqueda
     * @param criteria objeto criteria
     */
    private void buscarCriteria(EquipoBusqueda equipoBusqueda, Criteria criteria) {
        
        if (equipoBusqueda.getFechaDesde() != null) {
            criteria.add(Restrictions.ge(Constantes.FECHAALTA, equipoBusqueda.getFechaDesde()));
        }
        if (equipoBusqueda.getFechaHasta() != null) {
            Date fechaHasta = new Date(equipoBusqueda.getFechaHasta().getTime() + TimeUnit.DAYS.toMillis(1));
            criteria.add(Restrictions.le(Constantes.FECHAALTA, fechaHasta));
        }
        criteria.createAlias("equipo.jefeEquipo", "jefe");
        if (equipoBusqueda.getNombreJefe() != null) {
            
            criteria.add(Restrictions.ilike("jefe.username", equipoBusqueda.getNombreJefe(), MatchMode.ANYWHERE));
        }
        if (equipoBusqueda.getNombreEquipo() != null) {
            criteria.add(Restrictions.ilike("nombreEquipo", equipoBusqueda.getNombreEquipo(), MatchMode.ANYWHERE));
        }
        criteria.createAlias("equipo.tipoEquipo", "tipoEquipo"); // inner join
        if (equipoBusqueda.getTipoEquipo() != null) {
            criteria.add(Restrictions.eq("tipoEquipo.id", equipoBusqueda.getTipoEquipo().getId()));
        }
        
        criteriaMiembro(criteria, equipoBusqueda.getNombreMiembro());
        
        if (EstadoEnum.INACTIVO.name().equals(equipoBusqueda.getEstado())) {
            criteria.add(Restrictions.isNotNull(Constantes.FECHABAJA));
        } else if (EstadoEnum.ACTIVO.name().equals(equipoBusqueda.getEstado())) {
            criteria.add(Restrictions.isNull(Constantes.FECHABAJA));
        }
        
        criteria.addOrder(Order.desc(Constantes.FECHAALTA));
    }
    
    /**
     * Añade al criteria el filtro para el miembro de un equipo.
     * 
     * @param criteria Criteria
     * @param usernameMiembro username del miembro introducido en el filtro
     */
    private void criteriaMiembro(Criteria criteria, String usernameMiembro) {
        if (usernameMiembro != null) {
            DetachedCriteria subquery = DetachedCriteria.forClass(Miembro.class, "miembro");
            subquery.createAlias("miembro.usuario", "usuario");
            subquery.add(Restrictions.ilike("usuario.username", usernameMiembro, MatchMode.ANYWHERE));
            subquery.add(Restrictions.eqProperty("equipo.id", "miembro.equipo"));
            subquery.setProjection(Projections.property("miembro.equipo"));
            criteria.add(Property.forName("equipo.id").in(subquery));
        }
    }
    
    /**
     * Comprueba si existe algún equipo del tipo proporcionado.
     * 
     * @param tipo de equipo
     * @return boolean valor booleano
     */
    @Override
    public boolean existsByTipoEquipo(TipoEquipo tipo) {
        return equipoRepository.existsByTipoEquipo(tipo);
    }
    
    /**
     * Recupera sólo los equipos activos.
     * 
     * @return lista de equipos
     */
    @Override
    public List<Equipo> findByFechaBajaIsNull() {
        return equipoRepository.findByFechaBajaIsNull();
    }
    
    /**
     * Método que devuelve la lista de equipos en una consulta basada en criteria.
     * 
     * @param equipoBusqueda objeto con los criterios de búsqueda
     * @param first primer elemento
     * @param pageSize tamaño de cada página de resultados
     * @param sortField campo por el que se ordenan los resultados
     * @param sortOrder sentido de la ordenacion (ascendente/descendente)
     * @return la lista de equipos.
     */
    @Override
    public List<Equipo> buscarEquipoCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            EquipoBusqueda equipoBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Equipo.class, "equipo");
        buscarCriteria(equipoBusqueda, criteria);
        
        criteriaService.prepararPaginacionOrdenCriteria(criteria, first, pageSize, sortField, sortOrder, "id");
        
        @SuppressWarnings("unchecked")
        List<Equipo> listado = criteria.list();
        session.close();
        
        return listado;
    }
    
    /**
     * Método que devuelve el número de equipos en una consulta basada en criteria.
     * 
     * @param busqueda objeto con parámetros de búsqueda
     * @return devuelve el número de registros de una consulta criteria.
     */
    @Override
    public int getCounCriteria(EquipoBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Equipo.class, "equipo");
        buscarCriteria(busqueda, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
    /**
     * Búsqueda de equipos por nombre de usuario.
     * 
     * @param paramLogin nombre usuario (username)
     * @return listado de equipos a los que pertenece el usuario
     */
    @Override
    public List<Equipo> buscarEquiposByUsername(String paramLogin) {
        return equipoRepository.buscarEquiposByUsername(paramLogin);
    }
    
    /**
     * Devuelve un equipo localizado por su id.
     * 
     * @param id Identificador del equipo
     * @return Equipo
     */
    @Override
    public Equipo findOne(Long id) {
        return equipoRepository.findOne(id);
    }
    
    /**
     * Devuelve el equipo de un miembro con Rol jefe.
     * 
     * @param paramLogin username jefe
     * @return lista de equipos a los que pertenece el jefe
     */
    @Override
    public List<Equipo> buscarEquipoByJefe(String paramLogin) {
        return equipoRepository.buscarEquipoByJefe(paramLogin);
    }
    
}
