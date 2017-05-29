package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;

/**
 * Repositorio de operaciones con base de datos para la entidad User.
 * 
 * @author EZENTIS
 *
 */
public interface IUserRepository extends CrudRepository<User, String> {
    /**
     * Busca un usuario por su correo o su documento de identidad, ignorando mayúsculas.
     * 
     * @param correo correo del usuario a buscar
     * @param docIdentidad documento del usuario a buscar
     * @return resultado de la búsqueda
     * 
     */
    User findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase(String correo, String docIdentidad);
    
    /**
     * Busca un usuario a partir de su correo electrónico.
     * 
     * @param correo del usuario a buscar
     * @return resultado de la búsqueda
     */
    User findByCorreo(String correo);
    
    /**
     * Busca un usuario por su correo o su documento de identidad.
     * 
     * @param correo correo del usuario a buscar
     * @param docIdentidad documento del usuario a buscar
     * @return resultado de la búsqueda
     */
    User findByCorreoOrDocIdentidad(String correo, String docIdentidad);
    
    /**
     * Recupera un listado de usuarios que pertenezcan a un cuerpo recibido como parámetro.
     * 
     * @param cuerpo al que pertenecen los usuarios
     * @return resultado de la búsqueda
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
    @Query("SELECT u FROM User u WHERE u.role='ROLE_EQUIPO_INSPECCIONES' AND NOT EXISTS (SELECT m FROM Miembro m WHERE m.username=u.username AND (m.posicion='JEFE_EQUIPO' OR m.equipo = :equipo)) ")
    List<User> buscarNoJefeNoMiembroEquipo(@Param("equipo") Equipo equipo);
    
    /**
     * Recupera un usuario a partir del id recibido como parámetro ignorando mayúsculas.
     * 
     * @param id del usuario a localizar
     * @return resultado de la búsqueda
     */
    User findByUsernameIgnoreCase(String id);
    
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
     * Comprueba la existencia de usuarios que tengan asignado un cuerpo de estado recibido como parámetro.
     * 
     * @param cuerpo que se desea verificar
     * @return resultado de la comprobación
     */
    boolean existsByCuerpoEstado(CuerpoEstado cuerpo);
}
