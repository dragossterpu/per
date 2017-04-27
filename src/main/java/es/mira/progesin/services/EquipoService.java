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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.repositories.IEquipoRepository;
import es.mira.progesin.persistence.repositories.IMiembrosRepository;
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
    
    private static final String ACENTOS = "\\p{InCombiningDiacriticalMarks}+";
    
    @Autowired
    private IEquipoRepository equipoRepository;
    
    @Autowired
    private IMiembrosRepository miembrosRepository;
    
    @Autowired
    private SessionFactory sessionFactory;
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    @Override
    public Iterable<Equipo> findAll() {
        return equipoRepository.findAll();
    }
    
    @Override
    public Equipo save(Equipo entity) {
        return equipoRepository.save(entity);
    }
    
    @Override
    public Miembro save(Miembro miembro) {
        return miembrosRepository.save(miembro);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Miembro> save(List<Miembro> listaMiembros) {
        return (List<Miembro>) miembrosRepository.save(listaMiembros);
    }
    
    @Override
    public List<Miembro> findByEquipo(Equipo equipo) {
        return miembrosRepository.findByEquipo(equipo);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Equipo> buscarEquipoCriteria(EquipoBusqueda equipoBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Equipo.class, "equipo");
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
                    .replaceAll(ACENTOS, "");
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
        @SuppressWarnings("unchecked")
        List<Equipo> listEquipos = criteria.list();
        session.close();
        
        return listEquipos;
    }
    
    @Override
    public boolean existsByTipoEquipo(TipoEquipo tipo) {
        return equipoRepository.existsByTipoEquipo(tipo);
    }
    
    @Override
    public List<Equipo> findByFechaBajaIsNotNull() {
        return equipoRepository.findByFechaBajaIsNull();
    }
    
}
