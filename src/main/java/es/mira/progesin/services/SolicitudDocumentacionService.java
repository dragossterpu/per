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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IDocumentacionPreviaRepository;
import es.mira.progesin.persistence.repositories.ISolicitudDocumentacionPreviaRepository;
import es.mira.progesin.web.beans.SolicitudDocPreviaBusqueda;

@Service
public class SolicitudDocumentacionService implements ISolicitudDocumentacionService {
    
    private static final String ACENTOS = "\\p{InCombiningDiacriticalMarks}+";
    
    private static final String FECHAFINALIZACION = "fechaFinalizacion";
    
    private static final String FECHABAJA = "fechaBaja";
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    ISolicitudDocumentacionPreviaRepository solicitudDocumentacionPreviaRepository;
    
    @Autowired
    IUserService userService;
    
    @Autowired
    IDocumentacionPreviaRepository documentacionPreviaRepository;
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    @Override
    @Transactional(readOnly = false)
    public SolicitudDocumentacionPrevia save(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia) {
        return solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
    }
    
    @Override
    public List<SolicitudDocumentacionPrevia> findAll() {
        return (List<SolicitudDocumentacionPrevia>) solicitudDocumentacionPreviaRepository.findAll();
    }
    
    @Override
    public SolicitudDocumentacionPrevia findNoFinalizadaPorCorreoDestinatario(String correo) {
        return solicitudDocumentacionPreviaRepository
                .findByFechaBajaIsNullAndFechaFinalizacionIsNullAndCorreoDestinatarioIgnoreCase(correo);
    }
    
    @Override
    public SolicitudDocumentacionPrevia findEnviadaNoFinalizadaPorCorreoDestinatario(String correo) {
        return solicitudDocumentacionPreviaRepository
                .findByFechaBajaIsNullAndFechaFinalizacionIsNullAndFechaEnvioIsNotNullAndCorreoDestinatarioIgnoreCase(
                        correo);
    }
    
    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        solicitudDocumentacionPreviaRepository.delete(id);
    }
    
    /**
     * Método que devuelve la lista de solicitudes previas en una consulta basada en criteria.
     * 
     * @param solicitudDocPreviaBusqueda
     * @return devuelve la lista de registros tipo SolicitudDocPreviaBusqueda.
     * @author EZENTIS
     */
    @Override
    public List<SolicitudDocumentacionPrevia> buscarSolicitudDocPreviaCriteria(int firstResult, int maxResults,
            SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(SolicitudDocumentacionPrevia.class, "solicitud");
        consultaCriteriaSolicitudesDoc(solicitudDocPreviaBusqueda, criteria);
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResults);
        criteria.addOrder(Order.desc("fechaAlta"));
        
        @SuppressWarnings("unchecked")
        List<SolicitudDocumentacionPrevia> listaSolicitudesDocPrevia = criteria.list();
        session.close();
        
        return listaSolicitudesDocPrevia;
        
    }
    
    /**
     * Método que devuelve el número de solicitudes previas totales en una consulta basada en criteria.
     * 
     * @param solicitudDocPreviaBusqueda
     * @return número de registros
     * @author EZENTIS
     */
    @Override
    public long getCountSolicitudDocPreviaCriteria(SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(SolicitudDocumentacionPrevia.class, "solicitud");
        consultaCriteriaSolicitudesDoc(solicitudDocPreviaBusqueda, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        
        session.close();
        
        return cnt;
        
    }
    
    /**
     * Construye la consulta criteria dependiendo de los valores de un bean de tipo SolicitudDocPreviaBusqueda.
     * 
     * @param solicitudDocPreviaBusqueda
     * @param criteria
     */
    private void consultaCriteriaSolicitudesDoc(SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda,
            Criteria criteria) {
        String campoFecha = "this_.fecha_alta";
        if (solicitudDocPreviaBusqueda.getEstado() != null) {
            switch (solicitudDocPreviaBusqueda.getEstado()) {
                case VALIDADA_APOYO:
                    criteria.add(Restrictions.isNotNull("fechaValidApoyo"));
                    criteria.add(Restrictions.isNull("fechaValidJefeEquipo"));
                    break;
                case VALIDADA_JEFE_EQUIPO:
                    criteria.add(Restrictions.isNotNull("fechaValidJefeEquipo"));
                    criteria.add(Restrictions.isNull("fechaEnvio"));
                    break;
                // No se comprueba anulaciones (fecha_baja o fecha_finalizacion) en estados antes de envío porque hay
                // eliminación física
                case ENVIADA:
                    criteria.add(Restrictions.isNotNull("fechaEnvio"));
                    criteria.add(Restrictions.isNull("fechaCumplimentacion"));
                    criteria.add(Restrictions.isNull(FECHABAJA));
                    break;
                case CUMPLIMENTADA:
                    criteria.add(Restrictions.isNotNull("fechaCumplimentacion"));
                    criteria.add(Restrictions.isNull(FECHAFINALIZACION));
                    criteria.add(Restrictions.isNull(FECHABAJA));
                    break;
                // Aparecen como no conformes tanto si están sólo reenviadas como si están recumplimentadas
                case NO_CONFORME:
                    criteria.add(Restrictions.isNotNull("fechaNoConforme"));
                    criteria.add(Restrictions.isNull(FECHAFINALIZACION));
                    criteria.add(Restrictions.isNull(FECHABAJA));
                    break;
                case FINALIZADA:
                    criteria.add(Restrictions.isNotNull(FECHAFINALIZACION));
                    criteria.add(Restrictions.isNull(FECHABAJA));
                    break;
                case ANULADA:
                    criteria.add(Restrictions.isNotNull(FECHABAJA));
                    break;
                // case CREADA:
                default:
                    criteria.add(Restrictions.isNull("fechaValidApoyo"));
                    break;
            }
        } else {
            criteria.add(Restrictions.isNull(FECHABAJA));
        }
        if (solicitudDocPreviaBusqueda.getFechaDesde() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions.sqlRestriction(
                    "TRUNC(" + campoFecha + ") >= '" + sdf.format(solicitudDocPreviaBusqueda.getFechaDesde()) + "'"));
        }
        if (solicitudDocPreviaBusqueda.getFechaHasta() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions.sqlRestriction(
                    "TRUNC(" + campoFecha + ") <= '" + sdf.format(solicitudDocPreviaBusqueda.getFechaHasta()) + "'"));
        }
        if (solicitudDocPreviaBusqueda.getUsuarioCreacion() != null) {
            criteria.add(
                    Restrictions.eq("usernameAlta", solicitudDocPreviaBusqueda.getUsuarioCreacion().getUsername()));
        }
        criteria.createAlias("solicitud.inspeccion", "inspeccion"); // inner join
        criteria.createAlias("inspeccion.tipoInspeccion", "tipoInspeccion"); // inner join
        String parametro;
        if (solicitudDocPreviaBusqueda.getNombreUnidad() != null
                && !solicitudDocPreviaBusqueda.getNombreUnidad().isEmpty()) {
            // TODO: Cambiar esta condición para que busque sin tildes/espacios por la parte de BDD
            parametro = Normalizer.normalize(solicitudDocPreviaBusqueda.getNombreUnidad(), Normalizer.Form.NFKD)
                    .replaceAll(ACENTOS, "");
            criteria.add(Restrictions.ilike("inspeccion.nombreUnidad", parametro, MatchMode.ANYWHERE));
        }
        if (solicitudDocPreviaBusqueda.getNumeroInspeccion() != null
                && !solicitudDocPreviaBusqueda.getNumeroInspeccion().isEmpty()) {
            // TODO: Cambiar esta condición para que busque sin tildes/espacios por la parte de BDD
            parametro = Normalizer.normalize(solicitudDocPreviaBusqueda.getNumeroInspeccion(), Normalizer.Form.NFKD)
                    .replaceAll(ACENTOS, "");
            criteria.add(Restrictions.ilike("inspeccion.numero", parametro, MatchMode.ANYWHERE));
        }
        if (solicitudDocPreviaBusqueda.getAmbitoInspeccion() != null) {
            criteria.add(Restrictions.eq("inspeccion.ambito", solicitudDocPreviaBusqueda.getAmbitoInspeccion()));
        }
        if (solicitudDocPreviaBusqueda.getTipoInspeccion() != null) {
            criteria.add(Restrictions.eq("tipoInspeccion.codigo",
                    solicitudDocPreviaBusqueda.getTipoInspeccion().getCodigo()));
        }
        
        criteria.createAlias("inspeccion.equipo", "equipo"); // inner join
        User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (RoleEnum.ROLE_EQUIPO_INSPECCIONES.equals(usuarioActual.getRole())) {
            DetachedCriteria subquery = DetachedCriteria.forClass(Miembro.class, "miembro");
            subquery.add(Restrictions.eq("miembro.username", usuarioActual.getUsername()));
            subquery.add(Restrictions.eqProperty("equipo.id", "miembro.equipo"));
            subquery.setProjection(Projections.property("miembro.equipo"));
            criteria.add(Property.forName("equipo.id").in(subquery));
        }
    }
    
    @Override
    @Transactional(readOnly = false)
    public boolean transaccSaveCreaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
            User usuarioProv) {
        solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
        userService.save(usuarioProv);
        return true;
    }
    
    @Override
    @Transactional(readOnly = false)
    public boolean transaccSaveElimUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
            String usuarioProv) {
        solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
        if (userService.exists(usuarioProv)) {
            userService.delete(usuarioProv);
        }
        return true;
    }
    
    @Override
    @Transactional(readOnly = false)
    public boolean transaccSaveInactivaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
            String usuarioProv) {
        solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
        userService.cambiarEstado(usuarioProv, EstadoEnum.INACTIVO);
        return true;
    }
    
    @Override
    @Transactional(readOnly = false)
    public boolean transaccSaveActivaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
            String usuarioProv) {
        solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
        userService.cambiarEstado(usuarioProv, EstadoEnum.ACTIVO);
        return true;
    }
    
    @Override
    @Transactional(readOnly = false)
    public void transaccDeleteElimDocPrevia(Long idSolicitud) {
        documentacionPreviaRepository.deleteByIdSolicitud(idSolicitud);
        solicitudDocumentacionPreviaRepository.delete(idSolicitud);
    }
    
    @Override
    public List<SolicitudDocumentacionPrevia> findFinalizadasPorInspeccion(Inspeccion inspeccion) {
        return solicitudDocumentacionPreviaRepository
                .findByFechaBajaIsNullAndFechaFinalizacionIsNotNullAndInspeccionOrderByFechaFinalizacionDesc(
                        inspeccion);
    }
    
    @Override
    public SolicitudDocumentacionPrevia findNoFinalizadaPorInspeccion(Inspeccion inspeccion) {
        return solicitudDocumentacionPreviaRepository
                .findByFechaBajaIsNullAndFechaFinalizacionIsNullAndInspeccion(inspeccion);
    }
    
    @Override
    public List<SolicitudDocumentacionPrevia> findEnviadasNoCumplimentadas() {
        return solicitudDocumentacionPreviaRepository
                .findByFechaBajaIsNullAndFechaFinalizacionIsNullAndFechaEnvioIsNotNullAndFechaCumplimentacionIsNull();
    }
    
}
