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
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
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
     * Servicio para usar los métodos usados junto con criteria.
     */
    @Autowired
    private ICriteriaService criteriaService;
    
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
        if (pageSize > 0) {
            criteria.setMaxResults(pageSize);
        }
        
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
     * @param busquedaInspecciones filtro de búsqueda
     * @param criteria objeto criteria
     */
    private void consultaCriteriaInspecciones(InspeccionBusqueda busquedaInspecciones, Criteria criteria) {
        
        if (busquedaInspecciones.getFechaDesde() != null) {
            criteria.add(Restrictions.ge(Constantes.FECHAALTA, busquedaInspecciones.getFechaDesde()));
        }
        
        if (busquedaInspecciones.getFechaHasta() != null) {
            Date fechaHasta = new Date(busquedaInspecciones.getFechaHasta().getTime() + TimeUnit.DAYS.toMillis(1));
            criteria.add(Restrictions.le(Constantes.FECHAALTA, fechaHasta));
        }
        
        if (busquedaInspecciones.getId() != null) {
            criteria.add(Restrictions.eq("id", Long.parseLong(busquedaInspecciones.getId())));
        }
        
        if (busquedaInspecciones.getAnio() != null) {
            criteria.add(Restrictions.eq("anio", Integer.parseInt(busquedaInspecciones.getAnio())));
        }
        
        if (busquedaInspecciones.getUsuarioCreacion() != null) {
            criteria.add(
                    Restrictions.ilike("usernameAlta", busquedaInspecciones.getUsuarioCreacion(), MatchMode.ANYWHERE));
        }
        
        if (busquedaInspecciones.getTipoInspeccion() != null) {
            criteria.add(Restrictions.eq("tipoInspeccion", busquedaInspecciones.getTipoInspeccion()));
        }
        
        if (busquedaInspecciones.getAmbito() != null) {
            criteria.add(Restrictions.eq("ambito", busquedaInspecciones.getAmbito()));
        }
        
        if (busquedaInspecciones.getNombreUnidad() != null) {
            criteria.add(
                    Restrictions.ilike("nombreUnidad", busquedaInspecciones.getNombreUnidad(), MatchMode.ANYWHERE));
        }
        
        if (busquedaInspecciones.getCuatrimestre() != null) {
            criteria.add(Restrictions.eq("cuatrimestre", busquedaInspecciones.getCuatrimestre()));
        }
        
        criteria.createAlias("inspeccion.equipo", "equipo"); // inner join
        if (busquedaInspecciones.getEquipo() != null) {
            criteria.add(Restrictions.eq("equipo", busquedaInspecciones.getEquipo()));
        }
        
        if (busquedaInspecciones.getJefeEquipo() != null) {
            criteria.add(Restrictions.ilike("equipo.jefeEquipo.username", busquedaInspecciones.getJefeEquipo(),
                    MatchMode.ANYWHERE));
        }
        
        if (busquedaInspecciones.getEstado() != null) {
            criteria.add(Restrictions.eq("estadoInspeccion", busquedaInspecciones.getEstado()));
        }
        
        criteriaMunicipioProvincia(criteria, busquedaInspecciones.getMunicipio(), busquedaInspecciones.getProvincia());
        
        if (busquedaInspecciones.getTipoUnidad() != null) {
            criteria.add(Restrictions.eq("tipoUnidad", busquedaInspecciones.getTipoUnidad()));
        }
        
        criteria.add(Restrictions.isNull("fechaBaja"));
        
        // Desde el buscador muestro sólo las inspecciones de su mismo equipo, desde asociar inspecciones muestro todas
        if (!busquedaInspecciones.isAsociar()) {
            criteriaService.setCriteriaEquipo(criteria);
        }
        
        criteraAsociarInspeccionModificar(criteria, busquedaInspecciones.isAsociar(),
                busquedaInspecciones.getInspeccionModif());
    }
    
    /**
     * Añade al criteria el filtro del municipio o la provincia.
     * 
     * @param criteria Criteria
     * @param municipio municipio filtrado
     * @param provincia provincia filtrada
     */
    private void criteriaMunicipioProvincia(Criteria criteria, Municipio municipio, Provincia provincia) {
        criteria.createAlias("inspeccion.municipio", "municipio"); // inner join
        if (municipio != null) {
            criteria.add(Restrictions.eq("municipio", municipio));
        } else if (provincia != null) {
            DetachedCriteria subquery = DetachedCriteria.forClass(Municipio.class, "munic");
            subquery.add(Restrictions.eq("munic.provincia", provincia));
            subquery.setProjection(Projections.property("munic.id"));
            criteria.add(Property.forName("inspeccion.municipio").in(subquery));
        }
    }
    
    /**
     * Elimina del resultado de la busqueda la inspección que está modificando.
     * 
     * @param criteria Criteria
     * @param isAsociar true si va al buscador desde la opción "Asociar inspecciones"
     * @param inspeccion inspección a modificar
     */
    private void criteraAsociarInspeccionModificar(Criteria criteria, boolean isAsociar, Inspeccion inspeccion) {
        if (isAsociar && inspeccion != null && inspeccion.getId() != null) {
            criteria.add(Restrictions.ne("id", inspeccion.getId()));
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
    
    /**
     * Devuelve un listado de inspecciones que están en un estado recibido como parámetro.
     * 
     * @param estado Estado de inspección a buscar
     * @return Resultado de la búsqueda
     */
    @Override
    public List<Inspeccion> findByEstadoInspeccion(EstadoInspeccionEnum estado) {
        return inspeccionesRepository.findByEstadoInspeccion(estado);
    }
    
}
