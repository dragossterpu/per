package es.mira.progesin.services;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.repositories.ICuestionarioPersonalizadoRepository;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBusqueda;
import lombok.NoArgsConstructor;

/**
 * Servicio de modelos de cuestionario personalizados.
 * 
 * @author EZENTIS
 */
@NoArgsConstructor
@Service
public class CuestionarioPersonalizadoService implements ICuestionarioPersonalizadoService {
    
    /**
     * Variable utilizada para inyectar el repositorio de cuestionarios personalizados.
     * 
     */
    @Autowired
    private ICuestionarioPersonalizadoRepository cuestionarioPersRep;
    
    /**
     * Variable utilizada para inyectar la sesión de spring.
     * 
     */
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * Servicio para usar los métodos usados junto con criteria.
     */
    @Autowired
    private ICriteriaService criteriaService;
    
    /**
     * Constructor usado para el test.
     * 
     * @param sessionFact Factoría de sesiones
     * @param criteriaServ Servicio Criteria
     */
    public CuestionarioPersonalizadoService(SessionFactory sessionFact, ICriteriaService criteriaServ) {
        this.sessionFactory = sessionFact;
        this.criteriaService = criteriaServ;
    }
    
    /**
     * Elimina un cuestionario personalizado a partir de su objeto.
     * 
     * @param entity cuestionario a eliminar
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(CuestionarioPersonalizado entity) {
        cuestionarioPersRep.delete(entity);
    }
    
    /**
     * Guarda la información de un modelo de cuestionario personalizado en la bdd.
     * 
     * @param entity cuestionario a guardar
     * @return cuestionario guardado con id
     */
    @Override
    @Transactional(readOnly = false)
    public CuestionarioPersonalizado save(CuestionarioPersonalizado entity) {
        return cuestionarioPersRep.save(entity);
    }
    
    /**
     * Crea la consulta criteria.
     * 
     * @param cuestionarioBusqueda cuestionario a guardar
     * @param criteria cuestionario a guardar
     */
    private void creaCriteria(CuestionarioPersonalizadoBusqueda cuestionarioBusqueda, Criteria criteria) {
        
        if (cuestionarioBusqueda.getFechaDesde() != null) {
            criteria.add(Restrictions.ge(Constantes.FECHACREACION, cuestionarioBusqueda.getFechaDesde()));
        }
        if (cuestionarioBusqueda.getFechaHasta() != null) {
            Date fechaHasta = new Date(cuestionarioBusqueda.getFechaHasta().getTime() + TimeUnit.DAYS.toMillis(1));
            criteria.add(Restrictions.le(Constantes.FECHACREACION, fechaHasta));
        }
        
        if (cuestionarioBusqueda.getUsername() != null) {
            criteria.add(
                    Restrictions.ilike("usernameCreacion", cuestionarioBusqueda.getUsername(), MatchMode.ANYWHERE));
        }
        if (cuestionarioBusqueda.getModeloCuestionarioSeleccionado() != null) {
            criteria.add(
                    Restrictions.eq("modeloCuestionario", cuestionarioBusqueda.getModeloCuestionarioSeleccionado()));
        }
        if (cuestionarioBusqueda.getNombreCuestionario() != null) {
            criteria.add(Restrictions.ilike("nombreCuestionario", cuestionarioBusqueda.getNombreCuestionario(),
                    MatchMode.ANYWHERE));
        }
        if (cuestionarioBusqueda.getEstado() == null || cuestionarioBusqueda.getEstado().equals(EstadoEnum.ACTIVO)) {
            criteria.add(Restrictions.isNull(Constantes.FECHABAJA));
        } else if (cuestionarioBusqueda.getEstado().equals(EstadoEnum.INACTIVO)) {
            criteria.add(Restrictions.isNotNull(Constantes.FECHABAJA));
        }
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        
    }
    
    /**
     * Método que devuelve la lista de modelos de cuestionario personalizados en una consulta basada en criteria.
     * 
     * @param cuestionarioBusqueda objeto con los criterios de búsqueda
     * @param first primer elemento de la consulta
     * @param pageSize tamaño de cada página de resultados
     * @param sortField campo por el que se ordenan los resultados
     * @param sortOrder sentido de la ordenacion (ascendente/descendente)
     * @return la lista de modelos de cuestionario personalizados.
     */
    @Override
    public List<CuestionarioPersonalizado> buscarCuestionarioPersonalizadoCriteria(int first, int pageSize,
            String sortField, SortOrder sortOrder, CuestionarioPersonalizadoBusqueda cuestionarioBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(CuestionarioPersonalizado.class);
        creaCriteria(cuestionarioBusqueda, criteria);
        
        criteriaService.prepararPaginacionOrdenCriteria(criteria, first, pageSize, sortField, sortOrder, "id");
        
        @SuppressWarnings("unchecked")
        List<CuestionarioPersonalizado> listaCuestionarioEnvio = criteria.list();
        session.close();
        
        return listaCuestionarioEnvio;
    }
    
    /**
     * Método que devuelve el número de modelos de cuestionario personalizados en una consulta basada en criteria.
     * 
     * @param cuestionarioBusqueda objeto con parámetros de búsqueda
     * @return devuelve el número de registros de la consulta criteria.
     */
    @Override
    public int getCountCuestionarioCriteria(CuestionarioPersonalizadoBusqueda cuestionarioBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(CuestionarioPersonalizado.class);
        creaCriteria(cuestionarioBusqueda, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
    /**
     * Metodo que comprueba la existencia de cuestionarios personalizados que tengan como modelo base el que se recibe
     * como parámetro.
     * 
     * @param modelo Modelo del que se desea conocer si hay cuestionarios personalizados.
     * @return Respuesta de la consulta
     */
    @Override
    public boolean existsByModelo(ModeloCuestionario modelo) {
        return cuestionarioPersRep.existsBymodeloCuestionario(modelo);
    }
    
}
