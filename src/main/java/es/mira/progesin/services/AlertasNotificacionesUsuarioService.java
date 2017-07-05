package es.mira.progesin.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.AlertasNotificacionesUsuario;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
import es.mira.progesin.persistence.repositories.IAlertasNotificacionesUsuarioRepository;
import es.mira.progesin.persistence.repositories.IMiembrosRepository;

/**
 * 
 * Implementación del servicio de alertas y notificaciones de usuario.
 * 
 * @author EZENTIS
 * 
 *
 */

@Service
public class AlertasNotificacionesUsuarioService implements IAlertasNotificacionesUsuarioService {
    /**
     * Repositorio de Alertas y notificaciones.
     */
    @Autowired
    private IAlertasNotificacionesUsuarioRepository mensajeRepo;
    
    /**
     * Servicio de usuarios.
     */
    @Autowired
    private IUserService userService;
    
    /**
     * Servicio de alertas.
     */
    @Autowired
    private IAlertaService alertaService;
    
    /**
     * Servicio de notificaciones.
     */
    @Autowired
    private INotificacionService notificacionService;
    
    /**
     * Servicio de miembros.
     */
    @Autowired
    private IMiembrosRepository miembrosRepository;
    
    /**
     * 
     * Elimina un registro de base de datos. El registro se identifica por su tipo, el id y el usuario vinculado
     * 
     * @param user que tiene asignado el mensaje
     * @param id identificador del mensaje
     * @param tipo de Mensaje (Alerta o Notificacion)
     * 
     */
    @Override
    public void delete(String user, Long id, TipoMensajeEnum tipo) {
        AlertasNotificacionesUsuario men = mensajeRepo.findByUsuarioAndTipoAndIdMensaje(user, tipo, id);
        mensajeRepo.delete(men);
        
    }
    
    /**
     * 
     * Guarda un registro en base de datos.
     * 
     * @param entity Mensaje (Alerta o Notificacion) a guardar en base de datos
     * @return Mensaje (Alerta o Notificacion)
     * 
     */
    @Override
    public AlertasNotificacionesUsuario save(AlertasNotificacionesUsuario entity) {
        return mensajeRepo.save(entity);
    }
    
    /**
     * 
     * Devuelve un listado de alertas vinculadas al usuario.
     * 
     * @param user usuario para el que se busca el listado de alertas
     * @return listado de alertas asignadas al usuario
     * 
     */
    @Override
    public List<Alerta> findAlertasByUser(String user) {
        List<AlertasNotificacionesUsuario> mensajesAlerta = mensajeRepo.findByUsuarioAndTipo(user,
                TipoMensajeEnum.ALERTA);
        List<Alerta> respuesta = new ArrayList<>();
        
        for (AlertasNotificacionesUsuario alertasNotificacionesUsuario : mensajesAlerta) {
            Alerta alerta = alertaService.findOne(alertasNotificacionesUsuario.getIdMensaje());
            respuesta.add(alerta);
        }
        return respuesta;
    }
    
    /**
     * 
     * Devuelve un listado de notificaciones vinculadas al usuario.
     * 
     * @param user usuario para el que se busca el listado de notificaciones
     * @return listado de alertas asignadas al usuario
     * 
     */
    @Override
    public List<Notificacion> findNotificacionesByUser(String user) {
        List<AlertasNotificacionesUsuario> mensajesAlerta = mensajeRepo.findByUsuarioAndTipo(user,
                TipoMensajeEnum.NOTIFICACION);
        List<Notificacion> respuesta = new ArrayList<>();
        
        for (AlertasNotificacionesUsuario alertasNotificacionesUsuario : mensajesAlerta) {
            Notificacion alerta = notificacionService.findOne(alertasNotificacionesUsuario.getIdMensaje());
            respuesta.add(alerta);
        }
        return respuesta;
    }
    
    /**
     * 
     * Graba un mensaje (Alerta o Notificacion) vinculado a un usuario.
     * 
     * @param entidad Alerta o Notificación a grabar
     * @param user usuario al que se asigna el mensaje
     * 
     */
    @Override
    public void grabarMensajeUsuario(Object entidad, String user) {
        AlertasNotificacionesUsuario men = new AlertasNotificacionesUsuario();
        String nombre = entidad.getClass().getName().substring(entidad.getClass().getName().lastIndexOf('.') + 1);
        switch (nombre) {
            case "Alerta":
                men = rellenarMensaje((Alerta) entidad);
                break;
            case "Notificacion":
                men = rellenarMensaje((Notificacion) entidad);
                break;
            default:
        }
        men.setUsuario(user);
        men.setFechaAlta(new Date());
        mensajeRepo.save(men);
    }
    
    /**
     * 
     * Graba un mensaje (Alerta o Notificacion) vinculado al jefe de un equipo.
     * 
     * @param entidad Alerta o Notificación a grabar
     * @param inspeccion Se asignará el mensaje al jefe del equipo que tiene asignada esta inspección
     * 
     */
    @Override
    public void grabarMensajeJefeEquipo(Object entidad, Inspeccion inspeccion) {
        grabarMensajeUsuario(entidad, inspeccion.getEquipo().getJefeEquipo().getUsername());
    }
    
    /**
     * 
     * Graba un mensaje (Alerta o Notificacion) vinculado a todos los usuarios pertenecientes a un mismo rol.
     * 
     * @param entidad Alerta o Notificación a grabar
     * @param rol rol del los usuarios a los que se asignará el mensaje
     * 
     */
    @Override
    public void grabarMensajeRol(Object entidad, RoleEnum rol) {
        List<User> usuariosRol = userService.findByfechaBajaIsNullAndRole(rol);
        for (User user : usuariosRol) {
            grabarMensajeUsuario(entidad, user.getUsername());
        }
        
    }
    
    /**
     * 
     * Graba un mensaje (Alerta o Notificacion) vinculado a todos los usuarios pertenecientes a una lista de roles.
     * 
     * @param entidad Alerta o Notificación a grabar
     * @param roles lista de roles a los que pertenecen los usuarios a los que se asignará el mensaje
     * 
     */
    @Override
    public void grabarMensajeRol(Object entidad, List<RoleEnum> roles) {
        for (RoleEnum rol : roles) {
            grabarMensajeRol(entidad, rol);
        }
    }
    
    /**
     * Crea el mensaje con los datos de la alerta.
     * 
     * @param entidad Alerta de la que se creará el AlertasNotificacionesUsuario
     * @return AlertasNotificacionesUsuario generado.
     */
    private AlertasNotificacionesUsuario rellenarMensaje(Alerta entidad) {
        AlertasNotificacionesUsuario men = new AlertasNotificacionesUsuario();
        men.setIdMensaje(entidad.getIdAlerta());
        men.setNombreSeccion(entidad.getNombreSeccion());
        men.setTipo(TipoMensajeEnum.ALERTA);
        return men;
    }
    
    /**
     * Crea el mensaje con los datos de la notificación.
     * 
     * @param entidad Notificación de la que se creará el AlertasNotificacionesUsuario
     * @return AlertasNotificacionesUsuario generado.
     */
    private AlertasNotificacionesUsuario rellenarMensaje(Notificacion entidad) {
        AlertasNotificacionesUsuario men = new AlertasNotificacionesUsuario();
        men.setIdMensaje(entidad.getIdNotificacion());
        men.setNombreSeccion(entidad.getNombreSeccion());
        men.setTipo(TipoMensajeEnum.NOTIFICACION);
        return men;
    }
    
    /**
     * 
     * Recupera la lista de notificaciones en función de una lista contenida en una lista de
     * AlertasNotificacionesUsuario pasada como parámetro.
     * 
     * @param lista List<AlertasNotificacionesUsuario>
     * @return List<Notificacion>
     * 
     */
    @Override
    public List<Notificacion> findNotificaciones(List<AlertasNotificacionesUsuario> lista) {
        List<Notificacion> listaNotificaciones = new ArrayList<>();
        for (AlertasNotificacionesUsuario men : lista) {
            listaNotificaciones.add(notificacionService.findOne(men.getIdMensaje()));
        }
        return listaNotificaciones;
    }
    
    /**
     * 
     * Recupera la lista de alertas en función de una lista contenida en una lista de AlertasNotificacionesUsuario
     * pasada como parámetro.
     * 
     * @param lista List<AlertasNotificacionesUsuario>
     * @return List<Alerta>
     * 
     */
    @Override
    public List<Alerta> findAlertas(List<AlertasNotificacionesUsuario> lista) {
        List<Alerta> listaAlertas = new ArrayList<>();
        for (AlertasNotificacionesUsuario men : lista) {
            listaAlertas.add(alertaService.findOne(men.getIdMensaje()));
        }
        return listaAlertas;
    }
    
    /**
     * 
     * Graba un mensaje (Alerta o Notificacion) vinculado a todos los usuarios pertenecientes al equipo asignado a una
     * inspección.
     * 
     * @param entidad Alerta o Notificación
     * @param equipo al que se desea enviar el Mensaje (Alerta o Notificacion)
     * 
     */
    @Override
    public void grabarMensajeEquipo(Object entidad, Equipo equipo) {
        List<Miembro> miembrosEquipo = miembrosRepository.findByEquipo(equipo);
        for (Miembro miembro : miembrosEquipo) {
            grabarMensajeUsuario(entidad, miembro.getUsuario().getUsername());
        }
    }
    
}
