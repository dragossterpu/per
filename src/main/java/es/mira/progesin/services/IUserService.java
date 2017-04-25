package es.mira.progesin.services;

import java.util.List;

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
     * @param id Username
     */
    void delete(String id);
    
    /**
     * @param id
     * @return boolean
     */
    boolean exists(String id);
    
    /**
     * @param id
     * @return User
     */
    User findOne(String id);
    
    /**
     * @param id
     * @return usuario
     */
    User findByUsernameIgnoreCase(String id);
    
    /**
     * @param entity
     * @return usuario
     */
    User save(User entity);
    
    /**
     * @param nif
     * @param correo
     * @return usuario
     */
    User findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase(String nif, String correo);
    
    /**
     * @param userBusqueda
     * @return lista de usuarios
     */
    List<User> buscarUsuarioCriteria(UserBusqueda userBusqueda);
    
    /**
     * @param cuerpo
     * @return lista de usuarios
     */
    List<User> findByCuerpoEstado(CuerpoEstado cuerpo);
    
    /**
     * @param rolesProv
     * @return lista de usuarios
     */
    List<User> findByfechaBajaIsNullAndRoleNotIn(List<RoleEnum> rolesProv);
    
    /**
     * @param usuarioProv
     * @param estado
     */
    void cambiarEstado(String usuarioProv, EstadoEnum estado);
    
    /**
     * @param rol
     * @return lista de usuarios
     */
    List<User> findByfechaBajaIsNullAndRole(RoleEnum rol);
    
    /**
     * Busca todos aquellos usuarios que no son jefe de algún equipo o miembros del equipo pasado como parámetro
     * 
     * @param equipo
     * @return lista de usuarios
     */
    List<User> buscarNoJefeNoMiembroEquipo(Equipo equipo);
    
    /**
     * Comprueba si un usuario es jefe de algún equipo de inspecciones
     * 
     * @param username
     * @return verdadero o falso
     */
    boolean esJefeEquipo(String username);
    
    /**
     * @param correoPrincipal
     * @param rawPassword
     * @return lista de usuarios
     */
    List<User> crearUsuariosProvisionalesCuestionario(String correoPrincipal, String rawPassword);
    
    /**
     * @param puesto
     * @return lista de usuarios
     */
    List<User> findByPuestoTrabajo(PuestoTrabajo puesto);
    
    /**
     * @param departamento
     * @return lista de usuarios
     */
    List<User> findByDepartamento(Departamento departamento);
    
}
