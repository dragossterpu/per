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
    
    /**
     * Guarda en base de datos un registro de actividad.
     * 
     * @param entity Registro a guardar en base de datos
     * @return Registro guardado
     */
    @Override
    @Transactional(readOnly = false)
    public RegistroActividad save(RegistroActividad entity) {
        return regActividadRepository.save(entity);
    }
    
    /**
     * Devuelve el número total de registros de una búsqueda.
     * 
     * @param regActividadBusqueda Objeto que contiene los criterios de búsqueda
     * @return Número de registros que responden a la búsqueda
     */
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
    
    /**
     * Busca registros de actividad según unos parámetros de forma paginada.
     * 
     * @param first Primer elemento a mostrar
     * @param pageSize Número máximo de registros recuperados
     * @param sortField Campo de ordenación
     * @param sortOrder Sentido de la ordenación
     * @param regActividadBusqueda Objeto que contiene los parámetros de búsqueda
     * @return Listado de los registros que responden a la búsqueda
     */
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
    
    /**
     * Consulta criteria para la búsqueda del registro de actividad.
     * 
     * @param criteria consulta criteria
     * @param regActividadBusqueda Objeto que contiene los parámetros de búsqueda
     */
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
    
    /**
     * Se introduce un registro de error en la base de datos.
     * 
     * @param nombreSeccion Nombre de la sección en la que se produce el error
     * @param e Excepción lanzada por la aplicación cuando se produce el error
     */
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
        } catch (SQLException e1) {
            log.error(nombreSeccion, e1);
            
        }
    }
    
    /**
     * Se introduce un registro de actividad en la base de datos.
     * 
     * @param descripcion Descripción del registro
     * @param tipoReg Tipo de actividad registrada
     * @param seccion Sección en la que se hace el registro
     */
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
    
    /**
     * Listado de registros de actividad para una sección.
     * 
     * @param infoSeccion Sección para la que se hace la consulta
     * @return Listado de los registros de la sección
     */
    @Override
    public List<String> buscarPorNombreSeccion(String infoSeccion) {
        return regActividadRepository.buscarPorNombreSeccion("%" + infoSeccion + "%");
    }
    
    /**
     * Busca los registros realizados por un usuario.
     * 
     * @param infoUsuario Usuario para el que se hace la consulta
     * @return Listado de los registros resultantes
     */
    @Override
    public List<String> buscarPorUsuarioRegistro(String infoUsuario) {
        return regActividadRepository.buscarPorUsuarioRegistro("%" + infoUsuario + "%");
    }
    
}
