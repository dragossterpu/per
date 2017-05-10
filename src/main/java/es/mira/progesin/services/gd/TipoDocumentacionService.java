package es.mira.progesin.services.gd;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.persistence.repositories.IDocumentacionPreviaRepository;
import es.mira.progesin.persistence.repositories.gd.ITipoDocumentacionRepository;

/**
 * 
 * Implementación del servivio de Tipos de documentación previa
 * 
 * @author EZENTIS
 *
 */
@Service
public class TipoDocumentacionService implements ITipoDocumentacionService {
    @Autowired
    ITipoDocumentacionRepository tipoDocumentacionRepository;
    
    @Autowired
    IDocumentacionPreviaRepository documentacionPreviaRepository;
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public List<TipoDocumentacion> findAll() {
        return tipoDocumentacionRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        tipoDocumentacionRepository.delete(id);
    }
    
    @Override
    @Transactional(readOnly = false)
    public TipoDocumentacion save(TipoDocumentacion entity) {
        return tipoDocumentacionRepository.save(entity);
    }
    
    @Override
    public List<TipoDocumentacion> findByAmbito(AmbitoInspeccionEnum ambito) {
        return tipoDocumentacionRepository.findByAmbito(ambito);
    }
    
    @Override
    @Transactional(readOnly = false)
    public DocumentacionPrevia save(DocumentacionPrevia entity) {
        return documentacionPreviaRepository.save(entity);
    }
    
    @Override
    public List<DocumentacionPrevia> findByIdSolicitud(Long idSolicitud) {
        return documentacionPreviaRepository.findByIdSolicitud(idSolicitud);
    }
    
    @Override
    public List<TipoDocumentacion> buscarRegistros(int first, int pageSize, String sortField, SortOrder sortOrder) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TipoDocumentacion.class);
        
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
        List<TipoDocumentacion> listado = criteria.list();
        session.close();
        
        return listado;
    }
    
    @Override
    public int contarRegistros() {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TipoDocumentacion.class);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
}
