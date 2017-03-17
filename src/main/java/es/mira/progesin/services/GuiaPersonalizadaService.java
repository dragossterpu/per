package es.mira.progesin.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.persistence.repositories.IGuiaPersonalizadaRepository;
import es.mira.progesin.persistence.repositories.IGuiasPasosRepository;
import es.mira.progesin.web.beans.GuiaPersonalizadaBusqueda;

/**********************************************************
 * 
 * Implementación de los métodos definidos en la interfaz IGuiaPersonalizadaService
 * 
 * @author Ezentis
 *
 ***********************************************************/

@Service
public class GuiaPersonalizadaService implements IGuiaPersonalizadaService {
    
    private static final String COMPARADORSINACENTOS = "upper(convert(replace(%1$s, \' \', \'\'), \'US7ASCII\')) LIKE upper(convert(\'%%\' || replace(\'%2$s\', \' \', \'\') || \'%%\', \'US7ASCII\'))";
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private IGuiaPersonalizadaRepository guiaPersonalizadaRepository;
    
    @Autowired
    private IGuiasPasosRepository pasosRepository;
    
    @Override
    public void eliminar(GuiaPersonalizada guia) {
        guiaPersonalizadaRepository.delete(guia);
        
    }
    
    @Override
    public GuiaPersonalizada save(GuiaPersonalizada guia) {
        return guiaPersonalizadaRepository.save(guia);
    }
    
    @Override
    public List<GuiaPersonalizada> buscarGuiaPorCriteria(int firstResult, int maxResults,
            GuiaPersonalizadaBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(GuiaPersonalizada.class, "guiaPersonalizada");
        
        if (busqueda.getFechaDesde() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions
                    .sqlRestriction("TRUNC(this_.fecha_creacion) >= '" + sdf.format(busqueda.getFechaDesde()) + "'"));
        }
        if (busqueda.getFechaHasta() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions
                    .sqlRestriction("TRUNC(this_.fecha_creacion) <= '" + sdf.format(busqueda.getFechaHasta()) + "'"));
        }
        
        if (busqueda.getNombre() != null && !busqueda.getNombre().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    String.format(COMPARADORSINACENTOS, "nombre_guia_personalizada", busqueda.getNombre())));
        }
        
        if (busqueda.getUsuarioCreacion() != null && !busqueda.getUsuarioCreacion().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    String.format(COMPARADORSINACENTOS, "usuario_creacion", busqueda.getUsuarioCreacion())));
        }
        
        if (busqueda.getTipoInspeccion() != null) {
            criteria.createCriteria("guia").add(Restrictions.eq("tipoInspeccion", busqueda.getTipoInspeccion()));
        }
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResults);
        criteria.addOrder(Order.desc("fechaCreacion"));
        
        @SuppressWarnings("unchecked")
        List<GuiaPersonalizada> listaGuias = criteria.list();
        session.close();
        
        return listaGuias;
    }
    
    @Override
    public long getCountGuiaCriteria(GuiaPersonalizadaBusqueda busqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(GuiaPersonalizada.class, "guiaPersonalizada");
        
        if (busqueda.getFechaDesde() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions
                    .sqlRestriction("TRUNC(this_.fecha_creacion) >= '" + sdf.format(busqueda.getFechaDesde()) + "'"));
        }
        if (busqueda.getFechaHasta() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions
                    .sqlRestriction("TRUNC(this_.fecha_creacion) <= '" + sdf.format(busqueda.getFechaHasta()) + "'"));
        }
        
        if (busqueda.getNombre() != null && !busqueda.getNombre().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    String.format(COMPARADORSINACENTOS, "nombre_guia_personalizada", busqueda.getNombre())));
        }
        
        if (busqueda.getUsuarioCreacion() != null && !busqueda.getUsuarioCreacion().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    String.format(COMPARADORSINACENTOS, "usuario_creacion", busqueda.getUsuarioCreacion())));
        }
        
        if (busqueda.getTipoInspeccion() != null) {
            criteria.createCriteria("guia").add(Restrictions.eq("tipoInspeccion", busqueda.getTipoInspeccion()));
        }
        
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        
        session.close();
        
        return cnt;
    }
    
    @Override
    public List<GuiaPasos> listaPasos(GuiaPersonalizada guia) {
        return pasosRepository.findPasosElegidosGuiaPersonalizada(guia.getId());
    }
    
    @Override
    public void anular(GuiaPersonalizada guia) {
        guia.setFechaBaja(new Date());
        guia.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
        guiaPersonalizadaRepository.save(guia);
        
    }
    
}
