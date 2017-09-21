package es.mira.progesin.persistence.repositories;

import java.util.List;
/**
 * Repositorio de alertas y notificaciones
 * 
 * @author EZENTIS
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
     * Recupera la lista de AlertasNotificacionesUsuario asignadas al usuario pasado como parámetro.
     * 
     * @param usuario Usuario para el que se desean recuperar los mensajes
     * @return Lista de mensajes (alertas o notificaciones) que tiene asiganadas el usuario
     * 
     */
    List<AlertasNotificacionesUsuario> findByUsuario(String usuario);
    
    /**
     * 
     * Recupera una notificacion/alerta asignada a un usuario y con una id pasados como parámetros.
     * 
     * @param usuario Usuario para el que se desean recuperar los mensajes
     * @param tipo Tipo de mensaje (alerta o notificación) que se desea recuperar
     * @param id Identificador del mensaje a recuperar
     * @return Mensaje recuperado
     * 
     */
    
    AlertasNotificacionesUsuario findByUsuarioAndTipoAndIdMensaje(String usuario, TipoMensajeEnum tipo, Long id);
    
    /**
     * 
     * Recupera una lista AlertasNotificacionesUsuario cuyo tipo y usuario asignado se reciben como parámetro.
     * 
     * @param usuario Usuario para el que se desean recuperar los mensajes
     * @param tipo Tipo de mensaje (alerta o notificación) que se desea recuperar
     * @return Lista de mensajes recuperados
     * 
     */
    
    List<AlertasNotificacionesUsuario> findByUsuarioAndTipoOrderByFechaAltaDesc(String usuario, TipoMensajeEnum tipo);
    
}
