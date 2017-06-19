package es.mira.progesin.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.AreaUsuarioCuestEnv;
import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.ICuestionarioEnvioRepository;
import es.mira.progesin.persistence.repositories.IDatosTablaGenericaRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBusqueda;

/**
 * Servicio de cuestionarios enviados.
 * 
 * @author EZENTIS
 */
@Service
public class CuestionarioEnvioService implements ICuestionarioEnvioService {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Factoría de sesiones.
     */
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * Repositorio de cuestionarios enviados.
     */
    @Autowired
    private transient ICuestionarioEnvioRepository cuestionarioEnvioRepository;
    
    /**
     * Repositorio de respuestas.
     */
    @Autowired
    private transient IRespuestaCuestionarioRepository respuestaRepository;
    
    /**
     * Repositorio de respuestas tipo tabla/matriz.
     */
    @Autowired
    private transient IDatosTablaGenericaRepository datosTablaRepository;
    
    /**
     * Servicio de usuarios.
     */
    @Autowired
    private transient IUserService userService;
    
    /**
     * Servicio de inspecciones.
     */
    @Autowired
    private transient IInspeccionesService inspeccionesService;
    
    /**
     * Interfaz para el envío de correos.
     */
    @Autowired
    private transient ICorreoElectronico correoElectronico;
    
    /**
     * Repositorio de preguntas de un cuestionario.
     */
    @Autowired
    private transient IPreguntaCuestionarioRepository preguntasRepository;
    
    /**
     * Servicio de areas asignadas a un usuario de cuestionario enviado.
     */
    @Autowired
    private transient IAreaUsuarioCuestEnvService areaUsuarioCuestEnvService;
    
    /**
     * Crea y envía un cuestionario a partir de un modelo personalizado, genera los usuarios provisionales que lo
     * responderán y envía un correo al destinatario.
     * 
     * @param listadoUsuariosProvisionales remitentes del cuestionario
     * @param cuestionarioEnvio enviado
     * @param cuerpoCorreo correo electrónico de los remitentes
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public void crearYEnviarCuestionario(List<User> listadoUsuariosProvisionales, CuestionarioEnvio cuestionarioEnvio,
            String cuerpoCorreo) {
        userService.save(listadoUsuariosProvisionales);
        CuestionarioEnvio cuestionarioEnviado = cuestionarioEnvioRepository.save(cuestionarioEnvio);
        List<AreaUsuarioCuestEnv> areasUsuarioCuestEnv = asignarAreasUsuarioProvPrincipal(cuestionarioEnviado,
                listadoUsuariosProvisionales.get(0));
        areaUsuarioCuestEnvService.save(areasUsuarioCuestEnv);
        String cuerpo = cuestionarioEnviado.getMotivoCuestionario().concat("\r\n").concat(cuerpoCorreo);
        String asunto = "Cuestionario para la inspección " + cuestionarioEnvio.getInspeccion().getNumero();
        correoElectronico.envioCorreo(cuestionarioEnvio.getCorreoEnvio(), asunto, cuerpo);
        
        inspeccionesService.cambiarEstado(cuestionarioEnvio.getInspeccion(),
                EstadoInspeccionEnum.F_PEND_RECIBIR_CUESTIONARIO);
    }
    
    /**
     * Asignación por defecto de todas las áreas de un cuestionario recién creado al usuario provisional principal.
     * 
     * @param cuestionarioEnviado cuestionario enviado
     * @param usuarioProv usuario provisional principal al que se le asignan todas las áreas
     * @return lista de asignaciones
     */
    private List<AreaUsuarioCuestEnv> asignarAreasUsuarioProvPrincipal(CuestionarioEnvio cuestionarioEnviado,
            User usuarioProv) {
        List<AreaUsuarioCuestEnv> areasUsuarioCuestEnv = new ArrayList<>();
        List<PreguntasCuestionario> preguntasElegidas = preguntasRepository
                .findPreguntasElegidasCuestionarioPersonalizado(
                        cuestionarioEnviado.getCuestionarioPersonalizado().getId());
        List<AreasCuestionario> areasElegidas = new ArrayList<>();
        preguntasElegidas.forEach(pregunta -> {
            if (areasElegidas.contains(pregunta.getArea()) == Boolean.FALSE) {
                areasElegidas.add(pregunta.getArea());
                areasUsuarioCuestEnv.add(new AreaUsuarioCuestEnv(cuestionarioEnviado.getId(),
                        pregunta.getArea().getId(), usuarioProv.getUsername()));
            }
        });
        return areasUsuarioCuestEnv;
    }
    
    /**
     * Recupera el cuestionario enviado no finalizado perteneciente a un destinatario (no puede haber más de uno).
     * 
     * @param correo electrónico del remitente
     * @return cuestionario a enviar
     */
    @Override
    public CuestionarioEnvio findNoFinalizadoPorCorreoEnvio(String correo) {
        return cuestionarioEnvioRepository.findByCorreoEnvioAndFechaFinalizacionIsNullAndFechaAnulacionIsNull(correo);
    }
    
    /**
     * Método que devuelve la lista de cuestionarios enviados en una consulta basada en criteria.
     * 
     * @param cuestionarioEnviadoBusqueda objeto con los criterios de búsqueda
     * @param first primer elemento
     * @param pageSize tamaño de cada página de resultados
     * @param sortField campo por el que se ordenan los resultados
     * @param sortOrder sentido de la ordenacion (ascendente/descendente)
     * @return la lista de cuestionarios enviados.
     */
    @Override
    public List<CuestionarioEnvio> buscarCuestionarioEnviadoCriteria(int first, int pageSize, String sortField,
            SortOrder sortOrder, CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(CuestionarioEnvio.class, "cuestionario");
        consultaCriteriaCuestionarioEnviado(cuestionarioEnviadoBusqueda, criteria);
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
        List<CuestionarioEnvio> listaCuestionarioEnvio = criteria.list();
        session.close();
        
        return listaCuestionarioEnvio;
    }
    
    /**
     * Método que devuelve el número de cuestionarios enviados en una consulta basada en criteria.
     * 
     * @param cuestionarioEnviadoBusqueda objeto con parámetros de búsqueda
     * @return devuelve el número de registros de una consulta criteria.
     */
    @Override
    public int getCountCuestionarioCriteria(CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(CuestionarioEnvio.class, "cuestionario");
        consultaCriteriaCuestionarioEnviado(cuestionarioEnviadoBusqueda, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
    /**
     * Consulta de cuestionarios basada en criteria.
     * 
     * @param cuestionarioEnviadoBusqueda parametros de búsqueda
     * @param criteria consulta con los párametros
     */
    private void consultaCriteriaCuestionarioEnviado(CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda,
            Criteria criteria) {
        if (cuestionarioEnviadoBusqueda.getEstado() != null) {
            switch (cuestionarioEnviadoBusqueda.getEstado()) {
                case CUMPLIMENTADO:
                    criteria.add(Restrictions.isNotNull("fechaCumplimentacion"));
                    criteria.add(Restrictions.isNull(Constantes.FECHAFINALIZACION));
                    criteria.add(Restrictions.isNull(Constantes.FECHAANULACION));
                    break;
                // Aparecen como no conformes tanto si están sólo reenviadas como si están recumplimentadas
                case NO_CONFORME:
                    criteria.add(Restrictions.isNotNull("fechaNoConforme"));
                    criteria.add(Restrictions.isNull(Constantes.FECHAFINALIZACION));
                    criteria.add(Restrictions.isNull(Constantes.FECHAANULACION));
                    break;
                case FINALIZADO:
                    criteria.add(Restrictions.isNotNull(Constantes.FECHAFINALIZACION));
                    criteria.add(Restrictions.isNull(Constantes.FECHAANULACION));
                    break;
                case ANULADO:
                    criteria.add(Restrictions.isNotNull(Constantes.FECHAANULACION));
                    break;
                // case ENVIADO:
                default:
                    criteria.add(Restrictions.isNull("fechaCumplimentacion"));
                    criteria.add(Restrictions.isNull(Constantes.FECHAANULACION));
                    break;
            }
        } else {
            criteria.add(Restrictions.isNull(Constantes.FECHAANULACION));
        }
        if (cuestionarioEnviadoBusqueda.getFechaDesde() != null) {
            criteria.add(Restrictions.ge("fechaEnvio", cuestionarioEnviadoBusqueda.getFechaDesde()));
        }
        if (cuestionarioEnviadoBusqueda.getFechaHasta() != null) {
            Date fechaHasta = new Date(
                    cuestionarioEnviadoBusqueda.getFechaHasta().getTime() + TimeUnit.DAYS.toMillis(1));
            criteria.add(Restrictions.le("fechaEnvio", fechaHasta));
        }
        if (cuestionarioEnviadoBusqueda.getFechaLimiteRespuesta() != null) {
            Date fechaLimiteRespuesta = new Date(
                    cuestionarioEnviadoBusqueda.getFechaLimiteRespuesta().getTime() + TimeUnit.DAYS.toMillis(1));
            criteria.add(Restrictions.le("fechaLimiteCuestionario", fechaLimiteRespuesta));
        }
        if (cuestionarioEnviadoBusqueda.getUsernameEnvio() != null) {
            criteria.add(Restrictions.ilike("usernameEnvio", cuestionarioEnviadoBusqueda.getUsernameEnvio(),
                    MatchMode.ANYWHERE));
        }
        criteria.createAlias("cuestionario.inspeccion", "inspeccion"); // inner join
        if (cuestionarioEnviadoBusqueda.getNombreUnidad() != null) {
            criteria.add(Restrictions.ilike("inspeccion.nombreUnidad", cuestionarioEnviadoBusqueda.getNombreUnidad(),
                    MatchMode.ANYWHERE));
        }
        if (cuestionarioEnviadoBusqueda.getIdInspeccion() != null) {
            criteria.add(
                    Restrictions.eq("inspeccion.id", Long.parseLong(cuestionarioEnviadoBusqueda.getIdInspeccion())));
        }
        if (cuestionarioEnviadoBusqueda.getAnioInspeccion() != null) {
            criteria.add(Restrictions.eq("inspeccion.anio",
                    Integer.parseInt(cuestionarioEnviadoBusqueda.getAnioInspeccion())));
        }
        if (cuestionarioEnviadoBusqueda.getAmbitoInspeccion() != null) {
            criteria.add(Restrictions.eq("inspeccion.ambito", cuestionarioEnviadoBusqueda.getAmbitoInspeccion()));
        }
        criteria.createAlias("inspeccion.tipoInspeccion", "tipoInspeccion"); // inner join
        if (cuestionarioEnviadoBusqueda.getTipoInspeccion() != null) {
            criteria.add(Restrictions.eq("tipoInspeccion.codigo",
                    cuestionarioEnviadoBusqueda.getTipoInspeccion().getCodigo()));
        }
        criteria.createAlias("inspeccion.equipo", "equipo"); // inner join
        if (cuestionarioEnviadoBusqueda.getNombreEquipo() != null) {
            criteria.add(Restrictions.ilike("equipo.nombreEquipo", cuestionarioEnviadoBusqueda.getNombreEquipo(),
                    MatchMode.ANYWHERE));
        }
        User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (RoleEnum.ROLE_EQUIPO_INSPECCIONES.equals(usuarioActual.getRole())) {
            DetachedCriteria subquery = DetachedCriteria.forClass(Miembro.class, "miembro");
            subquery.add(Restrictions.eq("miembro.usuario", usuarioActual));
            subquery.add(Restrictions.eqProperty("equipo.id", "miembro.equipo"));
            subquery.setProjection(Projections.property("miembro.equipo"));
            criteria.add(Property.forName("equipo.id").in(subquery));
        }
        criteria.createAlias("cuestionario.cuestionarioPersonalizado", "cuestionarioPersonalizado"); // inner join
        if (cuestionarioEnviadoBusqueda.getNombreCuestionario() != null) {
            criteria.add(Restrictions.ilike("cuestionarioPersonalizado.nombreCuestionario",
                    cuestionarioEnviadoBusqueda.getNombreCuestionario(), MatchMode.ANYWHERE));
        }
        criteria.createAlias("cuestionarioPersonalizado.modeloCuestionario", "modeloCuestionario"); // inner join
        if (cuestionarioEnviadoBusqueda.getModeloCuestionarioSeleccionado() != null) {
            criteria.add(Restrictions.eq("modeloCuestionario.id",
                    cuestionarioEnviadoBusqueda.getModeloCuestionarioSeleccionado().getId()));
        }
    }
    
    /**
     * Guarda la información de un cuestionario enviado en la bdd.
     * 
     * @param cuestionario a enviar
     */
    @Override
    @Transactional(readOnly = false)
    public void save(CuestionarioEnvio cuestionario) {
        cuestionarioEnvioRepository.save(cuestionario);
    }
    
    /**
     * Transacción que guarda los datos de las respuestas de un cuestionario enviado.
     * 
     * @param listaRespuestas para un cuestionario
     * @return lista de respuestas guardadas con id
     */
    @Override
    @Transactional(readOnly = false)
    public List<RespuestaCuestionario> transaccSaveConRespuestas(List<RespuestaCuestionario> listaRespuestas) {
        List<RespuestaCuestionario> listaRespuestasGuardadas = respuestaRepository.save(listaRespuestas);
        respuestaRepository.flush();
        datosTablaRepository.deleteRespuestasTablaHuerfanas();
        return listaRespuestasGuardadas;
    }
    
    /**
     * Guarda los datos de un cuestionario enviado y elimina los usuarios provisionales que lo han cumplimentado una vez
     * finalizado o anulado.
     * 
     * @param cuestionario enviado
     */
    @Override
    @Transactional(readOnly = false)
    public void transaccSaveElimUsuariosProv(CuestionarioEnvio cuestionario) {
        cuestionarioEnvioRepository.save(cuestionario);
        String correoPrincipal = cuestionario.getCorreoEnvio();
        String cuerpoCorreo = correoPrincipal.substring(0, correoPrincipal.indexOf('@'));
        String restoCorreo = correoPrincipal.substring(correoPrincipal.lastIndexOf('@'));
        if (userService.exists(correoPrincipal)) {
            userService.delete(correoPrincipal);
        }
        for (int i = 1; i < 10; i++) {
            if (userService.exists(cuerpoCorreo + i + restoCorreo)) {
                userService.delete(cuerpoCorreo + i + restoCorreo);
            }
        }
        areaUsuarioCuestEnvService.deleteByIdCuestionarioEnviado(cuestionario.getId());
        
        inspeccionesService.cambiarEstado(cuestionario.getInspeccion(),
                EstadoInspeccionEnum.G_PENDIENTE_VISITA_INSPECCION);
    }
    
    /**
     * Guarda los datos de un cuestionario enviado y sus respuestas, e inactiva los usuarios provisionales que lo han
     * cumplimentado una vez finalizado o anulado.
     * 
     * @param cuestionario enviado
     * @param listaRespuestas de un cuestionario
     */
    @Override
    @Transactional(readOnly = false)
    public void transaccSaveConRespuestasInactivaUsuariosProv(CuestionarioEnvio cuestionario,
            List<RespuestaCuestionario> listaRespuestas) {
        respuestaRepository.save(listaRespuestas);
        respuestaRepository.flush();
        datosTablaRepository.deleteRespuestasTablaHuerfanas();
        String correoPrincipal = cuestionario.getCorreoEnvio();
        userService.cambiarEstado(correoPrincipal, EstadoEnum.INACTIVO);
        cuestionarioEnvioRepository.save(cuestionario);
    }
    
    /**
     * Guarda los datos de un cuestionario enviado y activa los usuarios provisionales que debe cumplimentarlo de nuevo
     * en caso de no conformidad.
     * 
     * @param cuestionario enviado
     */
    @Override
    @Transactional(readOnly = false)
    public void transaccSaveActivaUsuariosProv(CuestionarioEnvio cuestionario) {
        cuestionarioEnvioRepository.save(cuestionario);
        String correoPrincipal = cuestionario.getCorreoEnvio();
        userService.cambiarEstado(correoPrincipal, EstadoEnum.ACTIVO);
    }
    
    /**
     * Recupera un cuestionario enviado a partir de su identificador.
     * 
     * @param idCuestionarioEnviado identificador del cuestionario
     * @return cuestionario enviado objeto
     */
    @Override
    public CuestionarioEnvio findById(Long idCuestionarioEnviado) {
        return cuestionarioEnvioRepository.findOne(idCuestionarioEnviado);
    }
    
    /**
     * Recupera el cuestionario enviado no finalizado y no anulado perteneciente a una inspección (no puede haber más de
     * uno).
     * 
     * @param inspeccion inspección a la que pertenece el cuestionario
     * @return cuestionario enviado
     */
    @Override
    public CuestionarioEnvio findNoFinalizadoPorInspeccion(Inspeccion inspeccion) {
        return cuestionarioEnvioRepository
                .findByFechaAnulacionIsNullAndFechaFinalizacionIsNullAndInspeccion(inspeccion);
    }
    
    /**
     * Comprueba si existe algún cuestionario enviado asociado a un modelo de cuestionario personalizado.
     * 
     * @param cuestionario personalizado
     * @return boolean valor booleano
     */
    @Override
    public boolean existsByCuestionarioPersonalizado(CuestionarioPersonalizado cuestionario) {
        return cuestionarioEnvioRepository.existsByCuestionarioPersonalizado(cuestionario);
    }
    
    /**
     * Recupera los cuestionarios enviados que aún no han sido cumplimentados.
     * 
     * @return lista de cuestionarios enviados no cumplimentados
     */
    @Override
    public List<CuestionarioEnvio> findNoCumplimentados() {
        return cuestionarioEnvioRepository
                .findByFechaAnulacionIsNullAndFechaFinalizacionIsNullAndFechaCumplimentacionIsNull();
    }
    
}
