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
import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.persistence.repositories.IDocumentacionPreviaRepository;
import es.mira.progesin.persistence.repositories.ISolicitudDocumentacionPreviaRepository;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.web.beans.SolicitudDocPreviaBusqueda;

/**
 * Servicio de solicitudes de documentación.
 * 
 * @author EZENTIS
 */
@Service
public class SolicitudDocumentacionService implements ISolicitudDocumentacionService {
    
    /**
     * Factoría de sesiones.
     */
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * Repositorio de solicitudes.
     */
    @Autowired
    private ISolicitudDocumentacionPreviaRepository solicitudDocumentacionPreviaRepository;
    
    /**
     * Servicio de usuarios.
     */
    @Autowired
    private IUserService userService;
    
    /**
     * Servicio de documentos.
     */
    @Autowired
    private IDocumentoService documentoService;
    
    /**
     * Servicio de inspecciones.
     */
    @Autowired
    private IInspeccionesService inspeccionesService;
    
    /**
     * Servicio de tipos de documentación asociados a la solicitud.
     */
    @Autowired
    private IDocumentacionPreviaRepository documentacionPreviaRepository;
    
    /**
     * Servicio de tipos de documentación.
     */
    @Autowired
    private ITipoDocumentacionService tipoDocumentacionService;
    
    /**
     * Servicio para usar los métodos usados junto con criteria.
     */
    @Autowired
    private ICriteriaService criteriaService;
    
    /**
     * Guarda la información de una solicitud en la bdd.
     * 
     * @param solicitudDocumentacionPrevia solicitud creada o modificada
     * @return solicitud
     */
    @Override
    @Transactional(readOnly = false)
    public SolicitudDocumentacionPrevia save(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia) {
        return solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
    }
    
    /**
     * Recupera todas las solicitudes existentes.
     * 
     * @return lista
     */
    @Override
    public List<SolicitudDocumentacionPrevia> findAll() {
        return (List<SolicitudDocumentacionPrevia>) solicitudDocumentacionPreviaRepository.findAll();
    }
    
    /**
     * Recupera la solicitud no finalizada perteneciente a un destinatario (no puede haber más de una).
     * 
     * @param correo destinatario de la solicitud
     * @return solicitud
     */
    @Override
    public SolicitudDocumentacionPrevia findNoFinalizadaPorCorreoDestinatario(String correo) {
        return solicitudDocumentacionPreviaRepository
                .findByFechaBajaIsNullAndFechaFinalizacionIsNullAndCorreoDestinatarioIgnoreCase(correo);
    }
    
    /**
     * Recupera la solicitud ya enviada pero sin finalizar perteneciente a un destinatario (no puede haber más de una).
     * 
     * @param correo destinatario de la solicitud
     * @return solicitud
     */
    @Override
    public SolicitudDocumentacionPrevia findEnviadaNoFinalizadaPorCorreoDestinatario(String correo) {
        return solicitudDocumentacionPreviaRepository
                .findByFechaBajaIsNullAndFechaFinalizacionIsNullAndFechaEnvioIsNotNullAndCorreoDestinatarioIgnoreCase(
                        correo);
    }
    
    /**
     * Método que devuelve la lista de solicitudes previas en una consulta basada en criteria.
     * 
     * @param solicitudDocPreviaBusqueda objeto con los criterios de búsqueda
     * @param first primer elemento
     * @param pageSize tamaño de cada página de resultados
     * @param sortField campo por el que se ordenan los resultados
     * @param sortOrder sentido de la ordenacion (ascendente/descendente)
     * @return la lista de solicitudes.
     */
    @Override
    public List<SolicitudDocumentacionPrevia> buscarSolicitudDocPreviaCriteria(int first, int pageSize,
            String sortField, SortOrder sortOrder, SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(SolicitudDocumentacionPrevia.class, "solicitud");
        consultaCriteriaSolicitudesDoc(solicitudDocPreviaBusqueda, criteria);
        
        criteriaService.prepararPaginacionOrdenCriteria(criteria, first, pageSize, sortField, sortOrder, "id");
        
        @SuppressWarnings("unchecked")
        List<SolicitudDocumentacionPrevia> listaSolicitudesDocPrevia = criteria.list();
        session.close();
        
        return listaSolicitudesDocPrevia;
        
    }
    
    /**
     * Método que devuelve el número de solicitudes previas totales en una consulta basada en criteria.
     * 
     * @param solicitudDocPreviaBusqueda objeto con los criterios de búsqueda
     * @return número de registros
     */
    @Override
    public int getCountSolicitudDocPreviaCriteria(SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(SolicitudDocumentacionPrevia.class, "solicitud");
        consultaCriteriaSolicitudesDoc(solicitudDocPreviaBusqueda, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        session.close();
        
        return Math.toIntExact(cnt);
        
    }
    
    /**
     * Construye la consulta criteria dependiendo de los valores de un bean de tipo SolicitudDocPreviaBusqueda.
     * 
     * @param solicitudDocPreviaBusqueda objeto con los parámetros de búsqueda
     * @param criteria consulta con las restricciones de búsqueda
     */
    private void consultaCriteriaSolicitudesDoc(SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda,
            Criteria criteria) {
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
                    criteria.add(Restrictions.isNull(Constantes.FECHABAJA));
                    break;
                case CUMPLIMENTADA:
                    criteria.add(Restrictions.isNotNull("fechaCumplimentacion"));
                    criteria.add(Restrictions.isNull(Constantes.FECHAFINALIZACION));
                    criteria.add(Restrictions.isNull(Constantes.FECHABAJA));
                    break;
                // Aparecen como no conformes tanto si están sólo reenviadas como si están recumplimentadas
                case NO_CONFORME:
                    criteria.add(Restrictions.isNotNull("fechaNoConforme"));
                    criteria.add(Restrictions.isNull(Constantes.FECHAFINALIZACION));
                    criteria.add(Restrictions.isNull(Constantes.FECHABAJA));
                    break;
                case FINALIZADA:
                    criteria.add(Restrictions.isNotNull(Constantes.FECHAFINALIZACION));
                    criteria.add(Restrictions.isNull(Constantes.FECHABAJA));
                    break;
                case ANULADA:
                    criteria.add(Restrictions.isNotNull(Constantes.FECHABAJA));
                    break;
                // case CREADA:
                default:
                    criteria.add(Restrictions.isNull("fechaValidApoyo"));
                    break;
            }
        } else {
            criteria.add(Restrictions.isNull(Constantes.FECHABAJA));
        }
        if (solicitudDocPreviaBusqueda.getFechaDesde() != null) {
            criteria.add(Restrictions.ge(Constantes.FECHAALTA, solicitudDocPreviaBusqueda.getFechaDesde()));
        }
        if (solicitudDocPreviaBusqueda.getFechaHasta() != null) {
            Date fechaHasta = new Date(
                    solicitudDocPreviaBusqueda.getFechaHasta().getTime() + TimeUnit.DAYS.toMillis(1));
            criteria.add(Restrictions.le(Constantes.FECHAALTA, fechaHasta));
        }
        if (solicitudDocPreviaBusqueda.getUsuarioCreacion() != null) {
            criteria.add(Restrictions.ilike("usernameAlta", solicitudDocPreviaBusqueda.getUsuarioCreacion(),
                    MatchMode.ANYWHERE));
        }
        criteria.createAlias("solicitud.inspeccion", "inspeccion"); // inner join
        criteria.createAlias("inspeccion.tipoInspeccion", "tipoInspeccion"); // inner join
        if (solicitudDocPreviaBusqueda.getNombreUnidad() != null) {
            criteria.add(Restrictions.ilike("inspeccion.nombreUnidad", solicitudDocPreviaBusqueda.getNombreUnidad(),
                    MatchMode.ANYWHERE));
        }
        if (solicitudDocPreviaBusqueda.getIdInspeccion() != null) {
            criteria.add(
                    Restrictions.eq("inspeccion.id", Long.parseLong(solicitudDocPreviaBusqueda.getIdInspeccion())));
        }
        if (solicitudDocPreviaBusqueda.getAnioInspeccion() != null) {
            criteria.add(Restrictions.eq("inspeccion.anio",
                    Integer.parseInt(solicitudDocPreviaBusqueda.getAnioInspeccion())));
        }
        if (solicitudDocPreviaBusqueda.getAmbitoInspeccion() != null) {
            criteria.add(Restrictions.eq("inspeccion.ambito", solicitudDocPreviaBusqueda.getAmbitoInspeccion()));
        }
        if (solicitudDocPreviaBusqueda.getTipoInspeccion() != null) {
            criteria.add(Restrictions.eq("tipoInspeccion.codigo",
                    solicitudDocPreviaBusqueda.getTipoInspeccion().getCodigo()));
        }
        
        criteria.createAlias("inspeccion.equipo", "equipo"); // inner join
        criteriaService.setCriteriaEquipo(criteria);
        
    }
    
    /**
     * Guarda los datos de una solicitud y crea el usuario provisional que debe cumplimentarla una vez enviada.
     * 
     * @param solicitudDocumentacionPrevia solicitud modificada
     * @param usuarioProv objeto usuario
     */
    @Override
    @Transactional(readOnly = false)
    public void transaccSaveCreaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
            User usuarioProv) {
        solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
        userService.save(usuarioProv);
        inspeccionesService.cambiarEstado(solicitudDocumentacionPrevia.getInspeccion(),
                EstadoInspeccionEnum.D_PEND_RECIBIR_DOC_PREVIA);
    }
    
    /**
     * Guarda los datos de una solicitud y elimina el usuario provisional que la ha cumplimentado una vez finalizada o
     * anulada.
     * 
     * @param solicitudDocumentacionPrevia solicitud modificada
     * @param usuarioProv nombre usuario
     */
    @Override
    @Transactional(readOnly = false)
    public void transaccSaveElimUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
            String usuarioProv) {
        solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
        if (userService.exists(usuarioProv)) {
            userService.delete(usuarioProv);
        }
        inspeccionesService.cambiarEstado(solicitudDocumentacionPrevia.getInspeccion(),
                EstadoInspeccionEnum.E_PEND_ENVIAR_CUESTIONARIO);
    }
    
    /**
     * Guarda los datos de una solicitud e inactiva el usuario provisional que la ha cumplimentado.
     * 
     * @param solicitudDocumentacionPrevia solicitud modificada
     * @param usuarioProv nombre usuario
     */
    @Override
    @Transactional(readOnly = false)
    public void transaccSaveInactivaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
            String usuarioProv) {
        solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
        userService.cambiarEstado(usuarioProv, EstadoEnum.INACTIVO);
    }
    
    /**
     * Guarda los datos de una solicitud y activa el usuario provisional que debe cumplimentarla de nuevo en caso de no
     * conformidad.
     * 
     * @param solicitudDocumentacionPrevia solicitud modificada
     * @param usuarioProv nombre usuario
     */
    @Override
    @Transactional(readOnly = false)
    public void transaccSaveActivaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
            String usuarioProv) {
        solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
        userService.cambiarEstado(usuarioProv, EstadoEnum.ACTIVO);
    }
    
    /**
     * Elimina una solicitud y todos los documentos subidos por el usuario asociados a la misma.
     * 
     * @param idSolicitud clave de la solicitud
     */
    @Override
    @Transactional(readOnly = false)
    public void transaccDeleteElimDocPrevia(Long idSolicitud) {
        documentacionPreviaRepository.deleteByIdSolicitud(idSolicitud);
        solicitudDocumentacionPreviaRepository.delete(idSolicitud);
    }
    
    /**
     * Recupera las solicitudes ya finalizadas asociadas a una inspección.
     * 
     * @param inspeccion inspección de la solicitud
     * @return lista
     */
    @Override
    public List<SolicitudDocumentacionPrevia> findFinalizadasPorInspeccion(Inspeccion inspeccion) {
        return solicitudDocumentacionPreviaRepository
                .findByFechaBajaIsNullAndFechaFinalizacionIsNotNullAndInspeccionOrderByFechaFinalizacionDesc(
                        inspeccion);
    }
    
    /**
     * Recupera la solicitud no finalizada asociada a una inspección (no puede haber más de una).
     * 
     * @param inspeccion inspección de la solicitud
     * @return lista
     */
    @Override
    public SolicitudDocumentacionPrevia findNoFinalizadaPorInspeccion(Inspeccion inspeccion) {
        return solicitudDocumentacionPreviaRepository
                .findByFechaBajaIsNullAndFechaFinalizacionIsNullAndInspeccion(inspeccion);
    }
    
    /**
     * Recupera las solicitudes enviadas pero aún no cumplimentadas.
     * 
     * @return lista
     */
    @Override
    public List<SolicitudDocumentacionPrevia> findEnviadasNoCumplimentadas() {
        return solicitudDocumentacionPreviaRepository
                .findByFechaBajaIsNullAndFechaFinalizacionIsNullAndFechaEnvioIsNotNullAndFechaCumplimentacionIsNull();
    }
    
    /**
     * Crea una solicitud de documentación y da de alta los documentos seleccionados. Colección de documentos de entre
     * los disponibles en TipoDocumentación que se asignan a la solicitud.
     * 
     * @param solicitudDocumentacionPrevia creada
     * @param documentosSeleccionados asociados a la solicitud
     */
    @Override
    @Transactional(readOnly = false)
    public void transaccSaveAltaDocumentos(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
            List<TipoDocumentacion> documentosSeleccionados) {
        solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
        documentosSeleccionados.forEach(tipodocumento -> {
            DocumentacionPrevia docPrevia = new DocumentacionPrevia();
            docPrevia.setIdSolicitud(solicitudDocumentacionPrevia.getId());
            docPrevia.setDescripcion(tipodocumento.getDescripcion());
            docPrevia.setExtensiones(tipodocumento.getExtensiones());
            docPrevia.setNombre(tipodocumento.getNombre());
            tipoDocumentacionService.save(docPrevia);
        });
        
    }
    
    /**
     * Recupera una solicitud y los documentos subidos al cumplimentarla a partir de su identificador.
     * 
     * @param id clave de la solicitud
     * @return solicitud con documentos cargados
     */
    @Override
    public SolicitudDocumentacionPrevia findByIdConDocumentos(Long id) {
        return solicitudDocumentacionPreviaRepository.findById(id);
    }
    
    /**
     * Elimina de BBDD el documento de una solicitud pasados como parámetros.
     * 
     * @param solicitud solicitud a eliminar
     * @param documento documento a eliminar
     */
    @Override
    @Transactional(readOnly = false)
    public SolicitudDocumentacionPrevia eliminarDocumentoSolicitud(SolicitudDocumentacionPrevia solicitud,
            Documento documento) {
        solicitud.getDocumentos().remove(documento);
        SolicitudDocumentacionPrevia solicitudSincronizada = solicitudDocumentacionPreviaRepository.save(solicitud);
        documentoService.delete(documento);
        return solicitudSincronizada;
    }
    
    /**
     * Devuelve una solicitud identificada por su id.
     * 
     * @param id Identificador de la solicitud
     * @return Solicitud
     */
    @Override
    public SolicitudDocumentacionPrevia findOne(Long id) {
        return solicitudDocumentacionPreviaRepository.findOne(id);
    }
}
