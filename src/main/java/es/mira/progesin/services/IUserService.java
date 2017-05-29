package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.web.beans.UserBusqueda;

/**
 * @author EZENTIS
 * 
 * Interfaz para el servicio de usuarios
 *
 */
public interface IUserService {
    /**
     * Elimina el usuario seleccionado de la base de datos.
     * 
     * @param id Username del usuario a eliminar
     */
    void delete(String id);
    
    /**
     * Comprueba la existencia de un usuario en la base de datos.
     * 
     * @param id Username del usuario a buscar
     * @return Respuesta de la consulta.
     */
    boolean exists(String id);
    
    /**
     * Busca en base de datos un usuario identificado por su username.
     * 
     * @param id Username del usuario a buscar
     * @return Usuario que correponde a la búsqueda.
     */
    User findOne(String id);
    
    /**
     * Busca en base de datos un usuario identificado por su username ignorando mayúsculas/minúsculas.
     * 
     * @param id Username del usuario a buscar
     * @return Usuario que correponde a la búsqueda.
     */
    User findByUsernameIgnoreCase(String id);
    
    /**
     * Guarda en base de datos el usuario.
     * 
     * @param entity Usuario a guardar
     * @return Usuario guardado.
     */
    User save(User entity);
    
    /**
     * Busca un usuario por su correo o su documento de identidad, ignorando mayúsculas.
     * 
     * @param correo correo del usuario a buscar
     * @param nif documento del usuario a buscar
     * @return resultado de la búsqueda
     * 
     */
    User findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase(String nif, String correo);
    
    /**
     * Devuelve una lista de usuarios en función de los criterios de búsqueda recibidos como parámetro. El listado se
     * devuelve paginado.
     * 
     * @param first Primer elemento del listado
     * @param pageSize Número máximo de registros recuperados
     * @param sortField Campo por el que sed realiza la ordenación del listado
     * @param sortOrder Sentido de la ordenación
     * @param userBusqueda Objeto que contiene los parámetros de búsqueda
     * 
     * @return Listado resultante de la búsqueda
     */
    List<User> buscarUsuarioCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            UserBusqueda userBusqueda);
    
    /**
     * Devuelve el número total de registros resultado de la búsqueda.
     * 
     * @param userBusqueda Objeto que contiene los parámetros de búsqueda
     * @return número de registros resultantes de la búsqueda
     */
    int contarRegistros(UserBusqueda userBusqueda);
    
    /**
     * Busca los usuarios que estén asignados a un cuerpo de estado.
     * 
     * @param cuerpo Cuerpo por el que se desea buscar
     * @return Lista de usuarios
     */
    List<User> findByCuerpoEstado(CuerpoEstado cuerpo);
    
    /**
     * Recupera un listado de usuarios que no hayan sido de baja y que cuyo rol no esté en el listado que se recibe como
     * parámetro.
     * 
     * @param rolesProv listado de roles a los que no queremos que pertenezcan los usuarios
     * @return resultado de la búsqueda
     */
    List<User> findByfechaBajaIsNullAndRoleNotIn(List<RoleEnum> rolesProv);
    
    /**
     * Cambia el estado del usuario.
     * 
     * @param usuarioProv Username del usuario al que se desea cambiar el estado
     * @param estado Estado que se desea asignar al usuario
     */
    void cambiarEstado(String usuarioProv, EstadoEnum estado);
    
    /**
     * Recupera un listado de usuarios que no hayan sido de baja y que cuyo rol corresponda al que se recibe como
     * parámetro.
     * 
     * @param rol al que no queremos que pertenezcan los usuarios
     * @return resultado de la búsqueda
     */
    List<User> findByfechaBajaIsNullAndRole(RoleEnum rol);
    
    /**
     * Buscar todos aquellos usuarios que no son jefe de algún equipo o miembros de este equipo.
     * 
     * @param equipo equipo para el que se hace la consulta
     * @return resultado de la búsqueda
     */
    List<User> buscarNoJefeNoMiembroEquipo(Equipo equipo);
    
    /**
     * Crea usuario provisionales.
     * 
     * @param correoPrincipal Correo electrónico del usuario principal
     * @param rawPassword Palabra clave
     * @return lista de usuarios Lista de usuarios creados
     */
    List<User> crearUsuariosProvisionalesCuestionario(String correoPrincipal, String rawPassword);
    
    /**
     * Recupera un listado de usuarios cuyo puesto de trabajo corresponde con el recibido como parámetro.
     * 
     * @param puesto sobre el que se hace la consulta
     * @return resultado de la búsqueda
     */
    List<User> findByPuestoTrabajo(PuestoTrabajo puesto);
    
    /**
     * Recupera un listado de todos los usuarios que pertenecen a un departamento.
     * 
     * @param departamento sobre el que se hace la consulta
     * @return resultado de la búsqueda
     */
    List<User> findByDepartamento(Departamento departamento);
    
    /**
     * Comprueba la existencia de usuarios que tengan asignado un cuerpo de estado.
     * 
     * @param cuerpo cuerpo que se desea verificar
     * @return resultado de la consulta
     */
    boolean existByCuerpoEstado(CuerpoEstado cuerpo);
}
