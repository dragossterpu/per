package es.mira.progesin.services;

import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    
    private static final String FECHA_CREACION = "fechaCreacion";
    
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
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions.sqlRestriction(
                    "TRUNC(this_.fecha_creacion) >= '" + sdf.format(cuestionarioBusqueda.getFechaDesde())));
        }
        if (cuestionarioBusqueda.getFechaHasta() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions.sqlRestriction(
                    "TRUNC(this_.fecha_creacion) <= '" + sdf.format(cuestionarioBusqueda.getFechaHasta())));
        }
        
        if (cuestionarioBusqueda.getUsername() != null && !cuestionarioBusqueda.getUsername().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    "upper(convert(replace(USERNAME_CREACION, ' ', ''), 'US7ASCII')) LIKE upper(convert('%' || replace('"
                            + cuestionarioBusqueda.getUsername() + "', ' ', '') || '%', 'US7ASCII'))"));
        }
        if (cuestionarioBusqueda.getModeloCuestionarioSeleccionado() != null) {
            criteria.add(
                    Restrictions.eq("modeloCuestionario", cuestionarioBusqueda.getModeloCuestionarioSeleccionado()));
        }
        if (cuestionarioBusqueda.getNombreCuestionario() != null
                && !cuestionarioBusqueda.getNombreCuestionario().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    "upper(convert(replace(NOMBRE_CUESTIONARIO, ' ', ''), 'US7ASCII')) LIKE upper(convert('%' || replace('"
                            + cuestionarioBusqueda.getNombreCuestionario() + "', ' ', '') || '%', 'US7ASCII'))"));
            
        }
        if (cuestionarioBusqueda.getEstado() == null || cuestionarioBusqueda.getEstado().equals(EstadoEnum.ACTIVO)) {
            criteria.add(Restrictions.isNull(Constantes.FECHABAJA));
            criteria.addOrder(Order.desc(FECHA_CREACION));
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
