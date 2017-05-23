package es.mira.progesin.services;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IAreaUsuarioCuestEnvRepository;
import es.mira.progesin.persistence.repositories.ICuestionarioEnvioRepository;
import es.mira.progesin.persistence.repositories.IDatosTablaGenericaRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IUserRepository;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBusqueda;

/**
 * Servicio de cuestionarios enviados
 * 
 * @author EZENTIS
 */
@Service
public class CuestionarioEnvioService implements ICuestionarioEnvioService {
    
    private static final long serialVersionUID = 1L;
    
    private static final String FECHAFINALIZACION = "fechaFinalizacion";
    
    private static final String FECHAANULACION = "fechaAnulacion";
    
    private static final String ACENTOS = "\\p{InCombiningDiacriticalMarks}+";
    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private transient ICuestionarioEnvioRepository cuestionarioEnvioRepository;
    
    @Autowired
    private transient IRespuestaCuestionarioRepository respuestaRepository;
    
    @Autowired
    private transient IDatosTablaGenericaRepository datosTablaRepository;
    
    @Autowired
    private transient IUserRepository userRepository;
    
    @Autowired
    private transient IUserService userService;
    
    @Autowired
    private transient IAreaUsuarioCuestEnvRepository areaUsuarioCuestEnvRepository;
    
    @Autowired
    private transient ICorreoElectronico correoElectronico;
    
    @Autowired
    private transient IPreguntaCuestionarioRepository preguntasRepository;
    
    @Autowired
    private transient IAreaUsuarioCuestEnvService areaUsuarioCuestEnvService;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public void crearYEnviarCuestionario(List<User> listadoUsuariosProvisionales, CuestionarioEnvio cuestionarioEnvio,
            String cuerpoCorreo) {
        userRepository.save(listadoUsuariosProvisionales);
        CuestionarioEnvio cuestionarioEnviado = cuestionarioEnvioRepository.save(cuestionarioEnvio);
        List<AreaUsuarioCuestEnv> areasUsuarioCuestEnv = asignarAreasUsuarioProvPrincipal(cuestionarioEnviado,
                listadoUsuariosProvisionales.get(0));
        areaUsuarioCuestEnvRepository.save(areasUsuarioCuestEnv);
        String cuerpo = cuestionarioEnviado.getMotivoCuestionario().concat("\r\n").concat(cuerpoCorreo);
        String asunto = "Cuestionario para la inspección " + cuestionarioEnvio.getInspeccion().getId() + "/"
                + cuestionarioEnvio.getInspeccion().getAnio();
        correoElectronico.envioCorreo(cuestionarioEnvio.getCorreoEnvio(), asunto, cuerpo);
    }
    
    /**
     * Asignación por defecto de todas las áreas de un cuestionario recien creado al usuario provisional principal
     * 
     * @author Ezentis
     * @param cuestionarioEnviado
     * @param usuarioProv
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
    
    @Override
    public CuestionarioEnvio findNoFinalizadoPorCorreoEnvio(String correo) {
        return cuestionarioEnvioRepository.findByCorreoEnvioAndFechaFinalizacionIsNullAndFechaAnulacionIsNull(correo);
    }
    
    /**
     * Método que devuelve la lista de cuestionarios enviados en una consulta basada en criteria.
     * 
     * @author EZENTIS
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
     * Método que devuelve el número de cuestionarios enviados en una consulta basada en criteria
     * 
     * @param cuestionarioEnviadoBusqueda objeto con parámetros de búsqueda
     * @return devuelve el número de registros de una consulta criteria.
     * @author EZENTIS
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
     * @param cuestionarioEnviadoBusqueda
     * @param criteria
     */
    private void consultaCriteriaCuestionarioEnviado(CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda,
            Criteria criteria) {
        String campoFecha = "this_.fecha_envio";
        if (cuestionarioEnviadoBusqueda.getEstado() != null) {
            switch (cuestionarioEnviadoBusqueda.getEstado()) {
                case CUMPLIMENTADO:
                    criteria.add(Restrictions.isNotNull("fechaCumplimentacion"));
                    criteria.add(Restrictions.isNull(FECHAFINALIZACION));
                    criteria.add(Restrictions.isNull(FECHAANULACION));
                    break;
                // Aparecen como no conformes tanto si están sólo reenviadas como si están recumplimentadas
                case NO_CONFORME:
                    criteria.add(Restrictions.isNotNull("fechaNoConforme"));
                    criteria.add(Restrictions.isNull(FECHAFINALIZACION));
                    criteria.add(Restrictions.isNull(FECHAANULACION));
                    break;
                case FINALIZADO:
                    criteria.add(Restrictions.isNotNull(FECHAFINALIZACION));
                    criteria.add(Restrictions.isNull(FECHAANULACION));
                    break;
                case ANULADO:
                    criteria.add(Restrictions.isNotNull(FECHAANULACION));
                    break;
                // case ENVIADO:
                default:
                    criteria.add(Restrictions.isNull("fechaCumplimentacion"));
                    criteria.add(Restrictions.isNull(FECHAANULACION));
                    break;
            }
        } else {
            criteria.add(Restrictions.isNull(FECHAANULACION));
        }
        if (cuestionarioEnviadoBusqueda.getFechaDesde() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions.sqlRestriction(
                    "TRUNC(" + campoFecha + ") >= '" + sdf.format(cuestionarioEnviadoBusqueda.getFechaDesde()) + "'"));
        }
        if (cuestionarioEnviadoBusqueda.getFechaHasta() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions.sqlRestriction(
                    "TRUNC(" + campoFecha + ") <= '" + sdf.format(cuestionarioEnviadoBusqueda.getFechaHasta()) + "'"));
        }
        if (cuestionarioEnviadoBusqueda.getFechaLimiteRespuesta() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions.sqlRestriction("TRUNC(FECHA_LIMITE_CUESTIONARIO) <= '"
                    + sdf.format(cuestionarioEnviadoBusqueda.getFechaLimiteRespuesta()) + "'"));
        }
        
        String parametro;
        if (cuestionarioEnviadoBusqueda.getUsernameEnvio() != null
                && !cuestionarioEnviadoBusqueda.getUsernameEnvio().isEmpty()) {
            // TODO: Cambiar esta condición para que busque sin tildes/espacios por la parte de BDD
            parametro = Normalizer.normalize(cuestionarioEnviadoBusqueda.getUsernameEnvio(), Normalizer.Form.NFKD)
                    .replaceAll(ACENTOS, "");
            criteria.add(Restrictions.ilike("usernameEnvio", parametro, MatchMode.ANYWHERE));
        }
        
        criteria.createAlias("cuestionario.inspeccion", "inspeccion"); // inner join
        if (cuestionarioEnviadoBusqueda.getNombreUnidad() != null
                && !cuestionarioEnviadoBusqueda.getNombreUnidad().isEmpty()) {
            // TODO: Cambiar esta condición para que busque sin tildes/espacios por la parte de BDD
            parametro = Normalizer.normalize(cuestionarioEnviadoBusqueda.getNombreUnidad(), Normalizer.Form.NFKD)
                    .replaceAll(ACENTOS, "");
            criteria.add(Restrictions.ilike("inspeccion.nombreUnidad", parametro, MatchMode.ANYWHERE));
        }
        if (cuestionarioEnviadoBusqueda.getIdInspeccion() != null
                && !cuestionarioEnviadoBusqueda.getIdInspeccion().isEmpty()) {
            criteria.add(
                    Restrictions.eq("inspeccion.id", Long.parseLong(cuestionarioEnviadoBusqueda.getIdInspeccion())));
        }
        if (cuestionarioEnviadoBusqueda.getAnioInspeccion() != null
                && !cuestionarioEnviadoBusqueda.getAnioInspeccion().isEmpty()) {
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
        if (cuestionarioEnviadoBusqueda.getNombreEquipo() != null
                && !cuestionarioEnviadoBusqueda.getNombreEquipo().isEmpty()) {
            // TODO: Cambiar esta condición para que busque sin tildes/espacios por la parte de BDD
            parametro = Normalizer.normalize(cuestionarioEnviadoBusqueda.getNombreEquipo(), Normalizer.Form.NFKD)
                    .replaceAll(ACENTOS, "");
            criteria.add(Restrictions.ilike("equipo.nombreEquipo", parametro, MatchMode.ANYWHERE));
        }
        
        User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (RoleEnum.ROLE_EQUIPO_INSPECCIONES.equals(usuarioActual.getRole())) {
            DetachedCriteria subquery = DetachedCriteria.forClass(Miembro.class, "miembro");
            subquery.add(Restrictions.eq("miembro.username", usuarioActual.getUsername()));
            subquery.add(Restrictions.eqProperty("equipo.id", "miembro.equipo"));
            subquery.setProjection(Projections.property("miembro.equipo"));
            criteria.add(Property.forName("equipo.id").in(subquery));
        }
        
        criteria.createAlias("cuestionario.cuestionarioPersonalizado", "cuestionarioPersonalizado"); // inner join
        if (cuestionarioEnviadoBusqueda.getNombreCuestionario() != null
                && !cuestionarioEnviadoBusqueda.getNombreCuestionario().isEmpty()) {
            // TODO: Cambiar esta condición para que busque sin tildes/espacios por la parte de BDD
            parametro = Normalizer.normalize(cuestionarioEnviadoBusqueda.getNombreCuestionario(), Normalizer.Form.NFKD)
                    .replaceAll(ACENTOS, "");
            criteria.add(
                    Restrictions.ilike("cuestionarioPersonalizado.nombreCuestionario", parametro, MatchMode.ANYWHERE));
        }
        
        criteria.createAlias("cuestionarioPersonalizado.modeloCuestionario", "modeloCuestionario"); // inner join
        if (cuestionarioEnviadoBusqueda.getModeloCuestionarioSeleccionado() != null) {
            criteria.add(Restrictions.eq("modeloCuestionario.id",
                    cuestionarioEnviadoBusqueda.getModeloCuestionarioSeleccionado().getId()));
        }
        
    }
    
    @Override
    @Transactional(readOnly = false)
    public void save(CuestionarioEnvio cuestionario) {
        cuestionarioEnvioRepository.save(cuestionario);
    }
    
    @Override
    @Transactional(readOnly = false)
    public List<RespuestaCuestionario> transaccSaveConRespuestas(List<RespuestaCuestionario> listaRespuestas) {
        List<RespuestaCuestionario> listaRespuestasGuardadas = respuestaRepository.save(listaRespuestas);
        respuestaRepository.flush();
        datosTablaRepository.deleteRespuestasTablaHuerfanas();
        return listaRespuestasGuardadas;
    }
    
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
    }
    
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
    
    @Override
    @Transactional(readOnly = false)
    public void transaccSaveActivaUsuariosProv(CuestionarioEnvio cuestionario) {
        cuestionarioEnvioRepository.save(cuestionario);
        String correoPrincipal = cuestionario.getCorreoEnvio();
        userService.cambiarEstado(correoPrincipal, EstadoEnum.ACTIVO);
    }
    
    @Override
    public CuestionarioEnvio findById(Long idCuestionarioEnviado) {
        return cuestionarioEnvioRepository.findOne(idCuestionarioEnviado);
    }
    
    @Override
    public CuestionarioEnvio findNoFinalizadoPorInspeccion(Inspeccion inspeccion) {
        return cuestionarioEnvioRepository
                .findByFechaAnulacionIsNullAndFechaFinalizacionIsNullAndInspeccion(inspeccion);
    }
    
    @Override
    public boolean existsByCuestionarioPersonalizado(CuestionarioPersonalizado cuestionario) {
        return cuestionarioEnvioRepository.existsByCuestionarioPersonalizado(cuestionario);
    }
    
    @Override
    public List<CuestionarioEnvio> findNoCumplimentados() {
        return cuestionarioEnvioRepository
                .findByFechaAnulacionIsNullAndFechaFinalizacionIsNullAndFechaCumplimentacionIsNull();
    }
    
}
