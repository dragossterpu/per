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
     * Buscar todos aquellos usuarios que no son miembros de algún equipo dado de alta (componente o miembro).
     * 
     * @return resultado de la búsqueda
     */
    @Query("SELECT u FROM User u WHERE u.role='ROLE_EQUIPO_INSPECCIONES' AND u.fechaBaja is null AND NOT EXISTS (SELECT m FROM Miembro m, Equipo e WHERE e.id=m.equipo AND m.usuario=u AND e.fechaBaja is null)")
    List<User> buscarNoMiembro();
    
    /**
     * Buscar todos aquellos usuarios que no son jefe de algún equipo o miembros de algún equipo en alta (componente o
     * miembro).
     * @param idEquipo del equipo editado
     * 
     * @return resultado de la búsqueda
     */
    @Query("SELECT u FROM User u WHERE u.role='ROLE_EQUIPO_INSPECCIONES' AND u.fechaBaja is null AND NOT EXISTS (SELECT m FROM Miembro m, Equipo e WHERE m.usuario=u AND e.id=m.equipo AND (m.posicion='JEFE_EQUIPO'AND e.fechaBaja is null OR e.id = :idEquipo))")
    List<User> buscarPosibleMiembroEquipoNoJefe(@Param("idEquipo") Long idEquipo);
    
    /**
     * Recupera un usuario a partir del id recibido como parámetro ignorando mayúsculas.
     * 
     * @param id del usuario a localizar
     * @return resultado de la búsqueda
     */
    User findByUsernameIgnoreCase(String id);
    
    /**
     * Comprueba la existencia de usuarios que tengan asignado un puesto de trabajo recibido como parámetro.
     * 
     * @param puesto puesto de trabajo que se desea verificar
     * @return resultado de la comprobación
     */
    boolean existsByPuestoTrabajo(PuestoTrabajo puesto);
    
    /**
     * Comprueba la existencia de usuarios que tengan asignado un departamento recibido como parámetro.
     * 
     * @param departamento departamento que se desea verificar
     * @return resultado de la comprobación
     */
    boolean existsByDepartamento(Departamento departamento);
    
    /**
     * Comprueba la existencia de usuarios que tengan asignado un cuerpo de estado recibido como parámetro.
     * 
     * @param cuerpo cuerpo de estado que se desea verificar
     * @return resultado de la comprobación
     */
    boolean existsByCuerpoEstado(CuerpoEstado cuerpo);
    
    /**
     * Devuelve una lista de usuarios que pertenecen al equipo recibido como parámetro.
     * 
     * @param equipo Equipo del que se desean extraer sus usuarios
     * @return Lista de usuarios.
     */
    @Query("Select a from User a, Miembro b where a.username=b.usuario.username and b.equipo=:equipo")
    List<User> usuariosEnEquipo(@Param("equipo") Equipo equipo);
    
    /**
     * Obtiene los usuarios provisionales que comparten un correo electrónico pasado como parámetro.
     * 
     * @param correo Correo por el que se buscará
     * @return Usuarios resultantes
     */
    @Query("Select a from User a where role='ROLE_PROV_CUESTIONARIO' and correo=?1")
    List<User> usuariosProvisionalesCorreo(String correo);
}
