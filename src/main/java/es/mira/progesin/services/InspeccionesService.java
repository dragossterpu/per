package es.mira.progesin.services;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.List;

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
 * 
 * Implementación del servicio de inspecciones.
 * 
 * @author Ezentis
 *
 */
@Service
public class InspeccionesService implements IInspeccionesService {
    
    private static final String ACENTOS = "\\p{InCombiningDiacriticalMarks}+";
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    IInspeccionesRepository inspeccionesRepository;
    
    @Override
    @Transactional(readOnly = false)
    public Inspeccion save(Inspeccion inspeccion) {
        return inspeccionesRepository.save(inspeccion);
    }
    
    @Override
    @Transactional(readOnly = false)
    public void delete(Inspeccion inspeccion) {
        inspeccionesRepository.delete(inspeccion);
    }
    
    @Override
    public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumero(String infoInspeccion) {
        return inspeccionesRepository.buscarNoFinalizadaPorNombreUnidadONumero("%" + infoInspeccion + "%");
    }
    
    @Override
    public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo(String infoInspeccion,
            String usernameJefeEquipo) {
        return inspeccionesRepository.buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo("%" + infoInspeccion + "%",
                usernameJefeEquipo);
    }
    
    @Override
    public Inspeccion findInspeccionById(Long id) {
        return inspeccionesRepository.findOne(id);
    }
    
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
     * @param busqueda
     * @param criteria
     */
    private void consultaCriteriaInspecciones(InspeccionBusqueda busqueda, Criteria criteria) {
        String parametro;
        if (busqueda.getFechaDesde() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions
                    .sqlRestriction("TRUNC(this_.fecha_alta) >= '" + sdf.format(busqueda.getFechaDesde()) + "'"));
        }
        if (busqueda.getFechaHasta() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions
                    .sqlRestriction("TRUNC(this_.fecha_alta) <= '" + sdf.format(busqueda.getFechaHasta()) + "'"));
        }
        
        if (busqueda.getId() != null) {
            criteria.add(Restrictions.eq("id", Long.parseLong(busqueda.getId())));
        }
        
        if (busqueda.getAnio() != null) {
            criteria.add(Restrictions.eq("anio", Integer.parseInt(busqueda.getAnio())));
        }
        
        if (busqueda.getUsuarioCreacion() != null && !busqueda.getUsuarioCreacion().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(String.format(Constantes.COMPARADORSINACENTOS,
                    "this_.username_alta", busqueda.getUsuarioCreacion())));
        }
        
        if (busqueda.getTipoInspeccion() != null) {
            criteria.add(Restrictions.eq("tipoInspeccion", busqueda.getTipoInspeccion()));
        }
        
        if (busqueda.getAmbito() != null) {
            criteria.add(Restrictions.eq("ambito", busqueda.getAmbito()));
        }
        
        if (busqueda.getNombreUnidad() != null && !busqueda.getNombreUnidad().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    String.format(Constantes.COMPARADORSINACENTOS, "this_.nombre_unidad", busqueda.getNombreUnidad())));
        }
        
        if (busqueda.getCuatrimestre() != null) {
            criteria.add(Restrictions.eq("cuatrimestre", busqueda.getCuatrimestre()));
        }
        
        criteria.createAlias("inspeccion.equipo", "equipo"); // inner join
        if (busqueda.getEquipo() != null) {
            criteria.add(Restrictions.eq("equipo", busqueda.getEquipo()));
        }
        
        if (busqueda.getJefeEquipo() != null && !busqueda.getJefeEquipo().isEmpty()) {
            parametro = Normalizer.normalize(busqueda.getJefeEquipo(), Normalizer.Form.NFKD).replaceAll(ACENTOS, "");
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
    
    @Override
    public List<Inspeccion> listaInspeccionesAsociadas(Inspeccion inspeccion) {
        return inspeccionesRepository.cargaInspeccionesAsociadas(inspeccion.getId());
    }
    
    @Override
    public boolean existeByTipoInspeccion(TipoInspeccion tipo) {
        return inspeccionesRepository.existsByTipoInspeccion(tipo);
    }
    
    @Override
    public List<Inspeccion> buscarPorNombreUnidadONumero(String infoInspeccion) {
        return inspeccionesRepository.buscarPorNombreUnidadONumero("%" + infoInspeccion + "%");
    }
    
    @Override
    public void cambiarEstado(Inspeccion inspeccion, EstadoInspeccionEnum estado) {
        inspeccion.setEstadoInspeccion(estado);
        inspeccionesRepository.save(inspeccion);
    }
    
}
