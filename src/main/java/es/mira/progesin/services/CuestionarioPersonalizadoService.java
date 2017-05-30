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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.repositories.ICuestionarioPersonalizadoRepository;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBusqueda;

/**
 * Servicio de modelos de cuestionario personalizados.
 * 
 * @author EZENTIS
 */
@Service
public class CuestionarioPersonalizadoService implements ICuestionarioPersonalizadoService {
    
    @Autowired
    ICuestionarioPersonalizadoRepository cuestionarioPersRep;
    
    @Autowired
    private SessionFactory sessionFactory;
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    @Override
    @Transactional(readOnly = false)
    public void delete(CuestionarioPersonalizado entity) {
        cuestionarioPersRep.delete(entity);
    }
    
    @Override
    @Transactional(readOnly = false)
    public CuestionarioPersonalizado save(CuestionarioPersonalizado entity) {
        return cuestionarioPersRep.save(entity);
    }
    
    private void creaCriteria(CuestionarioPersonalizadoBusqueda cuestionarioBusqueda, Criteria criteria) {
        
        if (cuestionarioBusqueda.getFechaDesde() != null) {
            criteria.add(Restrictions.ge(Constantes.FECHACREACION, cuestionarioBusqueda.getFechaDesde()));
        }
        if (cuestionarioBusqueda.getFechaHasta() != null) {
            Date fechaHasta = new Date(cuestionarioBusqueda.getFechaHasta().getTime() + TimeUnit.DAYS.toMillis(1));
            criteria.add(Restrictions.le(Constantes.FECHACREACION, fechaHasta));
        }
        
        if (cuestionarioBusqueda.getUsername() != null && !cuestionarioBusqueda.getUsername().isEmpty()) {
            criteria.add(
                    Restrictions.ilike("usernameCreacion", cuestionarioBusqueda.getUsername(), MatchMode.ANYWHERE));
        }
        if (cuestionarioBusqueda.getModeloCuestionarioSeleccionado() != null) {
            criteria.add(
                    Restrictions.eq("modeloCuestionario", cuestionarioBusqueda.getModeloCuestionarioSeleccionado()));
        }
        if (cuestionarioBusqueda.getNombreCuestionario() != null
                && !cuestionarioBusqueda.getNombreCuestionario().isEmpty()) {
            criteria.add(Restrictions.ilike("nombreCuestionario", cuestionarioBusqueda.getNombreCuestionario(),
                    MatchMode.ANYWHERE));
        }
        if (cuestionarioBusqueda.getEstado() == null || cuestionarioBusqueda.getEstado().equals(EstadoEnum.ACTIVO)) {
            criteria.add(Restrictions.isNull(Constantes.FECHABAJA));
            criteria.addOrder(Order.desc(Constantes.FECHACREACION));
        } else if (cuestionarioBusqueda.getEstado().equals(EstadoEnum.INACTIVO)) {
            criteria.add(Restrictions.isNotNull(Constantes.FECHABAJA));
            criteria.addOrder(Order.desc(Constantes.FECHABAJA));
        }
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        
    }
    
    @Override
    public List<CuestionarioPersonalizado> buscarCuestionarioPersonalizadoCriteria(int first, int pageSize,
            String sortField, SortOrder sortOrder, CuestionarioPersonalizadoBusqueda cuestionarioBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(CuestionarioPersonalizado.class);
        creaCriteria(cuestionarioBusqueda, criteria);
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
        List<CuestionarioPersonalizado> listaCuestionarioEnvio = criteria.list();
        session.close();
        
        return listaCuestionarioEnvio;
    }
    
    @Override
    public int getCountCuestionarioCriteria(CuestionarioPersonalizadoBusqueda cuestionarioBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(CuestionarioPersonalizado.class);
        creaCriteria(cuestionarioBusqueda, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
}
