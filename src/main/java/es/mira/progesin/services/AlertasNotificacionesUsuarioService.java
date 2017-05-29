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
    
    @Autowired
    private IAlertasNotificacionesUsuarioRepository mensajeRepo;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IAlertaService alertaService;
    
    @Autowired
    private INotificacionService notificacionService;
    
    @Autowired
    private IMiembrosRepository miembrosRepository;
    
    @Override
    public void delete(String user, Long id, TipoMensajeEnum tipo) {
        AlertasNotificacionesUsuario men = mensajeRepo.findByUsuarioAndTipoAndIdMensaje(user, tipo, id);
        mensajeRepo.delete(men);
        
    }
    
    @Override
    public AlertasNotificacionesUsuario save(AlertasNotificacionesUsuario entity) {
        return mensajeRepo.save(entity);
    }
    
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
    
    @Override
    public AlertasNotificacionesUsuario grabarMensajeUsuario(Object entidad, String user) {
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
        return null;
    }
    
    @Override
    public AlertasNotificacionesUsuario grabarMensajeJefeEquipo(Object entidad, Inspeccion inspeccion) {
        return grabarMensajeUsuario(entidad, inspeccion.getEquipo().getJefeEquipo());
    }
    
    @Override
    public void grabarMensajeRol(Object entidad, RoleEnum rol) {
        List<User> usuariosRol = userService.findByfechaBajaIsNullAndRole(rol);
        for (User user : usuariosRol) {
            grabarMensajeUsuario(entidad, user.getUsername());
        }
        
    }
    
    @Override
    public void grabarMensajeRol(Object entidad, List<RoleEnum> roles) {
        for (RoleEnum rol : roles) {
            grabarMensajeRol(entidad, rol);
        }
        
    }
    
    @Override
    public void grabarMensajeEquipo(Object entidad, Inspeccion inspeccion) {
        List<Miembro> miembrosEquipo = miembrosRepository.findByEquipo(inspeccion.getEquipo());
        
        for (Miembro miembro : miembrosEquipo) {
            grabarMensajeUsuario(entidad, miembro.getUsername());
        }
        
    }
    
    private AlertasNotificacionesUsuario rellenarMensaje(Alerta entidad) {
        AlertasNotificacionesUsuario men = new AlertasNotificacionesUsuario();
        men.setIdMensaje(entidad.getIdAlerta());
        men.setNombreSeccion(entidad.getNombreSeccion());
        men.setTipo(TipoMensajeEnum.ALERTA);
        return men;
    }
    
    private AlertasNotificacionesUsuario rellenarMensaje(Notificacion entidad) {
        AlertasNotificacionesUsuario men = new AlertasNotificacionesUsuario();
        men.setIdMensaje(entidad.getIdNotificacion());
        men.setNombreSeccion(entidad.getNombreSeccion());
        men.setTipo(TipoMensajeEnum.NOTIFICACION);
        return men;
    }
    
    @Override
    public List<Notificacion> findNotificaciones(List<AlertasNotificacionesUsuario> lista) {
        List<Notificacion> listaNotificaciones = new ArrayList<>();
        for (AlertasNotificacionesUsuario men : lista) {
            listaNotificaciones.add(notificacionService.findOne(men.getIdMensaje()));
        }
        return listaNotificaciones;
    }
    
    @Override
    public List<Alerta> findAlertas(List<AlertasNotificacionesUsuario> lista) {
        List<Alerta> listaAlertas = new ArrayList<>();
        for (AlertasNotificacionesUsuario men : lista) {
            listaAlertas.add(alertaService.findOne(men.getIdMensaje()));
        }
        return listaAlertas;
    }
    
    @Override
    public void grabarMensajeEquipo(Object entidad, Equipo equipo) {
        List<Miembro> miembrosEquipo = miembrosRepository.findByEquipo(equipo);
        for (Miembro miembro : miembrosEquipo) {
            grabarMensajeUsuario(entidad, miembro.getUsername());
        }
    }
    
}
