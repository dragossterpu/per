package es.mira.progesin.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.web.beans.DocumentoBusqueda;

/**
 * Clase utilizada para reducir la duplicidad de código en los critera de los buscadores.
 * 
 * @author EZENTIS
 *
 */
@Service("criteriaService")
public class CriteriaService implements ICriteriaService {
    
    /**
     * Servicio de equipos.
     */
    @Autowired
    private IEquipoService equipoService;
    
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
    public void setCriteriaEquipo(Criteria criteria) { // TODO Modificar
        User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (RoleEnum.ROLE_EQUIPO_INSPECCIONES.equals(usuarioActual.getRole())) {
            DetachedCriteria subquery = DetachedCriteria.forClass(Miembro.class, "miembro");
            subquery.add(Restrictions.eq("miembro.usuario", usuarioActual));
            subquery.add(Restrictions.eqProperty("equipo.id", "miembro.equipo"));
            subquery.setProjection(Projections.property("miembro.equipo"));
            
            criteria.add(Property.forName("equipo.id").in(subquery));
        }
    }
    
    /**
     * Añade al criteria la búsqueda según el/los equipo/s de inspección.
     * 
     * @param criteria Criteria al que se añade el criterio
     * @param username Usuario sobre el que se quiere hacer la consulta..
     * 
     */
    @Override
    public void creaCriteriaEquipoInspeccion(Criteria criteria, String username, DocumentoBusqueda busquedaDocumento) {
        List<Equipo> equipos = equipoService.buscarEquiposByUsername(username);
        
        criteria.createAlias("documento.inspeccion", "inspeccion", JoinType.LEFT_OUTER_JOIN);
        Criterion[] clavesOr = new Criterion[2];
        clavesOr[0] = Restrictions.in("inspeccion.equipo", equipos);
        clavesOr[1] = Restrictions.isNull("inspeccion.equipo");
        criteria.add(Restrictions.or(clavesOr));
        
        if (busquedaDocumento.getInspeccion() != null) {
            // criteria.createAlias("inspeccion", "inspecciones");
            criteria.add(Restrictions.eq("inspeccion.id", busquedaDocumento.getInspeccion().getId()));
            criteria.add(Restrictions.eq("inspeccion.anio", busquedaDocumento.getInspeccion().getAnio()));
            
        }
    }
    
    /**
     * Añade al criteria la búsqueda según el equipo de inspección y el tipo de inspección.
     * 
     * @param criteria Criteria al que se añade el criterio
     * 
     */
    @Override
    public void creaCriteriaEquipoInformes(Criteria criteria) {
        User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (RoleEnum.ROLE_EQUIPO_INSPECCIONES.equals(usuarioActual.getRole())) {
            List<Equipo> equipos = equipoService.buscarEquiposByUsername(usuarioActual.getUsername());
            Criterion noExtraoridinaria = Restrictions
                    .not(Restrictions.like("tipoInspeccion.codigo", "EX", MatchMode.START));
            Criterion finalizada = Restrictions.isNotNull(Constantes.FECHAFINALIZACION);
            
            Conjunction noExFinalizada = Restrictions.conjunction();
            noExFinalizada.add(noExtraoridinaria);
            noExFinalizada.add(finalizada);
            
            Disjunction propia = Restrictions.disjunction();
            propia.add(Restrictions.in("inspeccion.equipo", equipos));
            propia.add(noExFinalizada);
            
            criteria.add(propia);
            
        }
    }
}
