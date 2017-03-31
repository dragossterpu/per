package es.mira.progesin.services;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.repositories.IInspeccionesRepository;
import es.mira.progesin.web.beans.InspeccionBusqueda;

@Service
public class InspeccionesService implements IInspeccionesService {
    
    private static final String COMPARADORSINACENTOS = "upper(convert(replace(%1$s, \' \', \'\'), \'US7ASCII\')) LIKE upper(convert(\'%%\' || replace(\'%2$s\', \' \', \'\') || \'%%\', \'US7ASCII\'))";
    
    private static final String ACENTOS = "\\p{InCombiningDiacriticalMarks}+";
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    IInspeccionesRepository inspeccionesRepository;
    
    @Override
    public Iterable<Inspeccion> findAll() {
        return inspeccionesRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = false)
    public Inspeccion save(Inspeccion inspecciones) {
        return inspeccionesRepository.save(inspecciones);
    }
    
    @Override
    @Transactional(readOnly = false)
    public void delete(Inspeccion inspecciones) {
        inspeccionesRepository.delete(inspecciones);
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
    public List<Inspeccion> buscarInspeccionPorCriteria(int firstResult, int maxResults, InspeccionBusqueda busqueda,
            List<Order> listaOrden) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Inspeccion.class, "inspeccion");
        consultaCriteriaInspecciones(busqueda, criteria);
        
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResults);
        for (Order orden : listaOrden) {
            criteria.addOrder(orden);
        }
        
        @SuppressWarnings("unchecked")
        List<Inspeccion> listaInspecciones = criteria.list();
        session.close();
        
        return listaInspecciones;
    }
    
    @Override
    public long getCountInspeccionCriteria(InspeccionBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Inspeccion.class, "inspeccion");
        consultaCriteriaInspecciones(busqueda, criteria);
        
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        
        session.close();
        
        return cnt;
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
        
        if (busqueda.getId() != null && !busqueda.getId().isEmpty()) {
            criteria.add(Restrictions.eq("id", Long.parseLong(busqueda.getId())));
        }
        
        if (busqueda.getAnio() != null && !busqueda.getAnio().isEmpty()) {
            criteria.add(Restrictions.eq("anio", Integer.parseInt(busqueda.getAnio())));
        }
        
        if (busqueda.getUsuarioCreacion() != null && !busqueda.getUsuarioCreacion().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    String.format(COMPARADORSINACENTOS, "this_.username_alta", busqueda.getUsuarioCreacion())));
        }
        
        if (busqueda.getTipoInspeccion() != null) {
            criteria.add(Restrictions.eq("tipoInspeccion", busqueda.getTipoInspeccion()));
        }
        
        if (busqueda.getAmbito() != null) {
            criteria.add(Restrictions.eq("ambito", busqueda.getAmbito()));
        }
        
        if (busqueda.getNombreUnidad() != null && !busqueda.getNombreUnidad().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    String.format(COMPARADORSINACENTOS, "this_.nombre_unidad", busqueda.getNombreUnidad())));
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
        criteria.createAlias("inspeccion.municipio", "municipio"); // inner join
        criteria.createAlias("municipio.provincia", "provincia"); // inner join
        if (busqueda.getProvincia() != null && !busqueda.getProvincia().getCodigo().equals("00")) {
            criteria.add(Restrictions.eq("provincia", busqueda.getProvincia()));
        }
        
        if (busqueda.getTipoUnidad() != null) {
            criteria.add(Restrictions.eq("tipoUnidad", busqueda.getTipoUnidad()));
        }
        
        if (busqueda.getMunicipio() != null) {
            criteria.add(Restrictions.eq("municipio", busqueda.getMunicipio()));
        }
    }
    
    @Override
    public Inspeccion findInspeccionById(Long id) {
        return inspeccionesRepository.findOne(id);
    }
    
}
