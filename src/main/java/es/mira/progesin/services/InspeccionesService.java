package es.mira.progesin.services;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IInspeccionesRepository;
import es.mira.progesin.web.beans.InspeccionBusqueda;

/**
 * Implementación del servicio de inspecciones.
 * 
 * @author EZENTIS
 */
@Service
public class InspeccionesService implements IInspeccionesService {
    
    /**
     * Variable para el formato de la fecha.
     */
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * Variable utilizada para inyectar la SessionFactory.
     * 
     */
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * Variable utilizada para inyectar el repositorio de inspecciones.
     * 
     */
    @Autowired
    private IInspeccionesRepository inspeccionesRepository;
    
    /**
     * Método que guarda una inspección.
     * @param inspeccion a guardar
     * @return devuelve la inspección guardada
     */
    @Override
    @Transactional(readOnly = false)
    public Inspeccion save(Inspeccion inspeccion) {
        return inspeccionesRepository.save(inspeccion);
    }
    
    /**
     * Borra una inspección pasada por parámetro.
     * @param inspeccion a borrar
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Inspeccion inspeccion) {
        inspeccionesRepository.delete(inspeccion);
    }
    
    /**
     * Busca inspecciones no finalizadas filtrando por el nombre de la unidad o el número de la inspección.
     * 
     * @param infoInspeccion nombre de la unidad o número de inspección
     * @return devuelve una lista con todas las inspecciones filtradas indicando el nombre de la unidad o el número de
     * inspección
     */
    @Override
    public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumero(String infoInspeccion) {
        return inspeccionesRepository.buscarNoFinalizadaPorNombreUnidadONumero("%" + infoInspeccion + "%");
    }
    
    /**
     * Busca inspecciones filtrando por el nombre de la unidad o el número de la inspección.
     * 
     * @param infoInspeccion nombre de la unidad o número de inspección
     * @return devuelve una lista con todas las inspecciones filtradas indicando el nombre de la unidad o el número de
     * inspección
     */
    @Override
    public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo(String infoInspeccion,
            String usernameJefeEquipo) {
        return inspeccionesRepository.buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo("%" + infoInspeccion + "%",
                usernameJefeEquipo);
    }
    
    /**
     * Busca una inspección con cierto id pasado por parámetro.
     * 
     * @param id de la inspección
     * @return devuelve inspección si es encontrada.
     */
    @Override
    public Inspeccion findInspeccionById(Long id) {
        return inspeccionesRepository.findOne(id);
    }
    
    /**
     * Método que realiza una consulta de inspecciones, usando criteria, coincidente con determinados parámetros.
     * 
     * @param first primer registro
     * @param pageSize tamaño de la página (número de registros que queremos)
     * @param sortField campo por el que ordenamos
     * @param sortOrder si la ordenación es ascendente o descendente
     * @param busqueda bean InspeccionBusqueda que define el filtro de la consulta realizada
     * @return lista de inspecciones resultado de la consulta
     */
    @Override
    public List<Inspeccion> buscarInspeccionPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            InspeccionBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Inspeccion.class, "inspeccion");
        consultaCriteriaInspecciones(busqueda, criteria);
        
        criteria.setFirstResult(first);
        criteria.setMaxResults(pageSize);
        
        if (sortField != null && sortOrder.equals(SortOrder.ASCENDING)) {
            criteria.addOrder(Order.asc(sortField));
        } else if (sortField != null && sortOrder.equals(SortOrder.DESCENDING)) {
            criteria.addOrder(Order.desc(sortField));
        } else if (sortField == null) {
            criteria.addOrder(Order.desc("fechaAlta"));
        }
        
        @SuppressWarnings("unchecked")
        List<Inspeccion> listaInspecciones = criteria.list();
        session.close();
        
        return listaInspecciones;
    }
    
    /**
     * @param busqueda bean InspeccionBusqueda que define el filtro de la consulta realizada
     * @return número de registros encontrados
     */
    @Override
    public int getCountInspeccionCriteria(InspeccionBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Inspeccion.class, "inspeccion");
        consultaCriteriaInspecciones(busqueda, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
    /**
     * Consulta criteria para búsqueda de inspecciones.
     * 
     * @param busqueda filtro de búsqueda
     * @param criteria objeto criteria
     */
    private void consultaCriteriaInspecciones(InspeccionBusqueda busqueda, Criteria criteria) {
        String parametro;
        
        if (busqueda.getFechaDesde() != null) {
            criteria.add(Restrictions.ge(Constantes.FECHAALTA, busqueda.getFechaDesde()));
        }
        
        if (busqueda.getFechaHasta() != null) {
            Date fechaHasta = new Date(busqueda.getFechaHasta().getTime() + TimeUnit.DAYS.toMillis(1));
            criteria.add(Restrictions.le(Constantes.FECHAALTA, fechaHasta));
        }
        
        if (busqueda.getId() != null) {
            criteria.add(Restrictions.eq("id", Long.parseLong(busqueda.getId())));
        }
        
        if (busqueda.getAnio() != null) {
            criteria.add(Restrictions.eq("anio", Integer.parseInt(busqueda.getAnio())));
        }
        
        if (busqueda.getUsuarioCreacion() != null) {
            criteria.add(Restrictions.ilike("usernameAlta", busqueda.getUsuarioCreacion(), MatchMode.ANYWHERE));
        }
        
        if (busqueda.getTipoInspeccion() != null) {
            criteria.add(Restrictions.eq("tipoInspeccion", busqueda.getTipoInspeccion()));
        }
        
        if (busqueda.getAmbito() != null) {
            criteria.add(Restrictions.eq("ambito", busqueda.getAmbito()));
        }
        
        if (busqueda.getNombreUnidad() != null) {
            criteria.add(Restrictions.ilike("nombreUnidad", busqueda.getNombreUnidad(), MatchMode.ANYWHERE));
        }
        
        if (busqueda.getCuatrimestre() != null) {
            criteria.add(Restrictions.eq("cuatrimestre", busqueda.getCuatrimestre()));
        }
        
        criteria.createAlias("inspeccion.equipo", "equipo"); // inner join
        if (busqueda.getEquipo() != null) {
            criteria.add(Restrictions.eq("equipo", busqueda.getEquipo()));
        }
        
        if (busqueda.getJefeEquipo() != null && !busqueda.getJefeEquipo().isEmpty()) {
            parametro = Normalizer.normalize(busqueda.getJefeEquipo(), Normalizer.Form.NFKD)
                    .replaceAll(Constantes.ACENTOS, "");
            criteria.add(Restrictions.ilike("equipo.jefeEquipo", parametro, MatchMode.ANYWHERE));
        }
        
        if (busqueda.getEstado() != null) {
            criteria.add(Restrictions.eq("estadoInspeccion", busqueda.getEstado()));
        }
        
        // criteria.createAlias("municipio.provincia", "provincia"); // inner join
        criteria.createAlias("inspeccion.municipio", "municipio"); // inner join
        if (busqueda.getMunicipio() != null) {
            criteria.add(Restrictions.eq("municipio", busqueda.getMunicipio()));
        } else if (busqueda.getProvincia() != null) {
            DetachedCriteria subquery = DetachedCriteria.forClass(Municipio.class, "munic");
            subquery.add(Restrictions.eq("munic.provincia", busqueda.getProvincia()));
            subquery.setProjection(Projections.property("munic.id"));
            criteria.add(Property.forName("inspeccion.municipio").in(subquery));
        }
        
        if (busqueda.getTipoUnidad() != null) {
            criteria.add(Restrictions.eq("tipoUnidad", busqueda.getTipoUnidad()));
        }
        
        criteria.add(Restrictions.isNull("fechaBaja"));
        if (busqueda.isAsociar() && busqueda.getInspeccionModif().getId() != null) {
            criteria.add(Restrictions.ne("id", busqueda.getInspeccionModif().getId()));
        } else if (!busqueda.isAsociar()) {
            User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (RoleEnum.ROLE_EQUIPO_INSPECCIONES.equals(usuarioActual.getRole())) {
                DetachedCriteria subquery = DetachedCriteria.forClass(Miembro.class, "miembro");
                subquery.add(Restrictions.eq("miembro.username", usuarioActual.getUsername()));
                subquery.add(Restrictions.eqProperty("equipo.id", "miembro.equipo"));
                subquery.setProjection(Projections.property("miembro.equipo"));
                criteria.add(Property.forName("equipo.id").in(subquery));
            }
        }
    }
    
    /**
     * Devuelve la lista de inspecciones asociadas a otra.
     * 
     * @param inspeccion pasada por parámetro
     * @return devuelve lista de inspecciones asociadas
     */
    @Override
    public List<Inspeccion> listaInspeccionesAsociadas(Inspeccion inspeccion) {
        return inspeccionesRepository.cargaInspeccionesAsociadas(inspeccion.getId());
    }
    
    /**
     * Comprueba si existe una inspección de determinado tipo.
     * 
     * @param tipo de inspección
     * @return valor booleando de la comprobación
     */
    @Override
    public boolean existeByTipoInspeccion(TipoInspeccion tipo) {
        return inspeccionesRepository.existsByTipoInspeccion(tipo);
    }
    
    /**
     * Busca inspecciones filtrando por el nombre de la unidad o el número de la inspección.
     * 
     * @param infoInspeccion nombre de la unidad o número de inspección
     * @return devuelve una lista con todas las inspecciones filtradas indicando el nombre de la unidad o el número de
     * inspección
     */
    @Override
    public List<Inspeccion> buscarPorNombreUnidadONumero(String infoInspeccion) {
        return inspeccionesRepository.buscarPorNombreUnidadONumero("%" + infoInspeccion + "%");
    }
    
    /**
     * Cambia el estado de una inspección.
     * 
     * @param inspeccion a cambiar
     * @param estado a poner
     */
    @Override
    public void cambiarEstado(Inspeccion inspeccion, EstadoInspeccionEnum estado) {
        inspeccion.setEstadoInspeccion(estado);
        inspeccionesRepository.save(inspeccion);
    }
    
}
