package es.mira.progesin.services;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;

/**
 * Clase utilizada para reducir la duplicidad de código en los critera de los buscadores.
 * 
 * @author EZENTIS
 *
 */
@Service("criteriaService")
public class CriteriaService implements ICriteriaService {
    
    /**
     * Prepara el criteria pasado como parámetro para la paginación de Primefaces.
     * 
     * @param criteria criteria a configurar
     * @param first primer elemento
     * @param pageSize tamaño de cada página de resultados
     * @param sortField campo por el que se ordenan los resultados
     * @param sortOrder sentido de la ordenacion (ascendente/descendente)
     * @param defaultField campo de ordenación por defecto
     */
    @Override
    public void prepararPaginacionOrdenCriteria(Criteria criteria, int first, int pageSize, String sortField,
            SortOrder sortOrder, String defaultField) {
        criteria.setFirstResult(first);
        criteria.setMaxResults(pageSize);
        
        if (sortField != null && sortOrder.equals(SortOrder.ASCENDING)) {
            criteria.addOrder(Order.asc(sortField));
        } else if (sortField != null && sortOrder.equals(SortOrder.DESCENDING)) {
            criteria.addOrder(Order.desc(sortField));
        } else if (sortField == null) {
            criteria.addOrder(Order.asc(defaultField));
        }
    }
    
    /**
     * Añade al criteria als restricciones necesarias para realizar las consultas en caso de que el usuario conectado
     * tenga el rol EQUIPO_INSPECCIONES.
     * @param criteria criteria al que se desea añadir la restricción
     */
    @Override
    public void setCriteriaEquipo(Criteria criteria) {
        User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (RoleEnum.ROLE_EQUIPO_INSPECCIONES.equals(usuarioActual.getRole())) {
            DetachedCriteria subquery = DetachedCriteria.forClass(Miembro.class, "miembro");
            subquery.add(Restrictions.eq("miembro.usuario", usuarioActual));
            subquery.add(Restrictions.eqProperty("equipo.id", "miembro.equipo"));
            subquery.setProjection(Projections.property("miembro.equipo"));
            criteria.add(Property.forName("equipo.id").in(subquery));
        }
    }
}
