package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;

public interface IUserRepository extends CrudRepository<User, String> {
    User findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase(String correo, String docIdentidad);
    
    User findByCorreo(String correo);
    
    User findByCorreoOrDocIdentidad(String correo, String docIdentidad);
    
    List<User> findByCuerpoEstado(CuerpoEstado cuerpo);
    
    List<User> findByfechaBajaIsNullAndRoleNotIn(List<RoleEnum> rolesProv);
    
    List<User> findByfechaBajaIsNullAndRole(RoleEnum rol);
    
    // Buscar todos aquellos usuarios que no son jefe de algún equipo o miembros de este equipo
    @Query("SELECT u FROM User u WHERE u.role='EQUIPO_INSPECCIONES' AND NOT EXISTS (SELECT m FROM Miembro m WHERE m.username=u.username AND (m.posicion='JEFE_EQUIPO' OR m.equipo = :equipo)) ")
    List<User> buscarNoJefeNoMiembroEquipo(@Param("equipo") Equipo equipo);
    
    User findByUsernameIgnoreCase(String id);
    
    List<User> findByPuestoTrabajo(PuestoTrabajo puesto);
}
