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
import org.springframework.stereotype.Service;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.repositories.IEquipoRepository;
import es.mira.progesin.web.beans.EquipoBusqueda;

/**
 * @author EZENTIS
 * 
 * Sevicio para la clase Equipo
 *
 */
@Service
public class EquipoService implements IEquipoService {
    
    private static final String FECHABAJA = "fechaBaja";
    
    @Autowired
    private IEquipoRepository equipoRepository;
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public List<Equipo> findAll() {
        return (List<Equipo>) equipoRepository.findAll();
    }
    
    @Override
    public Equipo save(Equipo entity) {
        return equipoRepository.save(entity);
    }
    
    private void buscarCriteria(EquipoBusqueda equipoBusqueda, Criteria criteria) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        if (equipoBusqueda.getFechaDesde() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions
                    .sqlRestriction("TRUNC(this_.fecha_alta) >= '" + sdf.format(equipoBusqueda.getFechaDesde()) + "'"));
        }
        if (equipoBusqueda.getFechaHasta() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions
                    .sqlRestriction("TRUNC(this_.fecha_alta) <= '" + sdf.format(equipoBusqueda.getFechaHasta()) + "'"));
        }
        String parametro;
        if (equipoBusqueda.getNombreJefe() != null && !equipoBusqueda.getNombreJefe().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    "upper(convert(replace(NOMBRE_JEFE, ' ', ''), 'US7ASCII')) LIKE upper(convert('%' || replace('"
                            + equipoBusqueda.getNombreJefe() + "', ' ', '') || '%', 'US7ASCII'))"));
        }
        if (equipoBusqueda.getNombreEquipo() != null && !equipoBusqueda.getNombreEquipo().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    "upper(convert(replace(NOMBRE_EQUIPO, ' ', ''), 'US7ASCII')) LIKE upper(convert('%' || replace('"
                            + equipoBusqueda.getNombreEquipo() + "', ' ', '') || '%', 'US7ASCII'))"));
        }
        criteria.createAlias("equipo.tipoEquipo", "tipoEquipo"); // inner join
        if (equipoBusqueda.getTipoEquipo() != null) {
            criteria.add(Restrictions.eq("tipoEquipo.id", equipoBusqueda.getTipoEquipo().getId()));
        }
        if (equipoBusqueda.getNombreMiembro() != null && !equipoBusqueda.getNombreMiembro().isEmpty()) {
            DetachedCriteria subquery = DetachedCriteria.forClass(Miembro.class, "miembro");
            
            // TODO: Cambiar esta condición para que busque sin tildes/espacios por la parte de BDD
            parametro = Normalizer.normalize(equipoBusqueda.getNombreMiembro(), Normalizer.Form.NFKD)
                    .replaceAll(Constantes.ACENTOS, "");
            subquery.add(Restrictions.ilike("miembro.nombreCompleto", parametro, MatchMode.ANYWHERE));
            subquery.add(Restrictions.eqProperty("equipo.id", "miembro.equipo"));
            subquery.setProjection(Projections.property("miembro.equipo"));
            criteria.add(Property.forName("equipo.id").in(subquery));
            
        }
        if (equipoBusqueda.getEstado() != null && equipoBusqueda.getEstado().equals(EstadoEnum.ACTIVO.name())) {
            criteria.add(Restrictions.isNull(FECHABAJA));
        }
        if (equipoBusqueda.getEstado() != null && equipoBusqueda.getEstado().equals(EstadoEnum.INACTIVO.name())) {
            criteria.add(Restrictions.isNotNull(FECHABAJA));
            criteria.addOrder(Order.desc(FECHABAJA));
        } else {
            criteria.addOrder(Order.desc("fechaAlta"));
        }
        
    }
    
    @Override
    public boolean existsByTipoEquipo(TipoEquipo tipo) {
        return equipoRepository.existsByTipoEquipo(tipo);
    }
    
    @Override
    public List<Equipo> findByFechaBajaIsNull() {
        return equipoRepository.findByFechaBajaIsNull();
    }
    
    @Override
    public List<Equipo> buscarEquipoCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            EquipoBusqueda equipoBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Equipo.class, "equipo");
        buscarCriteria(equipoBusqueda, criteria);
        
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
        List<Equipo> listado = criteria.list();
        session.close();
        
        return listado;
    }
    
    @Override
    public int getCounCriteria(EquipoBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Equipo.class, "equipo");
        buscarCriteria(busqueda, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
    @Override
    public List<Equipo> buscarEquiposByUsername(String paramLogin) {
        return equipoRepository.buscarEquiposByUsername(paramLogin);
    }
    
}
