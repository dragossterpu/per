package es.mira.progesin.persistence.repositories;

import java.util.List;
/**
 * Repositorio de alertas y notificaciones
 * 
 * @author Ezentis
 * 
 */

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.AlertasNotificacionesUsuario;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;

/**
 * 
 * Repositorio de operaciones de base de datos para Alertas y Notificaciones de usuario.
 * 
 * @author EZENTIS
 * 
 */

public interface IAlertasNotificacionesUsuarioRepository extends CrudRepository<AlertasNotificacionesUsuario, Long> {
    
    /**
     * 
     * Recupera la lista de AlertasNotificacionesUsuario asignadas al usuario pasado como parámetro
     * 
     * @param usuario usuario para el que se desean recuperar los mensajes
     * @return lista de mensajes (alertas o notificaciones) que tiene asiganadas el usuario
     * 
     */
    List<AlertasNotificacionesUsuario> findByUsuario(String usuario);
    
    /**
     * 
     * Recupera una notificacion/alerta asignada a un usuario y con una id pasados como parámetros
     * 
     * @param usuario usuario para el que se desean recuperar los mensajes
     * @param tipo tipo de mensaje (alerta o notificación) que se desea recuperar
     * @param id identificador del mensaje a recuperar
     * @return AlertasNotificacionesUsuario mensaje recuperado
     * 
     */
    
    AlertasNotificacionesUsuario findByUsuarioAndTipoAndIdMensaje(String usuario, TipoMensajeEnum tipo, Long id);
    
    /**
     * 
     * Recupera una lista AlertasNotificacionesUsuario cuyo tipo y usuario asignado se reciben como parámetro
     * 
     * @param usuario usuario para el que se desean recuperar los mensajes
     * @param tipo tipo de mensaje (alerta o notificación) que se desea recuperar
     * @return lista de mensajes recuperados
     * 
     */
    
    List<AlertasNotificacionesUsuario> findByUsuarioAndTipo(String usuario, TipoMensajeEnum tipo);
    
}
