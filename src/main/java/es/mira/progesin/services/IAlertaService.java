package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;

/**
 * 
 * Interfaz del servicio de Alertas.
 * 
 * @author EZENTIS
 * 
 */

public interface IAlertaService {
    /**
     * 
     * Elimina de la base de datos una alerta cuyo id se recibe como parámetro.
     * 
     * @param id de la alerta a eliminar de la base de datos
     * 
     */
    void delete(Long id);
    
    /**
     * 
     * Elimina de la base de datos todas las alertas.
     * 
     * 
     */
    void deleteAll();
    
    /**
     * 
     * Comprueba si existe en de base de datos una alerta cuyo id se pasa como parámetro.
     * 
     * @param id de la alerta a buscar en base de datos
     * @return booleano con el valor de la consulta
     *
     */
    
    boolean exists(Long id);
    
    /**
     * 
     * Busca en base de datos las alerta cuyo id se recibe como parámetro.
     * 
     * @param id de la alerta a localizar en base de datos
     * @return alerta que corresponde a la búsqueda
     * 
     */
    
    Alerta findOne(Long id);
    
    /**
     * 
     * Busca en base de datos todas las alertas que no hayan sido dadas de baja.
     * 
     * @return Lista de las alertas cuya fecha de baja es nulo
     * 
     */
    
    List<Alerta> findByFechaBajaIsNull();
    
    /**
     * 
     * Crea una alerta y se asigna a u usuario. Se crea a partir de la sección, la descripción y el usuario que se
     * reciben como parámetros.
     * 
     * @param seccion Sección para la que se crea la alerta
     * @param descripcion Descripción de la alerta
     * @param usuario Usuario al que se envía la alerta
     * 
     */
    
    public void crearAlertaUsuario(String seccion, String descripcion, User usuario);
    
    /**
     * 
     * Crea una alerta y se asigna a una lista de usuarios.
     * 
     * @param seccion Sección para la que se crea la alerta
     * @param descripcion Descripción de la alerta
     * @param listaUsuarios Listado de usuarios a los que se debe enviar la alerta
     * 
     */
    public void crearAlertaMultiplesUsuarios(String seccion, String descripcion, List<User> listaUsuarios);
    
    /**
     * 
     * Crea una alerta y se asigna a un rol. Se crea a partir de la sección, la descripción y el rol que se reciben como
     * parámetros
     * 
     * @param seccion para la que se crea la alerta
     * @param descripcion de la alerta
     * @param rol Se enviará la alerta a todos los usuarios cuyo rol corresponda al pasado como parámetro
     * 
     */
    
    void crearAlertaRol(String seccion, String descripcion, RoleEnum rol);
    
    /**
     * 
     * Crea una alerta y se asigna a varios roles. Se crea a partir de la sección, la descripción y la lista de roles
     * que se reciben como parámetros
     * 
     * @param seccion para la que se crea la alerta
     * @param descripcion de la alerta
     * @param roles Se enviará la alerta a todos los usuarios cuyo rol esté contenido en la lista de roles que se recibe
     * como parámetro
     * 
     */
    
    void crearAlertaRol(String seccion, String descripcion, List<RoleEnum> roles);
    
    /**
     * 
     * Crea una alerta y se asigna a un equipo de inspección. Se crea a partir de la sección, la descripción y la
     * inspección que se reciben como parámetros
     * 
     * @param seccion para la que se crea la alerta
     * @param descripcion de la alerta
     * @param inspeccion Se enviará la alerta a todos los miembros del equipo que tiene asignada esta inspección
     * 
     */
    
    void crearAlertaEquipo(String seccion, String descripcion, Inspeccion inspeccion);
    
    /**
     * 
     * Crea una alerta y se asigna al jefe de un equipo asignado a una inspección. Se crea a partir de la sección, la
     * descripción y la inspección que se reciben como parámetros
     * 
     * @param seccion para la que se crea la alerta
     * @param descripcion de la alerta
     * @param inspeccion se envia la alerta al jefe del equipo que tiene asignada la inspección recibida como parámetro
     * 
     */
    
    void crearAlertaJefeEquipo(String seccion, String descripcion, Inspeccion inspeccion);
    
    /**
     * 
     * Busca en la base de datos por los campos pasados como parámetro. La búsqueda permite paginación.
     * 
     * @param first Primer elemento a recuperar del listado.
     * @param pageSize Número máximo de resultados a recuperar
     * @param sortField Campo por el cual se ordenarán los resultados
     * @param sortOrder Sentido de la ordenación (Ascendente/descendente)
     * @param usuario Usuario para el cual se buscan sus alertas
     * @return Listado de las alertas que resulten de la búsqueda
     */
    List<Alerta> buscarPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder, String usuario);
    
    /**
     * Devuelve el número de alertas existentes en base de datos para el usuario pasado como parámetro.
     * 
     * @param usuario Usuario para el cual se buscan las alertas
     * @return integer correspondiente al número total de las alertas contenidas en base de datos para el usuario
     */
    int getCounCriteria(String usuario);
}
