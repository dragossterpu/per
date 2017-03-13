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

import es.mira.progesin.model.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.ICuestionarioEnvioRepository;
import es.mira.progesin.persistence.repositories.IDatosTablaGenericaRepository;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IUserRepository;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBusqueda;

@Service
public class CuestionarioEnvioService implements ICuestionarioEnvioService {
    
    private static final long serialVersionUID = 1L;
    
    private static final String FECHAFINALIZACION = "fechaFinalizacion";
    
    private static final String FECHAANULACION = "fechaAnulacion";
    
    private static final String ACENTOS = "\\p{InCombiningDiacriticalMarks}+";
    
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
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    @Override
    public CuestionarioEnvio findByInspeccion(Inspeccion inspeccion) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    @Transactional(readOnly = false)
    public void enviarCuestionarioService(List<User> listadoUsuariosProvisionales,
            CuestionarioEnvio cuestionarioEnvio) {
        userRepository.save(listadoUsuariosProvisionales);
        cuestionarioEnvioRepository.save(cuestionarioEnvio);
    }
    
    @Override
    public CuestionarioEnvio findNoFinalizadoPorCorreoEnvio(String correo) {
        return cuestionarioEnvioRepository.findByCorreoEnvioAndFechaFinalizacionIsNullAndFechaAnulacionIsNull(correo);
    }
    
    @Override
    public List<CuestionarioEnvio> buscarCuestionarioEnviadoCriteria(int firstResult, int maxResults,
            CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(CuestionarioEnvio.class, "cuestionario");
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
        if (cuestionarioEnviadoBusqueda.getNumeroInspeccion() != null
                && !cuestionarioEnviadoBusqueda.getNumeroInspeccion().isEmpty()) {
            // TODO: Cambiar esta condición para que busque sin tildes/espacios por la parte de BDD
            parametro = Normalizer.normalize(cuestionarioEnviadoBusqueda.getNumeroInspeccion(), Normalizer.Form.NFKD)
                    .replaceAll(ACENTOS, "");
            criteria.add(Restrictions.ilike("inspeccion.numero", parametro, MatchMode.ANYWHERE));
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
        if (RoleEnum.EQUIPO_INSPECCIONES.equals(usuarioActual.getRole())) {
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
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResults);
        criteria.addOrder(Order.desc("fechaEnvio"));
        @SuppressWarnings("unchecked")
        List<CuestionarioEnvio> listaCuestionarioEnvio = criteria.list();
        session.close();
        
        return listaCuestionarioEnvio;
    }
    
    @Override
    public long getCountCuestionarioCriteria(CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(CuestionarioEnvio.class, "cuestionario");
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
        if (cuestionarioEnviadoBusqueda.getNumeroInspeccion() != null
                && !cuestionarioEnviadoBusqueda.getNumeroInspeccion().isEmpty()) {
            // TODO: Cambiar esta condición para que busque sin tildes/espacios por la parte de BDD
            parametro = Normalizer.normalize(cuestionarioEnviadoBusqueda.getNumeroInspeccion(), Normalizer.Form.NFKD)
                    .replaceAll(ACENTOS, "");
            criteria.add(Restrictions.ilike("inspeccion.numero", parametro, MatchMode.ANYWHERE));
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
        if (RoleEnum.EQUIPO_INSPECCIONES.equals(usuarioActual.getRole())) {
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
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        session.close();
        
        return cnt;
    }
    
    @Override
    @Transactional(readOnly = false)
    public void save(CuestionarioEnvio cuestionario) {
        cuestionarioEnvioRepository.save(cuestionario);
    }
    
    @Override
    @Transactional(readOnly = false)
    public void transaccSaveConRespuestas(CuestionarioEnvio cuestionario, List<RespuestaCuestionario> listaRespuestas,
            List<DatosTablaGenerica> listaDatosTablaSave) {
        cuestionarioEnvioRepository.save(cuestionario);
        respuestaRepository.save(listaRespuestas);
        respuestaRepository.flush();
        datosTablaRepository.save(listaDatosTablaSave);
    }
    
    @Override
    @Transactional(readOnly = false)
    public boolean transaccSaveElimUsuariosProv(CuestionarioEnvio cuestionario) {
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
        return true;
    }
    
    @Override
    @Transactional(readOnly = false)
    public boolean transaccSaveConRespuestasInactivaUsuariosProv(CuestionarioEnvio cuestionario,
            List<RespuestaCuestionario> listaRespuestas, List<DatosTablaGenerica> listaDatosTablaSave) {
        cuestionarioEnvioRepository.save(cuestionario);
        respuestaRepository.save(listaRespuestas);
        respuestaRepository.flush();
        datosTablaRepository.save(listaDatosTablaSave);
        String correoPrincipal = cuestionario.getCorreoEnvio();
        String cuerpoCorreo = correoPrincipal.substring(0, correoPrincipal.indexOf('@'));
        String restoCorreo = correoPrincipal.substring(correoPrincipal.lastIndexOf('@'));
        userService.cambiarEstado(correoPrincipal, EstadoEnum.INACTIVO);
        for (int i = 1; i < 10; i++) {
            userService.cambiarEstado(cuerpoCorreo + i + restoCorreo, EstadoEnum.INACTIVO);
        }
        return true;
    }
    
    @Override
    @Transactional(readOnly = false)
    public boolean transaccSaveActivaUsuariosProv(CuestionarioEnvio cuestionario) {
        cuestionarioEnvioRepository.save(cuestionario);
        String correoPrincipal = cuestionario.getCorreoEnvio();
        String cuerpoCorreo = correoPrincipal.substring(0, correoPrincipal.indexOf('@'));
        String restoCorreo = correoPrincipal.substring(correoPrincipal.lastIndexOf('@'));
        userService.cambiarEstado(correoPrincipal, EstadoEnum.ACTIVO);
        for (int i = 1; i < 10; i++) {
            userService.cambiarEstado(cuerpoCorreo + i + restoCorreo, EstadoEnum.ACTIVO);
        }
        return true;
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
    public CuestionarioEnvio findByCuestionarioPersonalizado(CuestionarioPersonalizado cuestionario) {
        return cuestionarioEnvioRepository.findByCuestionarioPersonalizado(cuestionario);
    }
    
    @Override
    public List<CuestionarioEnvio> findNoCumplimentados() {
        return cuestionarioEnvioRepository
                .findByFechaAnulacionIsNullAndFechaFinalizacionIsNullAndFechaCumplimentacionIsNull();
    }
    
}
