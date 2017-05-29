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
 * Sevicio para la clase Equipo.
 *
 * @author EZENTIS
 */
@Service
public class EquipoService implements IEquipoService {
    
    /**
     * Repositorio de equipos.
     */
    @Autowired
    private IEquipoRepository equipoRepository;
    
    /**
     * Session factory.
     */
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * Recupera todos los equipos activos y dados de baja.
     * 
     * @return lista de equipos
     */
    @Override
    public List<Equipo> findAll() {
        return (List<Equipo>) equipoRepository.findAll();
    }
    
    /**
     * Guarda la información de un equipo en la bdd.
     * 
     * @param entity equipo
     * @return equipo con id
     */
    @Override
    public Equipo save(Equipo entity) {
        return equipoRepository.save(entity);
    }
    
    /**
     * Método que devuelve la lista de equipos en una consulta basada en criteria.
     * @param equipoBusqueda filtro de la búsqueda
     * @param criteria objeto criteria
     */
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
            
            parametro = Normalizer.normalize(equipoBusqueda.getNombreMiembro(), Normalizer.Form.NFKD)
                    .replaceAll(Constantes.ACENTOS, "");
            subquery.add(Restrictions.ilike("miembro.nombreCompleto", parametro, MatchMode.ANYWHERE));
            subquery.add(Restrictions.eqProperty("equipo.id", "miembro.equipo"));
            subquery.setProjection(Projections.property("miembro.equipo"));
            criteria.add(Property.forName("equipo.id").in(subquery));
            
        }
        if (equipoBusqueda.getEstado() != null && equipoBusqueda.getEstado().equals(EstadoEnum.ACTIVO.name())) {
            criteria.add(Restrictions.isNull(Constantes.FECHABAJA));
        }
        if (equipoBusqueda.getEstado() != null && equipoBusqueda.getEstado().equals(EstadoEnum.INACTIVO.name())) {
            criteria.add(Restrictions.isNotNull(Constantes.FECHABAJA));
            criteria.addOrder(Order.desc(Constantes.FECHABAJA));
        } else {
            criteria.addOrder(Order.desc(Constantes.FECHAALTA));
        }
        
    }
    
    /**
     * Comprueba si existe algún equipo del tipo proporcionado.
     * 
     * @param tipo de equipo
     * @return boolean valor booleano
     */
    @Override
    public boolean existsByTipoEquipo(TipoEquipo tipo) {
        return equipoRepository.existsByTipoEquipo(tipo);
    }
    
    /**
     * Recupera sólo los equipos activos.
     * 
     * @return lista de equipos
     */
    @Override
    public List<Equipo> findByFechaBajaIsNull() {
        return equipoRepository.findByFechaBajaIsNull();
    }
    
    /**
     * Método que devuelve la lista de equipos en una consulta basada en criteria.
     * 
     * @param equipoBusqueda objeto con los criterios de búsqueda
     * @param first primer elemento
     * @param pageSize tamaño de cada página de resultados
     * @param sortField campo por el que se ordenan los resultados
     * @param sortOrder sentido de la ordenacion (ascendente/descendente)
     * @return la lista de equipos.
     */
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
    
    /**
     * Método que devuelve el número de equipos en una consulta basada en criteria.
     * 
     * @param busqueda objeto con parámetros de búsqueda
     * @return devuelve el número de registros de una consulta criteria.
     */
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
    
    /**
     * Búsqueda de equipos por nombre de usuario.
     * 
     * @param paramLogin nombre usuario (username)
     * @return listado de equipos a los que pertenece el usuario
     */
    @Override
    public List<Equipo> buscarEquiposByUsername(String paramLogin) {
        return equipoRepository.buscarEquiposByUsername(paramLogin);
    }
    
}
