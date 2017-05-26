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
import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.repositories.IGuiasPasosRepository;
import es.mira.progesin.persistence.repositories.IGuiasRepository;
import es.mira.progesin.web.beans.GuiaBusqueda;

/**
 * 
 * Implementación de los métodos definidos en la interfaz IGuiaService.
 * 
 * @author Ezentis
 *
 */

@Service
public class GuiaService implements IGuiaService {
    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private IGuiasPasosRepository pasosRepository;
    
    @Autowired
    private IGuiasRepository guiaRepository;
    
    /**
     * Añade los parámetros de búsqueda al criteria.
     * 
     * @param busqueda contiene los parámetros e introducir
     * @param critera criteria a modificar
     */
    
    private void creaCriteria(GuiaBusqueda busqueda, Criteria criteria) {
        
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
        
        if (busqueda.getNombre() != null && !busqueda.getNombre().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    String.format(Constantes.COMPARADORSINACENTOS, "nombre_guia", busqueda.getNombre())));
        }
        
        if (busqueda.getUsuarioCreacion() != null && !busqueda.getUsuarioCreacion().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    String.format(Constantes.COMPARADORSINACENTOS, "username_alta", busqueda.getUsuarioCreacion())));
        }
        
        if (busqueda.getTipoInspeccion() != null) {
            criteria.add(Restrictions.eq("tipoInspeccion", busqueda.getTipoInspeccion()));
        }
        
        if (busqueda.getEstado() != null) {
            if (EstadoEnum.INACTIVO.equals(busqueda.getEstado())) {
                criteria.add(Restrictions.isNotNull("fechaAnulacion"));
            } else {
                criteria.add(Restrictions.isNull("fechaAnulacion"));
            }
        }
        
        criteria.add(Restrictions.isNull("fechaBaja"));
        
    }
    
    @Override
    public List<GuiaPasos> listaPasos(Guia guia) {
        return pasosRepository.findByIdGuiaOrderByOrdenAsc(guia);
    }
    
    @Override
    public List<GuiaPasos> listaPasosNoNull(Guia guia) {
        return pasosRepository.findByIdGuiaAndFechaBajaIsNullOrderByOrdenAsc(guia);
        
    }
    
    @Override
    @Transactional(readOnly = false)
    public Guia guardaGuia(Guia guia) {
        return guiaRepository.save(guia);
    }
    
    @Override
    public boolean existePaso(GuiaPasos paso) {
        return pasosRepository.findPasoExistenteEnGuiasPersonalizadas(paso.getId()) != null;
        
    }
    
    @Override
    public void eliminar(Guia guia) {
        guiaRepository.delete(guia);
        
    }
    
    @Override
    public boolean existeByTipoInspeccion(TipoInspeccion tipo) {
        return guiaRepository.existsByTipoInspeccion(tipo);
    }
    
    @Override
    public List<Guia> buscarGuiaPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            GuiaBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Guia.class);
        creaCriteria(busqueda, criteria);
        
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
        List<Guia> listado = criteria.list();
        session.close();
        
        return listado;
    }
    
    @Override
    public int getCounCriteria(GuiaBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Guia.class);
        creaCriteria(busqueda, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
}
