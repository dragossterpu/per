package es.mira.progesin.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Throwables;

import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.repositories.IRegActividadRepository;
import es.mira.progesin.web.beans.RegActividadBusqueda;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Implementación del servicio de Registro de Actividad.
 * 
 * @author EZENTIS
 *
 */

@Slf4j
@Service("registroActividadService")
public class RegistroActividadService implements IRegistroActividadService {
    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    @Autowired
    private IRegActividadRepository regActividadRepository;
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    @Transactional(readOnly = false)
    public RegistroActividad save(RegistroActividad entity) {
        return regActividadRepository.save(entity);
    }
    
    @Override
    public int getCounCriteria(RegActividadBusqueda regActividadBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(RegistroActividad.class);
        creaCriteria(regActividadBusqueda, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
    @Override
    public List<RegistroActividad> buscarRegActividadCriteria(int first, int pageSize, String sortField,
            SortOrder sortOrder, RegActividadBusqueda regActividadBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(RegistroActividad.class);
        creaCriteria(regActividadBusqueda, criteria);
        
        criteria.setFirstResult(first);
        criteria.setMaxResults(pageSize);
        
        if (sortField != null && sortOrder.equals(SortOrder.ASCENDING)) {
            criteria.addOrder(Order.asc(sortField));
        } else if (sortField != null && sortOrder.equals(SortOrder.DESCENDING)) {
            criteria.addOrder(Order.desc(sortField));
        } else if (sortField == null) {
            criteria.addOrder(Order.desc("idRegActividad"));
        }
        
        @SuppressWarnings("unchecked")
        List<RegistroActividad> listado = criteria.list();
        session.close();
        
        return listado;
    }
    
    private void creaCriteria(RegActividadBusqueda regActividadBusqueda, Criteria criteria) {
        
        if (regActividadBusqueda.getFechaDesde() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions
                    .sqlRestriction("TRUNC(fecha_alta) >= '" + sdf.format(regActividadBusqueda.getFechaDesde()) + "'"));
        }
        if (regActividadBusqueda.getFechaHasta() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions
                    .sqlRestriction("TRUNC(fecha_alta) <= '" + sdf.format(regActividadBusqueda.getFechaHasta()) + "'"));
        }
        if (regActividadBusqueda.getNombreSeccion() != null && !regActividadBusqueda.getNombreSeccion().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    "upper(convert(replace(nombre_seccion, ' ', ''), 'US7ASCII')) LIKE upper(convert('%' || replace('"
                            + regActividadBusqueda.getNombreSeccion() + "', ' ', '') || '%', 'US7ASCII'))"));
        }
        if (regActividadBusqueda.getTipoRegActividad() != null
                && !regActividadBusqueda.getTipoRegActividad().isEmpty()) {
            criteria.add(Restrictions.ilike("tipoRegActividad", regActividadBusqueda.getTipoRegActividad(),
                    MatchMode.ANYWHERE));
        }
        if (regActividadBusqueda.getUsernameRegActividad() != null
                && !regActividadBusqueda.getUsernameRegActividad().isEmpty()) {
            criteria.add(Restrictions.ilike("usernameRegActividad", regActividadBusqueda.getUsernameRegActividad(),
                    MatchMode.ANYWHERE));
        }
        
    }
    
    @Override
    public void altaRegActividadError(String nombreSeccion, Exception e) {
        try {
            
            RegistroActividad registroActividad = new RegistroActividad();
            registroActividad.setTipoRegActividad(TipoRegistroEnum.ERROR.name());
            registroActividad.setFechaAlta(new Date());
            registroActividad.setNombreSeccion(nombreSeccion);
            registroActividad.setUsernameRegActividad(SecurityContextHolder.getContext().getAuthentication().getName());
            registroActividad.setDescripcion(Throwables.getStackTraceAsString(e));
            regActividadRepository.save(registroActividad);
        } catch (Exception e1) {
            log.error(nombreSeccion, e1);
            
        }
    }
    
    @Override
    public void altaRegActividad(String descripcion, String tipoReg, String seccion) {
        try {
            RegistroActividad registroActividad = new RegistroActividad();
            registroActividad.setTipoRegActividad(tipoReg);
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                registroActividad
                        .setUsernameRegActividad(SecurityContextHolder.getContext().getAuthentication().getName());
            } else {
                registroActividad.setUsernameRegActividad("system");
            }
            registroActividad.setFechaAlta(new Date());
            registroActividad.setNombreSeccion(seccion);
            registroActividad.setDescripcion(descripcion);
            regActividadRepository.save(registroActividad);
        } catch (Exception e) {
            altaRegActividadError(seccion, e);
        }
        
    }
    
    @Override
    public List<String> buscarPorNombreSeccion(String infoSeccion) {
        return regActividadRepository.buscarPorNombreSeccion("%" + infoSeccion + "%");
    }
    
    @Override
    public List<String> buscarPorUsuarioRegistro(String infoUsuario) {
        return regActividadRepository.buscarPorUsuarioRegistro("%" + infoUsuario + "%");
    }
    
}
