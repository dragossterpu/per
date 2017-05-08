package es.mira.progesin.persistence.repositories;

import java.util.List;
/******************
 * Repositorio de alertas y notificaciones
 * 
 * @author Ezentis
 * 
 * *****************/

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.AlertasNotificacionesUsuario;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;

/**************************************************
 * 
 * Repositorio de operaciones de base de datos para Alertas y Notificaciones de usuario. El repositorio es de tipo
 * PagingAndSortingRepository para permitir la paginación desde el servidor
 * 
 * @author Ezentis
 * 
 ****************************************************/

public interface IAlertasNotificacionesUsuarioRepository extends CrudRepository<AlertasNotificacionesUsuario, Long> {
    
    /**********************************************
     * 
     * Recupera la lista de AlertasNotificacionesUsuario asignadas al usuario pasado como parámetro
     * 
     * @param String usuario
     * @return List<AlertasNotificacionesUsuario>
     * 
     ********************************************/
    List<AlertasNotificacionesUsuario> findByUsuario(String usuario);
    
    /**********************************************
     * 
     * Recupera una notificacion/alerta asignada a un usuario y con una id pasados como parámetros
     * 
     * @param String usuario
     * @param TipoMensajeEnum tipo
     * @param Long id
     * @return AlertasNotificacionesUsuario
     * 
     ********************************************/
    
    AlertasNotificacionesUsuario findByUsuarioAndTipoAndIdMensaje(String usuario, TipoMensajeEnum tipo, Long id);
    
    /**********************************************
     * 
     * Recupera una lista AlertasNotificacionesUsuario cuyo tipo y usuario asignado se reciben como parámetro
     * 
     * @param String usuario
     * @param TipoMensajeEnum tipo
     * @return List<AlertasNotificacionesUsuario
     * 
     ********************************************/
    
    List<AlertasNotificacionesUsuario> findByUsuarioAndTipo(String usuario, TipoMensajeEnum tipo);
    
}
