package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.enums.RoleEnum;

/**
 * 
 * Interfaz del servicio de Notificaciones.
 * 
 * @author EZENTIS
 * 
 * @see NotificacionService
 * 
 */
public interface INotificacionService {
    
    /**
     * 
     * Recupera de la base de datos una notificación cuya id se pasa como parámetro.
     * 
     * @param id Identificador de la notificación a buscar
     * @return Notificación que corresponde al id recibido
     * 
     */
    Notificacion findOne(Long id);
    
    /**
     * 
     * Crea una notificación a partir de una descripción y una sección recibidas como parámetro y la asigna a un usuario
     * pasado como parámetro.
     * 
     * @param descripcion Descripción de la notificación
     * @param seccion Sección sobre la que se hace la notificación
     * @param usuario Usuario al que se le dirige la notificación
     * 
     */
    
    void crearNotificacionUsuario(String descripcion, String seccion, String usuario);
    
    /**
     * 
     * Crea una notificación a partir de una descripción y una sección recibidas como parámetro y la asigna a un rol
     * pasado como parámetro.
     * 
     * @param descripcion Descripción de la notificación
     * @param seccion Sección sobre la que se hace la notificación
     * @param rol Rol al que se le dirige la notificación
     * 
     */
    
    void crearNotificacionRol(String descripcion, String seccion, RoleEnum rol);
    
    /**
     * 
     * Crea una notificación a partir de una descripción y una sección recibidas como parámetro y la asigna a un listado
     * de roles pasado como parámetro.
     * 
     * @param descripcion Descripción de la notificación
     * @param seccion Sección sobre la que se hace la notificación
     * @param roles Lista de roles a los que se dirige la notificación
     * 
     */
    
    void crearNotificacionRol(String seccion, String descripcion, List<RoleEnum> roles);
    
    /**
     *
     * Crea una notificación a partir de una descripción y una sección recibidas como parámetro y la asigna al equipo
     * que se pasa como parámetro.
     *
     * @param descripcion Descripción de la notificación
     * @param seccion Sección sobre la que se hace la notificación
     * @param equipo Equipo al que se le dirige la notificación
     *
     */
    
    void crearNotificacionEquipo(String descripcion, String seccion, Equipo equipo);
    
    /**
     * 
     * Crea una notificación a partir de una descripción y una sección recibidas como parámetro y la asigna al jefe del
     * equipo de una inspección pasada como parámetro.
     * 
     * @param descripcion Descripción de la notificación
     * @param seccion Sección sobre la que se hace la notificación
     * @param inspeccion Inspección a cuyo jefe de equipo se le dirige la notificación
     * 
     */
    
    void crearNotificacionJefeEquipo(String descripcion, String seccion, Inspeccion inspeccion);
    
    /**
     * Devuelve un listado de notificaciones asignadas a un usuario. El resultado se devuelve paginado.
     * 
     * @param first Primer registro del listado
     * @param pageSize Número máximo de registros del listado
     * @param sortField Campo de ordenación
     * @param sortOrder Sentido de la ordenación
     * @param usuario Usuario para el que se buscan las notificaciones
     * @return Listado de notificaciones
     */
    List<Notificacion> buscarPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            String usuario);
    
    /**
     * Devuelve el número total de notificaciones asignadas a un usuario.
     * 
     * @param usuario Usuario del que se desean recuperar las notificaciones
     * @return Número de registros coincidentes con la búsqueda
     */
    int getCounCriteria(String usuario);
}
